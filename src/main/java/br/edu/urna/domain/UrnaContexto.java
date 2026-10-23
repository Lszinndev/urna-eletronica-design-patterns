package br.edu.urna.domain;

import br.edu.urna.patterns.behavioral.state.EstadoUrna;
import br.edu.urna.patterns.behavioral.state.UrnaBloqueadaState;
import br.edu.urna.patterns.behavioral.state.UrnaEncerradaState;

/**
 * Representa a urna eletrônica e guarda o estado em que ela se encontra.
 * @note Papel no Padrão: Context (State)
 */
public class UrnaContexto {

    private EstadoUrna estado;

    public UrnaContexto() {
        this.estado = new UrnaBloqueadaState();
    }

    public void setEstado(EstadoUrna novoEstado) {
        System.out.println("[STATE] Urna mudou de \"" + estado.getNome() + "\" para \"" + novoEstado.getNome() + "\"");
        this.estado = novoEstado;
    }

    public String getNomeEstado() {
        return estado.getNome();
    }

    public boolean liberar() {
        return estado.liberar(this);
    }

    public boolean votar(int numero) {
        return estado.votar(this, numero);
    }

    public boolean encerrar() {
        return estado.encerrar(this);
    }

    public boolean estaEncerrada() {
        return estado instanceof UrnaEncerradaState;
    }
}
