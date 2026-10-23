package br.edu.urna.service;

/**
 * Simula a tela da urna.
 * @note Papel no Padrão: Subsystem (Facade)
 */
public class TelaUrnaService {

    public void exibirMensagem(String mensagem) {
        System.out.println("[TELA] " + mensagem);
    }
}
