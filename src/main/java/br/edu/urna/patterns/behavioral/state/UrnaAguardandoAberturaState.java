package br.edu.urna.patterns.behavioral.state;

import br.edu.urna.domain.UrnaContexto;

import java.util.function.BooleanSupplier;

/**
 * Urna ligada, aguardando o mesário abrir a seção e emitir a Zerésima.
 * Único estado em que ainda é possível cadastrar candidatos.
 * @note Papel no Padrão: Concrete State
 */
public class UrnaAguardandoAberturaState implements EstadoUrna {

    @Override
    public String getNome() {
        return "Aguardando Abertura";
    }

    @Override
    public boolean abrir(UrnaContexto urna) {
        urna.setEstado(new UrnaBloqueadaState());
        return true;
    }

    @Override
    public boolean liberar(UrnaContexto urna, BooleanSupplier autorizacao) {
        System.out.println("[STATE] A seção ainda não foi aberta. Abra a seção (opção 2) antes de liberar eleitores.");
        return false;
    }

    @Override
    public boolean votar(UrnaContexto urna, int numero) {
        System.out.println("[STATE] Voto recusado: a seção ainda não foi aberta.");
        return false;
    }

    @Override
    public boolean encerrar(UrnaContexto urna) {
        System.out.println("[STATE] Não é possível encerrar: a seção ainda não foi aberta.");
        return false;
    }

    @Override
    public boolean permiteCadastro() {
        return true;
    }

    @Override
    public boolean permiteApuracao() {
        return false;
    }
}

