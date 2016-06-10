package br.diogo_julia.financialcontrol.dominio.planos;

import br.diogo_julia.financialcontrol.dominio.categoria.Categoria;
import br.diogo_julia.financialcontrol.dominio.entidadededominio.EntidadeDominio;

import java.util.Date;

public class Planos extends EntidadeDominio {

    private String resumo;
    private String descricao;
    private Date prazo;
    private Categoria categoria;

    public Planos(String categoria, String resumo, String descricao, Date prazo, int id_) {
        super.setId(id_);
        this.categoria = new Categoria(categoria);
        this.resumo = resumo;
        this.descricao = descricao;
        this.prazo = prazo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }
}
