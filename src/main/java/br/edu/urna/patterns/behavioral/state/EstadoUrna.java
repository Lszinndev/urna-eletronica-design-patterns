package br.edu.urna.patterns.behavioral.state;

import br.edu.urna.domain.UrnaContexto;

import java.util.function.BooleanSupplier;

/**
 * Define as ações que a urna pode receber em qualquer estado.
 * @note Papel no Padrão: State
 */
public interface EstadoUrna {

    String getNome();

    boolean abrir(UrnaContexto urna);

    boolean liberar(UrnaContexto urna, BooleanSupplier autorizacao);

    boolean votar(UrnaContexto urna, int numero);

    boolean encerrar(UrnaContexto urna);

    boolean permiteCadastro();

    boolean permiteApuracao();
}
