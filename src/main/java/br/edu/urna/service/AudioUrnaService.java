package br.edu.urna.service;

/**
 * Simula o alto-falante da urna.
 * @note Papel no Padrão: Subsystem (Facade)
 */
public class AudioUrnaService {

    public void emitirBipe() {
        System.out.println("[AUDIO] *bip*");
    }

    public void emitirSomDeFim() {
        System.out.println("[AUDIO] *bip bip bip... FIM*");
    }
}
