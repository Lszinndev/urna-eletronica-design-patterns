package br.edu.urna.domain;

/**
 * Representa um candidato que disputa a eleição na seção.
 * @note Papel no Padrão: Product (Builder)
 */
public class Candidato {

    private final String nome;
    private final int numero;
    private final String partido;
    private final int idade;

    public Candidato(String nome, int numero, String partido, int idade) {
        this.nome = nome;
        this.numero = numero;
        this.partido = partido;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    public String getPartido() {
        return partido;
    }

    public int getIdade() {
        return idade;
    }

    @Override
    public String toString() {
        return numero + " - " + nome + " (" + partido + "), " + idade + " anos";
    }
}
