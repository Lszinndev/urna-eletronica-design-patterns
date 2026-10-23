package br.edu.urna.patterns.behavioral.strategy;

import br.edu.urna.domain.Candidato;
import br.edu.urna.patterns.creational.singleton.CofreDeVotos;

/**
 * Define o formato comum das regras de apuração.
 * @note Papel no Padrão: Strategy
 */
public interface RegraApuracaoStrategy {

    String getNome();

    Candidato apurar(CofreDeVotos cofre);
}
