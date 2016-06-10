package br.diogo_julia.financialcontrol.dominio.usuario;

import br.diogo_julia.financialcontrol.dominio.conta.Conta;
import br.diogo_julia.financialcontrol.dominio.entidadededominio.EntidadeDominio;

import java.util.List;

public class Usuario extends EntidadeDominio {
    private String nome = null;
    private br.diogo_julia.financialcontrol.dominio.usuario.Carteira carteira = null;
    private List<Conta> contas = null;
    private List<String> planos = null;

    public Usuario(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
