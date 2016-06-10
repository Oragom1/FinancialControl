package br.diogo_julia.financialcontrol.application.menu;

public class NomeSubMenu {
    private String nome;

    public NomeSubMenu(String nome) {
        this.nome = nome;
    }

    // Métodos de acesso.
    public String getNome() {
        return nome;
    }


    @Override
    public String toString() {
        StringBuilder representacaoTextual = new StringBuilder(nome);
        return representacaoTextual.toString();
    }

}
