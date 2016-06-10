package br.diogo_julia.financialcontrol.application.DAO.planosdaoimpl;

import android.content.Context;
import android.database.Cursor;

import br.diogo_julia.financialcontrol.application.DAO.AbstractDAO;
import br.diogo_julia.financialcontrol.application.DAO.IDAO;
import br.diogo_julia.financialcontrol.dominio.planos.Planos;
import br.diogo_julia.financialcontrol.application.tabelasbanco.PlanosTb;
import br.diogo_julia.financialcontrol.application.tabelasbanco.Tabelas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanosDAO extends AbstractDAO implements IDAO<Planos> {

    StringBuilder sb;
    Planos plano;
    public PlanosDAO(Context context) {
        super(context);
    }
    @Override
    public long insert(Planos object) {
        db.beginTransaction();
        try{
            db.execSQL("INSERT INTO " + Tabelas.TABLE_PLANOS.getNomeTabela() + "("
                    + PlanosTb.COLUMN_DESCRICAO.getColuna() + ", "
                    + PlanosTb.COLUMN_RESUMO.getColuna() + ", "
                    + PlanosTb.COLUMN_PRAZO.getColuna() + ", "
                    + PlanosTb.COLUMN_CATEGORIA.getColuna() + ") VALUES ("
                    + object.getDescricao() + ", "
                    + object.getResumo() + ", "
                    + object.getPrazo().getTime() + ", "
                    + object.getCategoria().getNome() + ");");
            db.setTransactionSuccessful();
            return 1;
        }finally {
            db.endTransaction();
            return 0;
        }
    }

    @Override
    public long delete(Planos object) {
        db.beginTransaction();
        try{
            db.execSQL("DELETE FROM " + Tabelas.TABLE_PLANOS.getNomeTabela() + "WHERE " + PlanosTb.COLUMN_ID.getColuna() +" = " + object.getId() + ";");
            db.setTransactionSuccessful();
            return 1;
        }finally {
            db.endTransaction();
            return 0;
        }
    }

    @Override
    public long update(Planos object) {
        db.beginTransaction();
        try{
            db.execSQL("INSERT INTO " + Tabelas.TABLE_PLANOS.getNomeTabela() + "("
                    + PlanosTb.COLUMN_DESCRICAO.getColuna() + ", "
                    + PlanosTb.COLUMN_RESUMO.getColuna() + ", "
                    + PlanosTb.COLUMN_PRAZO.getColuna() + ", "
                    + PlanosTb.COLUMN_CATEGORIA.getColuna() + ") VALUES ("
                    + object.getDescricao() + ", "
                    + object.getResumo() + ", "
                    + object.getPrazo().getTime() + ", "
                    + object.getCategoria().getNome() + ");");
            db.setTransactionSuccessful();
            return 1;
        }finally {
            db.endTransaction();
            return 0;
        }
    }

    @Override
    public Planos getById(long id) {
        List<Planos> planos = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + Tabelas.TABLE_CONTAS.getNomeTabela()
                + "where id = ?;", new String[]{String.valueOf(id)});

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id_ = c.getInt(c.getColumnIndex(PlanosTb.COLUMN_ID.getColuna()));
            Long lData = c.getLong(c.getColumnIndex(PlanosTb.COLUMN_PRAZO.getColuna()));
            Date data = new Date();
            data.setTime(lData);
            String categoria = c.getString(c.getColumnIndex(PlanosTb.COLUMN_CATEGORIA.getColuna()));
            String resumo = c.getString(c.getColumnIndex(PlanosTb.COLUMN_RESUMO.getColuna()));
            String descricao = c.getString(c.getColumnIndex(PlanosTb.COLUMN_DESCRICAO.getColuna()));

            if(!categoria.equals(null) && !resumo.equals(null) && !descricao.equals(null) && data != null){
                plano = new Planos(categoria, resumo, descricao, data, id_);
            }
        }

        return plano;
    }

    @Override
    public List<Planos> getList() {
        List<Planos> planos = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + Tabelas.TABLE_CONTAS.getNomeTabela()
                + ";", new String[]{});

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id_ = c.getInt(c.getColumnIndex(PlanosTb.COLUMN_ID.getColuna()));
            Long lData = c.getLong(c.getColumnIndex(PlanosTb.COLUMN_PRAZO.getColuna()));
            Date data = new Date();
            data.setTime(lData);
            String categoria = c.getString(c.getColumnIndex(PlanosTb.COLUMN_CATEGORIA.getColuna()));
            String resumo = c.getString(c.getColumnIndex(PlanosTb.COLUMN_RESUMO.getColuna()));
            String descricao = c.getString(c.getColumnIndex(PlanosTb.COLUMN_DESCRICAO.getColuna()));

            if(!categoria.equals(null) && !resumo.equals(null) && !descricao.equals(null) && data != null){
                plano = new Planos(categoria, resumo, descricao, data, id_);
                planos.add(plano);
            }
        }

        return planos;
    }
}
