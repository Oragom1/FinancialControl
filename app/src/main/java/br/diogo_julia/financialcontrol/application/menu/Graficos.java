package br.diogo_julia.financialcontrol.application.menu;

public enum Graficos {
    GASTOS(0,"Gastos");

    int num;
    String nome;
    Graficos(int num, String nome) {
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
