package br.edu.urna.patterns.structural.facade;

import br.edu.urna.service.AudioUrnaService;
import br.edu.urna.service.ImpressoraService;
import br.edu.urna.service.TelaUrnaService;

/**
 * Oferece ao mesário comandos simples que coordenam tela, áudio e impressora da urna.
 * @note Papel no Padrão: Facade
 */
public class TerminalMesarioFacade {

    private final TelaUrnaService telaService = new TelaUrnaService();
    private final AudioUrnaService audioService = new AudioUrnaService();
    private final ImpressoraService impressoraService = new ImpressoraService();

    public void abrirSecao() {
        System.out.println("[FACADE] Abrindo a seção eleitoral...");
        telaService.exibirMensagem("Ligando a urna eletrônica...");
        impressoraService.imprimirZeresima();
        audioService.emitirBipe();
        telaService.exibirMensagem("Seção aberta. Aguardando eleitores.");
    }

    public void encerrarSecao() {
        System.out.println("[FACADE] Encerrando a seção eleitoral...");
        audioService.emitirSomDeFim();
        telaService.exibirMensagem("FIM - Votação encerrada.");
        impressoraService.imprimirBoletimDeUrna();
    }
}
