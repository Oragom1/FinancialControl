package br.diogo_julia.financialcontrol.application.tabelasbanco;

public enum CreditoTb {
    COLUMN_ID("id","integer primary key autoincrement"),
    COLUMN_DESCRICAO("descricao", "text"),
    COLUMN_LIMITE("limite", "real"),
    COLUMN_BANDEIRA("bandeira", "texto"),
    COLUMN_TIPO("tipo", "texto"),
    COLUMN_VENCIMENTO("vencimento", "integer");

    private String coluna;
    private String tipo;
    CreditoTb(String coluna, String tipo) {
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
