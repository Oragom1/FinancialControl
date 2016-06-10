package br.diogo_julia.financialcontrol.application.tabelasbanco;

/**
 * Created by rodrigo on 21/11/15.
 */
public enum TabelaCategoria {
    COLUMN_ID(new Coluna("id", Coluna.Tipo.INTEIRO ,true ,true ,false)),
    COLUMN_NAME(new Coluna("nome", Coluna.Tipo.TEXTO ,false));



    private Coluna coluna;

    private TabelaCategoria(Coluna coluna) {
        this.coluna = coluna;
    }

    public Coluna getColuna() {
        return coluna;
    }
}
