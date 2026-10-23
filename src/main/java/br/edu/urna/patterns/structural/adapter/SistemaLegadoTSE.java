package br.edu.urna.patterns.structural.adapter;

/**
 * Sistema antigo do TSE que responde com 1 (regular) ou 0 (irregular).
 * @note Papel no Padrão: Adaptee (Adapter)
 */
public class SistemaLegadoTSE {

    public int consultarSituacaoTitulo(String numeroTitulo) {
        System.out.println("[ADAPTER] Consultando o sistema legado do TSE para o título " + numeroTitulo + "...");
        if (numeroTitulo.isEmpty() || numeroTitulo.endsWith("0")) {
            return 0;
        }
        return 1;
    }
}
