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

    /**
     * Tenta liberar a urna para o próximo eleitor.
     * A autorização (ex.: consulta ao TSE) só é executada pelos estados que
     * realmente aceitam liberação, evitando consultas desnecessárias.
     */
    boolean liberar(UrnaContexto urna, BooleanSupplier autorizacao);

    boolean votar(UrnaContexto urna, int numero);

    boolean encerrar(UrnaContexto urna);

    /** Indica se ainda é possível cadastrar candidatos neste estado. */
    boolean permiteCadastro();

    /** Indica se os resultados já podem ser apurados neste estado. */
    boolean permiteApuracao();
}
