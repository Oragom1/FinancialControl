package br.diogo_julia.financialcontrol.application.DAO.categoriadaoimpl;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import br.diogo_julia.financialcontrol.application.DAO.AbstractDAO;
import br.diogo_julia.financialcontrol.application.DAO.IDAO;
import br.diogo_julia.financialcontrol.dominio.categoria.Categoria;
import br.diogo_julia.financialcontrol.application.tabelasbanco.CategoriasTb;
import br.diogo_julia.financialcontrol.application.tabelasbanco.Tabelas;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO extends AbstractDAO implements IDAO<Categoria>{

    StringBuilder sb;
    Categoria categoria;

    public CategoriaDAO(Context context) {
        super(context);
    }

    @Override
    public long insert(Categoria object) {
        db.beginTransaction();
        try {

            db.execSQL("INSERT INTO " + Tabelas.TABLE_CATEGORIAS.getNomeTabela()
                    + "(" + CategoriasTb.COLUMN_NAME.getColuna() + ") VALUES ('"
                    + object.getNome().toString() + "');");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return 0;
    }

    @Override
    public long delete(Categoria object) {
        db.beginTransaction();
        try{
            sb.append("DELETE FROM ");
            sb.append(Tabelas.TABLE_CATEGORIAS.getNomeTabela());
            sb.append(" WHERE ");
            sb.append(CategoriasTb.COLUMN_ID.getColuna());
            sb.append(" = ");
            sb.append(object.getId());
            sb.append(";");
            String sql = sb.toString();
            db.execSQL(sql);
            db.setTransactionSuccessful();
            return 1;
        }catch (SQLException e){
            e.getMessage();
            return 0;
        }finally {
            db.endTransaction();
            return 1;
        }
    }


    public long deleteByName(String nome) {
        db.beginTransaction();
        sb = new StringBuilder();
        try{
            sb.append("DELETE FROM ");
            sb.append(Tabelas.TABLE_CATEGORIAS.getNomeTabela());
            sb.append(" WHERE ");
            sb.append(CategoriasTb.COLUMN_NAME.getColuna());
            sb.append(" = '");
            sb.append(nome);
            sb.append("';");
            String sql = sb.toString();
            db.execSQL(sql);
            db.setTransactionSuccessful();
            return 1;
        }catch (SQLException e){
            e.getMessage();
            return 0;
        }finally {
            db.endTransaction();
            return 1;
        }
    }

    @Override
    public long update(Categoria object) {
        return 0;
    }

    public long updateByName(String nome, String antigo) {
        db.beginTransaction();
        sb = new StringBuilder();
        try{ //UPDATE table_name SET column1 = value1 WHERE [condition];
            //update categorias set nome = 'um' where nome = 'Outra';
            sb.append("UPDATE ");
            sb.append(Tabelas.TABLE_CATEGORIAS.getNomeTabela());
            sb.append(" SET ");
            sb.append(CategoriasTb.COLUMN_NAME.getColuna());
            sb.append(" = '" + nome);
            sb.append("' WHERE ");
            sb.append(CategoriasTb.COLUMN_NAME.getColuna());
            sb.append(" = '");
            sb.append(antigo);
            sb.append("';");
            String sql = sb.toString();
            db.execSQL(sql);
            db.setTransactionSuccessful();
            return 1;
        }catch (SQLException e){
            e.getMessage();
            return 0;
        }finally {
            db.endTransaction();
            return 1;
        }
    }

    @Override
    public Categoria getById(long id) {
        Cursor c = db.rawQuery("select * from " + Tabelas.TABLE_CATEGORIAS.getNomeTabela(), new String[]{});

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id_ = c.getInt(c.getColumnIndex(CategoriasTb.COLUMN_ID.getColuna()));
            String categoriaString = c.getString(c.getColumnIndex(CategoriasTb.COLUMN_NAME.getColuna()));
            categoria = new Categoria(categoriaString, id_);
        }
        return categoria;
    }

    public Categoria getByName(String name) {
        Cursor c = db.rawQuery("select * from " + Tabelas.TABLE_CATEGORIAS.getNomeTabela() + " where " + CategoriasTb.COLUMN_NAME.getColuna() + " like ?;", new String[]{name});

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id_ = c.getInt(c.getColumnIndex(CategoriasTb.COLUMN_ID.getColuna()));
            String categoriaString = c.getString(c.getColumnIndex(CategoriasTb.COLUMN_NAME.getColuna()));
            categoria = new Categoria(categoriaString, id_);
        }
        return categoria;
    }

    @Override
    public List<Categoria> getList() {
        List<Categoria> categorias = new ArrayList();
        Cursor c = db.rawQuery("select * from " + Tabelas.TABLE_CATEGORIAS.getNomeTabela(), new String[]{});

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id_ = c.getInt(c.getColumnIndex(CategoriasTb.COLUMN_ID.getColuna()));
            String categoriaString = c.getString(c.getColumnIndex(CategoriasTb.COLUMN_NAME.getColuna()));
            categoria = new Categoria(categoriaString, id_);
            categorias.add(categoria);
        }

        return categorias;
    }
}
