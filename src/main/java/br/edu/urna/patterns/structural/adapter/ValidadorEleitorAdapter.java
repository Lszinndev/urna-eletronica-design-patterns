package br.edu.urna.patterns.structural.adapter;

/**
 * Classe responsável por adaptar a resposta numérica do sistema legado do TSE.
 * @note Papel no Padrão: Adapter
 */
public class ValidadorEleitorAdapter implements ValidadorEleitor {

    private final SistemaLegadoTSE sistemaLegado;

    public ValidadorEleitorAdapter(SistemaLegadoTSE sistemaLegado) {
        this.sistemaLegado = sistemaLegado;
    }

    @Override
    public boolean eleitorApto(String tituloEleitor) {
        int codigo = sistemaLegado.consultarSituacaoTitulo(tituloEleitor);
        boolean apto = codigo == 1;
        System.out.println("[ADAPTER] Código legado " + codigo + " convertido para " + apto);
        return apto;
    }
}
