package br.diogo_julia.financialcontrol.application.menu;

public enum ResumoMenu {

    DETALHES(0,"Detalhes");

    int num;
    String nome;
    ResumoMenu(int num, String nome) {
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