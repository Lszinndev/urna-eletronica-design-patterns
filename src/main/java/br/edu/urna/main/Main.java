package br.edu.urna.main;

import br.edu.urna.service.EleicaoService;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final EleicaoService eleicaoService = new EleicaoService();

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            System.out.println();

            switch (opcao) {
                case 1:
                    cadastrarCandidato();
                    break;
                case 2:
                    eleicaoService.abrirSecao();
                    break;
                case 3:
                    liberarEleitor();
                    break;
                case 4:
                    votar();
                    break;
                case 5:
                    eleicaoService.encerrarVotacao();
                    break;
                case 6:
                    apurarResultados();
                    break;
                case 0:
                    System.out.println("Sistema finalizado.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("========== URNA ELETRÔNICA ==========");
        System.out.println("Estado atual da urna: " + eleicaoService.getEstadoUrna());
        System.out.println("[1] Cadastrar Candidato (Builder)");
        System.out.println("[2] Abrir Seção / Emitir Zerésima (Facade)");
        System.out.println("[3] Validar Eleitor e Liberar Urna (Adapter + State)");
        System.out.println("[4] Cabine de Votação (State + Singleton)");
        System.out.println("[5] Encerrar Votação (Facade + State)");
        System.out.println("[6] Apurar Resultados (Strategy + Singleton)");
        System.out.println("[0] Sair");
    }

    private static void cadastrarCandidato() {
        String nome = lerTexto("Nome: ");
        int numero = lerInteiro("Número: ");
        String partido = lerTexto("Partido: ");
        String cargo = lerTexto("Cargo: ");
        int idade = lerInteiro("Idade: ");
        eleicaoService.cadastrarCandidato(nome, numero, partido, cargo, idade);
    }

    private static void liberarEleitor() {
        String titulo = lerTexto("Número do título de eleitor: ");
        eleicaoService.liberarEleitor(titulo);
    }

    private static void votar() {
        int numero = lerInteiro("Digite o número do candidato (0 = Branco): ");
        eleicaoService.votar(numero);
    }

    private static void apurarResultados() {
        System.out.println("Escolha a regra de apuração:");
        System.out.println("[1] Maioria Simples");
        System.out.println("[2] Maioria Absoluta (50% + 1) com 2º Turno");
        int regra = lerInteiro("Regra: ");

        boolean temSegundoTurno = eleicaoService.apurar(regra);
        if (temSegundoTurno) {
            System.out.println();
            System.out.println("===== SIMULAÇÃO DO 2º TURNO =====");
            int votos1 = lerInteiro("Votos de " + eleicaoService.getFinalista1().getNome() + ": ");
            int votos2 = lerInteiro("Votos de " + eleicaoService.getFinalista2().getNome() + ": ");
            eleicaoService.apurarSegundoTurno(votos1, votos2);
        }
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
            }
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }
}
