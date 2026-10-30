package br.edu.urna.patterns.behavioral.strategy;

import br.edu.urna.domain.Candidato;
import br.edu.urna.patterns.creational.singleton.CofreDeVotos;

/**
 * Exige 50% + 1 dos votos válidos para eleger no 1º turno. Se ninguém atingir,
 * leva os dois mais votados ao 2º turno, com desempate pelo candidato mais idoso.
 * Estratégia sem estado: todo o resultado é devolvido em ResultadoApuracao.
 * @note Papel no Padrão: Concrete Strategy
 */
public class SegundoTurnoAbsolutoStrategy implements RegraApuracaoStrategy {

    @Override
    public String getNome() {
        return "Maioria Absoluta (50% + 1) com 2º Turno";
    }

    @Override
    public ResultadoApuracao apurar(CofreDeVotos cofre) {
        System.out.println("[STRATEGY] Executando a regra: " + getNome());

        int votosValidos = cofre.getVotosValidos();
        if (votosValidos == 0) {
            System.out.println("[STRATEGY] Nenhum voto válido. Não há candidato eleito.");
            return ResultadoApuracao.semEleito();
        }

        int meta = votosValidos / 2 + 1;
        System.out.println("[STRATEGY] Votos válidos: " + votosValidos + " | Meta de maioria absoluta: " + meta);

        Candidato finalista1 = null;
        Candidato finalista2 = null;
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
            return ResultadoApuracao.eleito(finalista1);
        }

        System.out.println("[STRATEGY] Ninguém atingiu a meta. Haverá 2º TURNO entre:");
        System.out.println("   1) " + finalista1.getNome() + " - " + votosDoPrimeiro + " votos");
        System.out.println("   2) " + finalista2.getNome() + " - " + cofre.getVotosDoCandidato(finalista2) + " votos");
        return ResultadoApuracao.segundoTurno(finalista1, finalista2);
    }
}
