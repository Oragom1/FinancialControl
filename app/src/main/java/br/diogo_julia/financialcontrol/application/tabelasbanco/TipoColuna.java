package br.diogo_julia.financialcontrol.application.tabelasbanco;

/**
 * Created by rodrigo on 21/11/15.
 */
public enum TipoColuna {

    INTEIRO("integer"), TEXTO("text"), DECIMAL("real");
    private String tipo;

    private TipoColuna(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.tipo;
    }
}
