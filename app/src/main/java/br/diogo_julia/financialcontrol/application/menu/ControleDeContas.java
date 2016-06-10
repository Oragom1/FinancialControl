package br.diogo_julia.financialcontrol.application.menu;

public enum ControleDeContas {
    CONTAS(0,"Contas");

    int num;
    String nome;
    ControleDeContas(int num, String nome) {
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