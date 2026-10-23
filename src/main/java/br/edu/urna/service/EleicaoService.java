package br.edu.urna.service;

import br.edu.urna.domain.Candidato;
import br.edu.urna.domain.UrnaContexto;
import br.edu.urna.patterns.behavioral.strategy.MaioriaSimplesStrategy;
import br.edu.urna.patterns.behavioral.strategy.RegraApuracaoStrategy;
import br.edu.urna.patterns.behavioral.strategy.SegundoTurnoAbsolutoStrategy;
import br.edu.urna.patterns.creational.builder.CandidatoBuilder;
import br.edu.urna.patterns.creational.singleton.CofreDeVotos;
import br.edu.urna.patterns.structural.adapter.SistemaLegadoTSE;
import br.edu.urna.patterns.structural.adapter.ValidadorEleitor;
import br.edu.urna.patterns.structural.adapter.ValidadorEleitorAdapter;
import br.edu.urna.patterns.structural.facade.TerminalMesarioFacade;

import java.util.ArrayList;
import java.util.List;

/**
 * Organiza as operações da seção eleitoral e escolhe a regra de apuração.
 * @note Papel no Padrão: Context (Strategy) e Client dos demais padrões
 */
public class EleicaoService {

    private final CofreDeVotos cofre = CofreDeVotos.getInstancia();
    private final UrnaContexto urna = new UrnaContexto();
    private final TerminalMesarioFacade terminalMesario = new TerminalMesarioFacade();
    private final ValidadorEleitor validador = new ValidadorEleitorAdapter(new SistemaLegadoTSE());
    private final MaioriaSimplesStrategy regraMaioriaSimples = new MaioriaSimplesStrategy();
    private final SegundoTurnoAbsolutoStrategy regraSegundoTurno = new SegundoTurnoAbsolutoStrategy();
    private final List<String> titulosQueVotaram = new ArrayList<>();
    private boolean secaoAberta = false;

    public void cadastrarCandidato(String nome, int numero, String partido, String cargo, int idade) {
        if (secaoAberta) {
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
                    .comCargo(cargo)
                    .comIdade(idade)
                    .construir();
            cofre.adicionarCandidato(candidato);
            System.out.println("Candidato cadastrado: " + candidato);
        } catch (IllegalStateException e) {
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }

    public void abrirSecao() {
        if (secaoAberta) {
            System.out.println("A seção já está aberta.");
            return;
        }
        if (cofre.getCandidatos().isEmpty()) {
            System.out.println("Cadastre pelo menos um candidato antes de abrir a seção.");
            return;
        }
        terminalMesario.abrirSecao();
        secaoAberta = true;
    }

    public void liberarEleitor(String titulo) {
        if (!secaoAberta) {
            System.out.println("Abra a seção (opção 2) antes de liberar eleitores.");
            return;
        }
        if (titulosQueVotaram.contains(titulo)) {
            System.out.println("Este eleitor já votou nesta seção.");
            return;
        }
        if (!validador.eleitorApto(titulo)) {
            System.out.println("Título irregular. Eleitor NÃO autorizado a votar.");
            return;
        }
        if (urna.liberar()) {
            titulosQueVotaram.add(titulo);
            System.out.println("Eleitor autorizado. Pode se dirigir à cabine de votação.");
        }
    }

    public void votar(int numero) {
        urna.votar(numero);
    }

    public void encerrarVotacao() {
        if (!secaoAberta) {
            System.out.println("A seção ainda não foi aberta.");
            return;
        }
        if (urna.encerrar()) {
            terminalMesario.encerrarSecao();
        }
    }

    public boolean apurar(int opcaoRegra) {
        if (!urna.estaEncerrada()) {
            System.out.println("Encerre a votação (opção 5) antes de apurar os resultados.");
            return false;
        }

        RegraApuracaoStrategy regraEscolhida;
        if (opcaoRegra == 1) {
            regraEscolhida = regraMaioriaSimples;
        } else if (opcaoRegra == 2) {
            regraEscolhida = regraSegundoTurno;
        } else {
            System.out.println("Regra inválida.");
            return false;
        }

        System.out.println("[STRATEGY] Regra selecionada: " + regraEscolhida.getNome());
        System.out.println("Brancos: " + cofre.getVotosBrancos() + " | Nulos: " + cofre.getVotosNulos()
                + " (não entram no cálculo)");
        regraEscolhida.apurar(cofre);

        return regraEscolhida == regraSegundoTurno && regraSegundoTurno.temSegundoTurno();
    }

    public void apurarSegundoTurno(int votosFinalista1, int votosFinalista2) {
        regraSegundoTurno.apurarSegundoTurno(votosFinalista1, votosFinalista2);
    }

    public Candidato getFinalista1() {
        return regraSegundoTurno.getFinalista1();
    }

    public Candidato getFinalista2() {
        return regraSegundoTurno.getFinalista2();
    }

    public String getEstadoUrna() {
        return urna.getNomeEstado();
    }
}
