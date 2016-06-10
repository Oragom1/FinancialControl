package br.diogo_julia.financialcontrol.application.tabelasbanco;

/**
 * Created by rodrigo on 21/11/15.
 */
public enum TabelaCredito {
    COLUMN_ID(new Coluna("id", Coluna.Tipo.INTEIRO ,true ,true ,false)),
    COLUMN_DESCRICAO(new Coluna("descricao", Coluna.Tipo.TEXTO, false)),
    COLUMN_LIMITE(new Coluna("limite", Coluna.Tipo.DECIMAL ,false)),
    COLUMN_BANDEIRA(new Coluna("bandeira", Coluna.Tipo.TEXTO ,false)),
    COLUMN_TIPO(new Coluna("tipo", Coluna.Tipo.TEXTO,true)),
    COLUMN_VENCIMENTO(new Coluna("vencimento", Coluna.Tipo.INTEIRO,true));


    private Coluna coluna;

    private TabelaCredito(Coluna coluna) {
        this.coluna = coluna;
    }

    public Coluna getColuna() {
        return coluna;
    }
}
