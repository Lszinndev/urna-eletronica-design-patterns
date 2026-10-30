package br.edu.urna.patterns.behavioral.strategy;

import br.edu.urna.domain.Candidato;
import br.edu.urna.patterns.creational.singleton.CofreDeVotos;

/**
 * Elege quem tiver mais votos válidos. Em caso de empate, vence o mais idoso.
 * @note Papel no Padrão: Concrete Strategy
 */
public class MaioriaSimplesStrategy implements RegraApuracaoStrategy {

    @Override
    public String getNome() {
        return "Maioria Simples";
    }

    @Override
    public ResultadoApuracao apurar(CofreDeVotos cofre) {
        System.out.println("[STRATEGY] Executando a regra: " + getNome());

        if (cofre.getVotosValidos() == 0) {
            System.out.println("[STRATEGY] Nenhum voto válido. Não há candidato eleito.");
            return ResultadoApuracao.semEleito();
        }

        Candidato vencedor = null;
        for (Candidato candidato : cofre.getCandidatos()) {
            if (vencedor == null || estaNaFrente(candidato, vencedor, cofre)) {
                vencedor = candidato;
            }
        }

        System.out.println("[STRATEGY] ELEITO: " + vencedor.getNome() + " com " + cofre.getVotosDoCandidato(vencedor) + " votos.");
        return ResultadoApuracao.eleito(vencedor);
    }
}
