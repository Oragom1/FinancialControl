package br.diogo_julia.financialcontrol.application.tabelasbanco;

/**
 * Created by rodrigo on 21/11/15.
 */
public enum TabelaPlanos {
    COLUMN_ID(new Coluna("id", Coluna.Tipo.INTEIRO ,true ,true ,false)),
    COLUMN_CATEGORIA(new Coluna("categoria", Coluna.Tipo.TEXTO, false)),
    COLUMN_RESUMO(new Coluna("resumo", Coluna.Tipo.DECIMAL ,false)),
    COLUMN_PRAZO(new Coluna("prazo", Coluna.Tipo.INTEIRO ,false)),
    COLUMN_DESCRICAO(new Coluna("descricao", Coluna.Tipo.TEXTO,true));

    private Coluna coluna;

    private TabelaPlanos(Coluna coluna) {
        this.coluna = coluna;
    }

    public Coluna getColuna() {
        return coluna;
    }
}
