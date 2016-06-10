package br.diogo_julia.financialcontrol.application.tabelasbanco;

/**
 * Created by rodrigo_sntg on 17/10/2015.
 */
public enum PlanosTb{

    COLUMN_ID("id","integer primary key autoincrement"),
    COLUMN_CATEGORIA("categoria", "text"),
    COLUMN_RESUMO("resumo", "text"),
    COLUMN_PRAZO("prazo", "integer"),
    COLUMN_DESCRICAO("descricao", "text");

    private String coluna;
    private String tipo;
    PlanosTb(String coluna, String tipo) {
        this.coluna = coluna;
        this.tipo = tipo;
    }

    public String getColuna() {
        return this.coluna;
    }

    public String getTipo() {
        return tipo;
    }
}
