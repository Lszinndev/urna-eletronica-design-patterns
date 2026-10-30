package br.edu.urna.domain;

import br.edu.urna.patterns.behavioral.state.EstadoUrna;
import br.edu.urna.patterns.behavioral.state.UrnaAguardandoAberturaState;

import java.util.function.BooleanSupplier;

/**
 * Representa a urna eletrônica e guarda o estado em que ela se encontra.
 * @note Papel no Padrão: Context (State)
 */
public class UrnaContexto {

    private EstadoUrna estado;

    public UrnaContexto() {
        this.estado = new UrnaAguardandoAberturaState();
    }

    public void setEstado(EstadoUrna novoEstado) {
        System.out.println("[STATE] Urna mudou de \"" + estado.getNome() + "\" para \"" + novoEstado.getNome() + "\"");
        this.estado = novoEstado;
    }

    public String getNomeEstado() {
        return estado.getNome();
    }

    public boolean abrir() {
        return estado.abrir(this);
    }

    public boolean liberar(BooleanSupplier autorizacao) {
        return estado.liberar(this, autorizacao);
    }

    public boolean votar(int numero) {
        return estado.votar(this, numero);
    }

    public boolean encerrar() {
        return estado.encerrar(this);
    }

    public boolean permiteCadastro() {
        return estado.permiteCadastro();
    }

    public boolean permiteApuracao() {
        return estado.permiteApuracao();
    }
}
