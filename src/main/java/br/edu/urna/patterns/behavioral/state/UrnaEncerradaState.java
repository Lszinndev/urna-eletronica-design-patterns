package br.edu.urna.patterns.behavioral.state;

import br.edu.urna.domain.UrnaContexto;

/**
 * Urna com a votação encerrada definitivamente.
 * @note Papel no Padrão: Concrete State
 */
public class UrnaEncerradaState implements EstadoUrna {

    @Override
    public String getNome() {
        return "Encerrada";
    }

    @Override
    public boolean liberar(UrnaContexto urna) {
        System.out.println("[STATE] A votação já foi encerrada. Nenhum eleitor pode ser liberado.");
        return false;
    }

    @Override
    public boolean votar(UrnaContexto urna, int numero) {
        System.out.println("[STATE] A votação já foi encerrada. Voto recusado.");
        return false;
    }

    @Override
    public boolean encerrar(UrnaContexto urna) {
        System.out.println("[STATE] A votação já está encerrada.");
        return false;
    }
}
