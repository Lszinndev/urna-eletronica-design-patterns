package br.edu.urna.patterns.behavioral.strategy;

import br.edu.urna.domain.Candidato;
import br.edu.urna.patterns.creational.singleton.CofreDeVotos;

/**
 * Exige 50% + 1 dos votos válidos para eleger no 1º turno. Se ninguém atingir,
 * leva os dois mais votados ao 2º turno, com desempate pelo candidato mais idoso.
 * @note Papel no Padrão: Concrete Strategy
 */
public class SegundoTurnoAbsolutoStrategy implements RegraApuracaoStrategy {

    private Candidato finalista1;
    private Candidato finalista2;
    private boolean temSegundoTurno;

    @Override
    public String getNome() {
        return "Maioria Absoluta (50% + 1) com 2º Turno";
    }

    @Override
    public Candidato apurar(CofreDeVotos cofre) {
        System.out.println("[STRATEGY] Executando a regra: " + getNome());
        temSegundoTurno = false;
        finalista1 = null;
        finalista2 = null;

        int votosValidos = cofre.getVotosValidos();
        if (votosValidos == 0) {
            System.out.println("[STRATEGY] Nenhum voto válido. Não há candidato eleito.");
            return null;
        }

        int meta = votosValidos / 2 + 1;
        System.out.println("[STRATEGY] Votos válidos: " + votosValidos + " | Meta de maioria absoluta: " + meta);

        for (Candidato candidato : cofre.getCandidatos()) {
            if (finalista1 == null || estaNaFrente(candidato, finalista1, cofre)) {
                finalista2 = finalista1;
                finalista1 = candidato;
            } else if (finalista2 == null || estaNaFrente(candidato, finalista2, cofre)) {
                finalista2 = candidato;
            }
        }

        int votosDoPrimeiro = cofre.getVotosDoCandidato(finalista1);
        if (votosDoPrimeiro >= meta || finalista2 == null) {
            System.out.println("[STRATEGY] ELEITO EM 1º TURNO: " + finalista1.getNome() + " com " + votosDoPrimeiro + " votos.");
            return finalista1;
        }

        temSegundoTurno = true;
        System.out.println("[STRATEGY] Ninguém atingiu a meta. Haverá 2º TURNO entre:");
        System.out.println("   1) " + finalista1.getNome() + " - " + votosDoPrimeiro + " votos");
        System.out.println("   2) " + finalista2.getNome() + " - " + cofre.getVotosDoCandidato(finalista2) + " votos");
        return null;
    }

    public Candidato apurarSegundoTurno(int votosFinalista1, int votosFinalista2) {
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

    public boolean temSegundoTurno() {
        return temSegundoTurno;
    }

    public Candidato getFinalista1() {
        return finalista1;
    }

    public Candidato getFinalista2() {
        return finalista2;
    }

    private boolean estaNaFrente(Candidato a, Candidato b, CofreDeVotos cofre) {
        int votosA = cofre.getVotosDoCandidato(a);
        int votosB = cofre.getVotosDoCandidato(b);
        return votosA > votosB || (votosA == votosB && a.getIdade() > b.getIdade());
    }
}
