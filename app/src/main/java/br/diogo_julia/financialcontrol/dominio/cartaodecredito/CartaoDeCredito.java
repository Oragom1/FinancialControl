package br.diogo_julia.financialcontrol.dominio.cartaodecredito;


import br.diogo_julia.financialcontrol.dominio.entidadededominio.EntidadeDominio;

public class CartaoDeCredito extends EntidadeDominio {
    private String nome;
    private String bandeira;



    private String tipo; //nacional ou internacional
    private int dataVencimento;
    private double limite;

    public CartaoDeCredito(String nome, String bandeira, String tipo, int dataVencimento, double limite) {
        this.nome = nome;
        this.bandeira = bandeira;
        this.tipo = tipo;
        this.dataVencimento = dataVencimento;
        this.limite = limite;
    }

    public CartaoDeCredito() {
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public int getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(int dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public String getTipo() {        return tipo;}

    public void setTipo(String tipo) {        this.tipo = tipo;    }
}
