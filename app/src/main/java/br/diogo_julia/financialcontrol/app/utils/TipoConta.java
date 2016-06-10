package br.diogo_julia.financialcontrol.app.utils;

public enum TipoConta {
    ENTRADA("entrada"), SAIDA("saida"), CARTAO_CREDITO("credito");

    private String tipo;

    TipoConta(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return this.tipo;
    }
}
