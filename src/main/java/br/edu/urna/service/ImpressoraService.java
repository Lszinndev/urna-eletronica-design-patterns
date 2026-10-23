package br.edu.urna.service;

import br.edu.urna.domain.Candidato;
import br.edu.urna.patterns.creational.singleton.CofreDeVotos;

/**
 * Simula a impressora da urna, que emite a Zerésima e o Boletim de Urna.
 * @note Papel no Padrão: Subsystem (Facade)
 */
public class ImpressoraService {

    public void imprimirZeresima() {
        System.out.println("[IMPRESSORA] ========== ZERÉSIMA ==========");
        imprimirContagem();
        System.out.println("[IMPRESSORA] ==============================");
    }

    public void imprimirBoletimDeUrna() {
        CofreDeVotos cofre = CofreDeVotos.getInstancia();
        System.out.println("[IMPRESSORA] ======= BOLETIM DE URNA =======");
        imprimirContagem();
        System.out.println("[IMPRESSORA] Total de votos: " + cofre.getTotalDeVotos());
        System.out.println("[IMPRESSORA] Votos válidos: " + cofre.getVotosValidos());
        System.out.println("[IMPRESSORA] ===============================");
    }

    private void imprimirContagem() {
        CofreDeVotos cofre = CofreDeVotos.getInstancia();
        for (Candidato candidato : cofre.getCandidatos()) {
            System.out.println("[IMPRESSORA] " + candidato.getNumero() + " - " + candidato.getNome()
                    + ": " + cofre.getVotosDoCandidato(candidato) + " votos");
        }
        System.out.println("[IMPRESSORA] Brancos: " + cofre.getVotosBrancos());
        System.out.println("[IMPRESSORA] Nulos: " + cofre.getVotosNulos());
    }
}
