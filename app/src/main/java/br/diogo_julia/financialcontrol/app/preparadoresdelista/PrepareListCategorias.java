package br.diogo_julia.financialcontrol.app.preparadoresdelista;

import android.content.Context;

import br.diogo_julia.financialcontrol.application.DAO.IDAO;
import br.diogo_julia.financialcontrol.application.DAO.categoriadaoimpl.CategoriaDAO;
import br.diogo_julia.financialcontrol.dominio.categoria.Categoria;

import java.util.ArrayList;
import java.util.List;

public class PrepareListCategorias extends AbstractPreparador {

    //list of cards
    String[] selecaoCategorias;
    IDAO dao;

    public PrepareListCategorias(Context context) {
        super(context);
        this.dao = new CategoriaDAO(context);
    }

    @Override
    public String[] prepare(){
        // adicionando os nodes
        List<Categoria> categorias;
        categorias = dao.getList();
        List<String> categoriasString = new ArrayList();

        for(Categoria c : categorias){
            categoriasString.add(c.getNome());
        }

        selecaoCategorias = (String[]) categoriasString.toArray(new String[0]);

        return  selecaoCategorias;
    }

    @Override
    public List prepareList() {
        return null;
    }
}
