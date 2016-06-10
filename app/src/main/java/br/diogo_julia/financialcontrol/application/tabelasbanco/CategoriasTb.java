package br.diogo_julia.financialcontrol.application.tabelasbanco;

public enum CategoriasTb{

    COLUMN_ID("id","integer primary key autoincrement"),
    COLUMN_NAME("nome", "text");


    private String coluna;
    private String tipo;
    CategoriasTb(String coluna, String tipo) {
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
