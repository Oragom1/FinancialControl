package br.diogo_julia.financialcontrol.application.tabelasbanco;

/**
 * Created by rodrigo_sntg on 17/10/2015.
 */
public enum Tabelas{

    TABLE_CONTAS("contas", TabelaConta.class),
    TABLE_PLANOS("planos", TabelaPlanos.class),
    TABLE_CATEGORIAS("categorias", TabelaCategoria.class),
    TABLE_CREDITO("credito", TabelaCredito.class);


    private String nomeTabela;
    private Class<? extends Enum> enumeracao;
    Tabelas(String nomeTabela, Class<? extends Enum> enumeracao) {
        this.nomeTabela = nomeTabela;
        this.enumeracao = enumeracao;

    }

    public String getNomeTabela() {
        return this.nomeTabela;
    }

    public Class<? extends Enum> getEnumeracao() {
        return enumeracao;
    }

    @Override
    public String toString() {
        return this.nomeTabela;
    }

}
