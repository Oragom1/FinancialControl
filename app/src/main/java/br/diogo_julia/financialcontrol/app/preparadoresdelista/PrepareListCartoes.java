package br.diogo_julia.financialcontrol.app.preparadoresdelista;

import android.content.Context;

import br.diogo_julia.financialcontrol.application.DAO.IDAO;
import br.diogo_julia.financialcontrol.application.DAO.creditodaoimpl.CreditoDAO;
import br.diogo_julia.financialcontrol.dominio.cartaodecredito.CartaoDeCredito;

import java.util.ArrayList;
import java.util.List;

public class PrepareListCartoes extends AbstractPreparador {

    //list of cards
    String[] selecaoCartoes;
    IDAO dao;

    public PrepareListCartoes(Context context) {
        super(context);
        this.dao = new CreditoDAO(context);
    }

    @Override
    public String[] prepare(){
        // adicionando os nodes
        List<CartaoDeCredito> cartoes = new ArrayList();
        cartoes = dao.getList();
        List<String> categoriasString = new ArrayList();

        for(CartaoDeCredito c : cartoes){
            categoriasString.add(c.getNome());
        }

        selecaoCartoes = (String[]) categoriasString.toArray(new String[0]);
        return selecaoCartoes;
    }

    @Override
    public List prepareList() {
        return null;
    }
}
