package br.diogo_julia.financialcontrol.application.menu;

public enum CartaoDeCreditoMenu {

    FATURA(0,"Fatura"),
    CARTOES(1,"Cartoes");

    int num;
    String nome;
    CartaoDeCreditoMenu(int num, String nome) {
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
