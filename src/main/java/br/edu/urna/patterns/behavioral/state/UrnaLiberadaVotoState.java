package br.edu.urna.patterns.behavioral.state;

import br.edu.urna.domain.UrnaContexto;
import br.edu.urna.patterns.creational.singleton.CofreDeVotos;

import java.util.function.BooleanSupplier;

/**
 * Urna liberada para um eleitor registrar o seu voto.
 * @note Papel no Padrão: Concrete State
 */
public class UrnaLiberadaVotoState implements EstadoUrna {

    @Override
    public String getNome() {
        return "Liberada para Voto";
    }

    @Override
    public boolean abrir(UrnaContexto urna) {
        System.out.println("[STATE] A seção já está aberta.");
        return false;
    }

    @Override
    public boolean liberar(UrnaContexto urna, BooleanSupplier autorizacao) {
        System.out.println("[STATE] A urna já está liberada. Aguarde o eleitor atual votar.");
        return false;
    }

    @Override
    public boolean votar(UrnaContexto urna, int numero) {
        CofreDeVotos.getInstancia().registrarVoto(numero);
        urna.setEstado(new UrnaBloqueadaState());
        return true;
    }

    @Override
    public boolean encerrar(UrnaContexto urna) {
        System.out.println("[STATE] Não é possível encerrar: há um eleitor na cabine de votação.");
        return false;
    }

    @Override
    public boolean permiteCadastro() {
        return false;
    }

    @Override
    public boolean permiteApuracao() {
        return false;
    }
}
