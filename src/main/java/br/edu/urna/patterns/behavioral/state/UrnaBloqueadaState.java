package br.edu.urna.patterns.behavioral.state;

import br.edu.urna.domain.UrnaContexto;

/**
 * Urna aguardando o mesário liberar o próximo eleitor.
 * @note Papel no Padrão: Concrete State
 */
public class UrnaBloqueadaState implements EstadoUrna {

    @Override
    public String getNome() {
        return "Bloqueada";
    }

    @Override
    public boolean liberar(UrnaContexto urna) {
        urna.setEstado(new UrnaLiberadaVotoState());
        return true;
    }

    @Override
    public boolean votar(UrnaContexto urna, int numero) {
        System.out.println("[STATE] Voto recusado: a urna está bloqueada. O mesário precisa liberar o eleitor.");
        return false;
    }

    @Override
    public boolean encerrar(UrnaContexto urna) {
        urna.setEstado(new UrnaEncerradaState());
        return true;
    }
}
