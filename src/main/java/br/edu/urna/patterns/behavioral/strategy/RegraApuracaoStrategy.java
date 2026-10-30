package br.edu.urna.patterns.behavioral.strategy;

import br.edu.urna.domain.Candidato;
import br.edu.urna.patterns.creational.singleton.CofreDeVotos;

/**
 * Define o formato comum das regras de apuração.
 * @note Papel no Padrão: Strategy
 */
public interface RegraApuracaoStrategy {

    String getNome();

    ResultadoApuracao apurar(CofreDeVotos cofre);

    default boolean estaNaFrente(Candidato a, Candidato b, CofreDeVotos cofre) {
        int votosA = cofre.getVotosDoCandidato(a);
        int votosB = cofre.getVotosDoCandidato(b);
        return votosA > votosB || (votosA == votosB && a.getIdade() > b.getIdade());
    }
}
