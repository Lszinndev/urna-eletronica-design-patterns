package br.edu.urna.patterns.behavioral.state;

import br.edu.urna.domain.UrnaContexto;

/**
 * Define as ações que a urna pode receber em qualquer estado.
 * @note Papel no Padrão: State
 */
public interface EstadoUrna {

    String getNome();

    boolean liberar(UrnaContexto urna);

    boolean votar(UrnaContexto urna, int numero);

    boolean encerrar(UrnaContexto urna);
}
