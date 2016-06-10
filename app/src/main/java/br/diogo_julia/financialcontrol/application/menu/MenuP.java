package br.diogo_julia.financialcontrol.application.menu;

public enum MenuP {
    CONTROLE_DE_CONTAS("Controle De Conta", ControleDeContas.class, 0),
    CARTAO_DE_CREDITO("Cartão de Crédito", CartaoDeCreditoMenu.class, 1),
    RESUMO("Resumo", ResumoMenu.class, 2);

    private String nomeTabela;
    private Class<? extends Enum> enumeracao;
    int pos;
    MenuP(String nomeTabela, Class<? extends Enum> enumeracao, int pos) {
        this.nomeTabela = nomeTabela;
        this.enumeracao = enumeracao;
        this.pos = pos;

    }

    public int getNum() {
        return this.pos;
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
