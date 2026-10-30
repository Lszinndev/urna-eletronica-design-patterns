package br.edu.urna.patterns.creational.singleton;

import br.edu.urna.domain.Candidato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Armazena os candidatos e todos os votos da seção em um único lugar.
 * @note Papel no Padrão: Singleton
 */
public class CofreDeVotos {

    private final List<Candidato> candidatos = new ArrayList<>();
    private final Map<Integer, Integer> votosPorCandidato = new HashMap<>();
    private int totalDeVotos;
    private int votosBrancos;
    private int votosNulos;

    private CofreDeVotos() {
        System.out.println("[SINGLETON] Cofre de votos criado (instância única).");
    }

    /**
     * Idiom "Initialization-on-demand Holder": a JVM só carrega a classe Holder
     * na primeira chamada de getInstancia(), e a inicialização de classes é
     * thread-safe por especificação. Resultado: criação lazy e segura sem synchronized.
     */
    private static class Holder {
        private static final CofreDeVotos INSTANCIA = new CofreDeVotos();
    }

    public static CofreDeVotos getInstancia() {
        return Holder.INSTANCIA;
    }

    public void adicionarCandidato(Candidato candidato) {
        candidatos.add(candidato);
        votosPorCandidato.put(candidato.getNumero(), 0);
        System.out.println("[SINGLETON] Candidato " + candidato.getNome() + " guardado no cofre.");
    }

    public Candidato buscarCandidato(int numero) {
        for (Candidato candidato : candidatos) {
            if (candidato.getNumero() == numero) {
                return candidato;
            }
        }
        return null;
    }

    public void registrarVoto(int numero) {
        totalDeVotos++;
        Candidato candidato = buscarCandidato(numero);

        if (numero == 0) {
            votosBrancos++;
            System.out.println("[SINGLETON] Voto em BRANCO registrado no cofre.");
        } else if (candidato == null) {
            votosNulos++;
            System.out.println("[SINGLETON] O número " + numero + " não existe. Voto NULO registrado no cofre.");
        } else {
            votosPorCandidato.put(numero, votosPorCandidato.get(numero) + 1);
            System.out.println("[SINGLETON] Voto para " + candidato.getNome() + " registrado no cofre.");
        }
    }

    public int getVotosDoCandidato(Candidato candidato) {
        return votosPorCandidato.get(candidato.getNumero());
    }

    /**
     * Retorna uma visão somente leitura: novos candidatos só entram pelo
     * adicionarCandidato(), mantendo a lista e o mapa de votos sempre consistentes.
     */
    public List<Candidato> getCandidatos() {
        return Collections.unmodifiableList(candidatos);
    }

    public int getTotalDeVotos() {
        return totalDeVotos;
    }

    public int getVotosBrancos() {
        return votosBrancos;
    }

    public int getVotosNulos() {
        return votosNulos;
    }

    public int getVotosInvalidos() {
        return votosBrancos + votosNulos;
    }

    public int getVotosValidos() {
        return totalDeVotos - getVotosInvalidos();
    }
}
