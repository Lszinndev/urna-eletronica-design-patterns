package br.edu.urna.service;

import br.edu.urna.domain.Candidato;
import br.edu.urna.domain.CandidatoBuilder;
import br.edu.urna.domain.UrnaContexto;
import br.edu.urna.patterns.behavioral.strategy.MaioriaSimplesStrategy;
import br.edu.urna.patterns.behavioral.strategy.RegraApuracaoStrategy;
import br.edu.urna.patterns.behavioral.strategy.ResultadoApuracao;
import br.edu.urna.patterns.behavioral.strategy.SegundoTurnoAbsolutoStrategy;
import br.edu.urna.patterns.creational.singleton.CofreDeVotos;
import br.edu.urna.patterns.structural.adapter.SistemaLegadoTSE;
import br.edu.urna.patterns.structural.adapter.ValidadorEleitor;
import br.edu.urna.patterns.structural.adapter.ValidadorEleitorAdapter;
import br.edu.urna.patterns.structural.facade.TerminalMesarioFacade;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Organiza as operações da seção eleitoral e escolhe a regra de apuração.
 * @note Papel no Padrão: Context (Strategy) e Client dos demais padrões
 */
public class EleicaoService {

    private final CofreDeVotos cofre = CofreDeVotos.getInstancia();
    private final UrnaContexto urna = new UrnaContexto();
    private final TerminalMesarioFacade terminalMesario = new TerminalMesarioFacade();
    private final ValidadorEleitor validador = new ValidadorEleitorAdapter(new SistemaLegadoTSE());
    private final Map<Integer, RegraApuracaoStrategy> regras = new LinkedHashMap<>();
    private final List<String> titulosQueVotaram = new ArrayList<>();
    private ResultadoApuracao ultimoResultado;

    public EleicaoService() {
        regras.put(1, new MaioriaSimplesStrategy());
        regras.put(2, new SegundoTurnoAbsolutoStrategy());
    }

    public void cadastrarCandidato(String nome, int numero, String partido, int idade) {
        if (!urna.permiteCadastro()) {
            System.out.println("Não é possível cadastrar candidatos depois que a seção foi aberta.");
            return;
        }
        if (cofre.buscarCandidato(numero) != null) {
            System.out.println("Já existe um candidato com o número " + numero + ".");
            return;
        }

        try {
            Candidato candidato = new CandidatoBuilder()
                    .comNome(nome)
                    .comNumero(numero)
                    .comPartido(partido)
                    .comIdade(idade)
                    .construir();
            cofre.adicionarCandidato(candidato);
            System.out.println("Candidato cadastrado: " + candidato);
        } catch (IllegalStateException e) {
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }

    public void abrirSecao() {
        if (cofre.getCandidatos().isEmpty()) {
            System.out.println("Cadastre pelo menos um candidato antes de abrir a seção.");
            return;
        }
        if (urna.abrir()) {
            terminalMesario.abrirSecao();
        }
    }

    /**
     * O estado da urna decide primeiro se aceita liberação; somente então
     * a autorização (eleitor repetido + consulta ao TSE via Adapter) é executada.
     */
    public void liberarEleitor(String titulo) {
        boolean liberou = urna.liberar(() -> autorizarEleitor(titulo));
        if (liberou) {
            titulosQueVotaram.add(titulo);
            System.out.println("Eleitor autorizado. Pode se dirigir à cabine de votação.");
        }
    }

    private boolean autorizarEleitor(String titulo) {
        if (titulosQueVotaram.contains(titulo)) {
            System.out.println("Este eleitor já votou nesta seção.");
            return false;
        }
        if (!validador.eleitorApto(titulo)) {
            System.out.println("Título irregular. Eleitor NÃO autorizado a votar.");
            return false;
        }
        return true;
    }

    public void votar(int numero) {
        urna.votar(numero);
    }

    public void encerrarVotacao() {
        if (urna.encerrar()) {
            terminalMesario.encerrarSecao();
        }
    }

    /**
     * Executa a regra escolhida. O Context conhece apenas a interface
     * RegraApuracaoStrategy e o ResultadoApuracao, nunca a classe concreta.
     * @return true se o resultado exigir 2º turno
     */
    public boolean apurar(int opcaoRegra) {
        if (!urna.permiteApuracao()) {
            System.out.println("Encerre a votação (opção 5) antes de apurar os resultados.");
            return false;
        }

        RegraApuracaoStrategy regraEscolhida = regras.get(opcaoRegra);
        if (regraEscolhida == null) {
            System.out.println("Regra inválida.");
            return false;
        }

        System.out.println("[STRATEGY] Regra selecionada: " + regraEscolhida.getNome());
        System.out.println("Brancos: " + cofre.getVotosBrancos() + " | Nulos: " + cofre.getVotosNulos()
                + " (não entram no cálculo)");
        ultimoResultado = regraEscolhida.apurar(cofre);

        return ultimoResultado.temSegundoTurno();
    }

    public void apurarSegundoTurno(int votosFinalista1, int votosFinalista2) {
        if (ultimoResultado == null || !ultimoResultado.temSegundoTurno()) {
            System.out.println("Não há 2º turno a ser apurado.");
            return;
        }
        ultimoResultado.decidirSegundoTurno(votosFinalista1, votosFinalista2);
    }

    public Candidato getFinalista1() {
        return ultimoResultado == null ? null : ultimoResultado.finalista1();
    }

    public Candidato getFinalista2() {
        return ultimoResultado == null ? null : ultimoResultado.finalista2();
    }

    public String getEstadoUrna() {
        return urna.getNomeEstado();
    }
}
