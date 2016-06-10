package br.diogo_julia.financialcontrol.application.tabelasbanco;

/**
 * Created by rodrigo on 21/11/15.
 */
public enum TabelaConta {
    COLUMN_ID(new Coluna("id", Coluna.Tipo.INTEIRO ,true ,true ,false)),
    COLUMN_CATEGORIA(new Coluna("categoria", Coluna.Tipo.TEXTO, false)),
    COLUMN_VALOR(new Coluna("valor", Coluna.Tipo.DECIMAL ,false)),
    COLUMN_TIPO(new Coluna("tipo", Coluna.Tipo.TEXTO ,false)),
    COLUMN_DATA(new Coluna("data", Coluna.Tipo.INTEIRO,false)),
    COLUMN_CARTAO(new Coluna("cartao", Coluna.Tipo.TEXTO,true)),
    COLUMN_QTDPARCELAS(new Coluna("qtdparcelas", Coluna.Tipo.INTEIRO,true));

    private Coluna coluna;

    private TabelaConta(Coluna coluna) {
        this.coluna = coluna;
    }

    public Coluna getColuna() {
        return coluna;
    }
}
