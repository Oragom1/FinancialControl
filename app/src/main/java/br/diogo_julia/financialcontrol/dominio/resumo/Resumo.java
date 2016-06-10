package br.diogo_julia.financialcontrol.dominio.resumo;

import br.diogo_julia.financialcontrol.dominio.entidadededominio.EntidadeDominio;

/**
 * Created by rodrigo on 17/11/15.
 */
public class Resumo extends EntidadeDominio {
    private String categoria;
    private double soma;

    public Resumo(String categoria, double soma) {
        this.categoria = categoria;
        this.soma = soma;
    }

    public Resumo() {
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getSoma() {
        return soma;
    }

    public void setSoma(double soma) {
        this.soma = soma;
    }
}
