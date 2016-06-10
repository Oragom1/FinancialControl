package br.diogo_julia.financialcontrol.application.tabelasbanco;

public enum ContaTb{

    COLUMN_ID("id","integer primary key autoincrement"),
    COLUMN_CATEGORIA("categoria", "text"),
    COLUMN_VALOR("valor", "real"),
    COLUMN_TIPO("tipo", "texto"),
    COLUMN_DATA("data", "integer"),
    COLUMN_CARTAO("cartao", "text"),
    COLUMN_QTDPARCELAS("qtdparcelas", "integer");

    private String coluna;
    private String tipo;
    ContaTb(String coluna, String tipo) {
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
