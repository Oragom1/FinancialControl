package br.diogo_julia.financialcontrol.application.menu;

public enum PlanejamentoFinanceiro {
    MENSAL(0,"Mensal"),
    ANUAL(1,"Anual"),
    INVESTIMENTOS(0,"Mensal");

    int num;
    String nome;
    PlanejamentoFinanceiro(int num, String nome) {
        this.num = num;this.nome=nome;
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