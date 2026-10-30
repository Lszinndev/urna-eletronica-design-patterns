package br.edu.urna.patterns.behavioral.strategy;

import br.edu.urna.domain.Candidato;

/**
 * Resultado comum devolvido por qualquer regra de apuração.
 * Permite que o Context trate todas as estratégias de forma uniforme,
 * sem precisar conhecer a classe concreta que gerou o resultado.
 *
 * @param eleito     candidato eleito, ou null se não houver eleito (sem votos válidos ou 2º turno)
 * @param finalista1 1º colocado que disputará o 2º turno, ou null se não houver 2º turno
 * @param finalista2 2º colocado que disputará o 2º turno, ou null se não houver 2º turno
 */
public record ResultadoApuracao(Candidato eleito, Candidato finalista1, Candidato finalista2) {

    public static ResultadoApuracao semEleito() {
        return new ResultadoApuracao(null, null, null);
    }

    public static ResultadoApuracao eleito(Candidato candidato) {
        return new ResultadoApuracao(candidato, null, null);
    }

    public static ResultadoApuracao segundoTurno(Candidato finalista1, Candidato finalista2) {
        return new ResultadoApuracao(null, finalista1, finalista2);
    }

    public boolean temSegundoTurno() {
        return eleito == null && finalista1 != null && finalista2 != null;
    }

    /**
     * Decide o 2º turno entre os dois finalistas. Em empate exato, vence o mais idoso.
     */
    public Candidato decidirSegundoTurno(int votosFinalista1, int votosFinalista2) {
        if (!temSegundoTurno()) {
            throw new IllegalStateException("Este resultado não possui 2º turno.");
        }

        System.out.println("[STRATEGY] Apurando o 2º turno...");
        Candidato vencedor;

        if (votosFinalista1 > votosFinalista2) {
            vencedor = finalista1;
        } else if (votosFinalista2 > votosFinalista1) {
            vencedor = finalista2;
        } else {
            System.out.println("[STRATEGY] Empate exato! Critério de desempate: vence o candidato mais idoso.");
            if (finalista1.getIdade() >= finalista2.getIdade()) {
                vencedor = finalista1;
            } else {
                vencedor = finalista2;
            }
        }

        System.out.println("[STRATEGY] ELEITO NO 2º TURNO: " + vencedor.getNome() + " (" + vencedor.getIdade() + " anos).");
        return vencedor;
    }
}

