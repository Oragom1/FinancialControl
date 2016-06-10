package br.diogo_julia.financialcontrol.application.menu;

public enum MenuPrincipal {
    CONTROLE_DE_CONTAS(0,"Controle De Contas"),
    CARTAO_DE_CREDITO(1,"Cartão de Crédito"),
    PLANEJAMENTO_FINANCEIRO(2,"Planejamento Financeiro"),
    RESUMO(3,"Resumo"),
    GRAFICOS(4,"Gráficos");

    int num;String nome;
    MenuPrincipal(int num, String nome) {
        this.num = num;
        this.nome=nome;
    }

    public int getNum() {
        return num;
    }
    public String getNome() {
        return nome;
    }
    @Override
    public String toString() {
        return nome;
    }

}
