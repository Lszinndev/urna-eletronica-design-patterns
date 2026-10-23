package br.edu.urna.patterns.structural.adapter;

/**
 * Formato que a urna espera para saber se um eleitor pode votar.
 * @note Papel no Padrão: Target (Adapter)
 */
public interface ValidadorEleitor {

    boolean eleitorApto(String tituloEleitor);
}
