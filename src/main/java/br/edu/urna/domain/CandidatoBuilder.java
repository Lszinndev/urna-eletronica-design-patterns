package br.edu.urna.domain;

/**
 * Monta um objeto Candidato passo a passo.
 * Fica no mesmo pacote de Candidato para que o construtor do produto seja
 * visível apenas ao Builder, garantindo que toda criação passe pelas validações.
 * @note Papel no Padrão: Builder
 */
public class CandidatoBuilder {

    private String nome;
    private int numero;
    private String partido;
    private int idade;

    public CandidatoBuilder() {
        System.out.println("[BUILDER] Iniciando a construção de um novo candidato...");
    }

    public CandidatoBuilder comNome(String nome) {
        this.nome = nome;
        System.out.println("[BUILDER] Nome definido: " + nome);
        return this;
    }

    public CandidatoBuilder comNumero(int numero) {
        this.numero = numero;
        System.out.println("[BUILDER] Número definido: " + numero);
        return this;
    }

    public CandidatoBuilder comPartido(String partido) {
        this.partido = partido;
        System.out.println("[BUILDER] Partido definido: " + partido);
        return this;
    }


    public CandidatoBuilder comIdade(int idade) {
        this.idade = idade;
        System.out.println("[BUILDER] Idade definida: " + idade);
        return this;
    }

    public Candidato construir() {
        if (nome == null || nome.isBlank()) {
            throw new IllegalStateException("O nome do candidato é obrigatório.");
        }
        if (numero <= 0) {
            throw new IllegalStateException("O número deve ser maior que zero (o 0 é reservado para voto em branco).");
        }
        if (partido == null || partido.isBlank()) {
            throw new IllegalStateException("O partido do candidato é obrigatório.");
        }
        if (idade < 18) {
            throw new IllegalStateException("O candidato precisa ter pelo menos 18 anos.");
        }
        System.out.println("[BUILDER] Candidato construído com sucesso!");
        return new Candidato(nome, numero, partido, idade);
    }
}

