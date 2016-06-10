package br.diogo_julia.financialcontrol.application.DAO.creditodaoimpl;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import br.diogo_julia.financialcontrol.application.DAO.AbstractDAO;
import br.diogo_julia.financialcontrol.application.DAO.IDAO;
import br.diogo_julia.financialcontrol.dominio.cartaodecredito.CartaoDeCredito;
import br.diogo_julia.financialcontrol.application.tabelasbanco.CreditoTb;
import br.diogo_julia.financialcontrol.application.tabelasbanco.Tabelas;

import java.util.ArrayList;
import java.util.List;

public class CreditoDAO extends AbstractDAO implements IDAO<CartaoDeCredito> {

    StringBuilder sb;
    CartaoDeCredito cartao;

    public CreditoDAO(Context context) {
        super(context);
    }

    @Override
    public long insert(CartaoDeCredito object) {

        sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(Tabelas.TABLE_CREDITO.getNomeTabela() + "(");
        sb.append(CreditoTb.COLUMN_DESCRICAO.getColuna() + ", ");
        sb.append(CreditoTb.COLUMN_LIMITE.getColuna() + ", ");
        sb.append(CreditoTb.COLUMN_BANDEIRA.getColuna() + ", ");
        sb.append(CreditoTb.COLUMN_TIPO.getColuna() + ", ");
        sb.append(CreditoTb.COLUMN_VENCIMENTO.getColuna() + ") VALUES('");
        sb.append(object.getNome() + "', '");
        sb.append(object.getLimite() + "', '");
        sb.append(object.getBandeira() + "', '");
        sb.append(object.getTipo() + "', '");
        sb.append(object.getDataVencimento() + "');");

        db.execSQL(sb.toString());
        Log.d("BD", "conta inserida");

        return 0;

    }

    @Override
    public long delete(CartaoDeCredito object) {
        db.beginTransaction();
        try{
            db.execSQL("DELETE FROM " + Tabelas.TABLE_CREDITO.getNomeTabela() + "WHERE " + CreditoTb.COLUMN_ID.getColuna() +" = " + object.getId() + ";");
            db.setTransactionSuccessful();
            return 1;
        }finally {
            db.endTransaction();
            return 0;
        }
    }

    public long deleteByName(String nome) {
        db.beginTransaction();
        sb = new StringBuilder();
        try{
            sb.append("DELETE FROM ");
            sb.append(Tabelas.TABLE_CREDITO.getNomeTabela());
            sb.append(" WHERE ");
            sb.append(CreditoTb.COLUMN_DESCRICAO.getColuna());
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
    public long update(CartaoDeCredito object) {
        db.beginTransaction();
        sb = new StringBuilder();
        try{ //UPDATE table_name SET column1 = value1 WHERE [condition];
            //update categorias set nome = 'um' where nome = 'Outra';
            sb.append("UPDATE ");
            sb.append(Tabelas.TABLE_CREDITO.getNomeTabela() + " SET ");
            sb.append(CreditoTb.COLUMN_DESCRICAO.getColuna());
            sb.append( " = '");
            sb.append( object.getNome()+"', ");
            sb.append(CreditoTb.COLUMN_LIMITE.getColuna());
            sb.append( " = '");
            sb.append( object.getLimite()+"', ");
            sb.append(CreditoTb.COLUMN_BANDEIRA.getColuna());
            sb.append( " = '");
            sb.append( object.getBandeira()+"', ");
            sb.append(CreditoTb.COLUMN_TIPO.getColuna());
            sb.append( " = '");
            sb.append( object.getTipo()+"', ");
            sb.append(CreditoTb.COLUMN_VENCIMENTO.getColuna());
            sb.append( " = '");
            sb.append( object.getDataVencimento()+"'");
            sb.append(" where ");

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

    public long updateByName(CartaoDeCredito object, String antigo) {
        db.beginTransaction();
        sb = new StringBuilder();
        try{ //UPDATE table_name SET column1 = value1 WHERE [condition];
            //update categorias set nome = 'um' where nome = 'Outra';
            sb.append("UPDATE ");
            sb.append(Tabelas.TABLE_CREDITO.getNomeTabela() + " SET ");
            sb.append(CreditoTb.COLUMN_DESCRICAO.getColuna());
            sb.append( " = '");
            sb.append( object.getNome()+"', ");
            sb.append(CreditoTb.COLUMN_LIMITE.getColuna());
            sb.append( " = '");
            sb.append( object.getLimite()+"', ");
            sb.append(CreditoTb.COLUMN_BANDEIRA.getColuna());
            sb.append( " = '");
            sb.append( object.getBandeira()+"', ");
            sb.append(CreditoTb.COLUMN_TIPO.getColuna());
            sb.append( " = '");
            sb.append( object.getTipo()+"', ");
            sb.append(CreditoTb.COLUMN_VENCIMENTO.getColuna());
            sb.append( " = '");
            sb.append( object.getDataVencimento()+"'");
            sb.append(" where ");
            sb.append(CreditoTb.COLUMN_DESCRICAO.getTipo());
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
    public CartaoDeCredito getById(long id) {
        Cursor c = db.rawQuery("select * from " + Tabelas.TABLE_CREDITO.getNomeTabela() + "where id = ?;", new String[]{String.valueOf(id)});
        String nome;
        String bandeira;
        String tipo;
        int dataVencimento;
        double limite;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id_ = c.getInt(c.getColumnIndex(CreditoTb.COLUMN_ID.getColuna()));
            nome = c.getString(c.getColumnIndex(CreditoTb.COLUMN_DESCRICAO.getColuna()));
            bandeira = c.getString(c.getColumnIndex(CreditoTb.COLUMN_BANDEIRA.getColuna()));
            tipo = c.getString(c.getColumnIndex(CreditoTb.COLUMN_TIPO.getColuna()));
            dataVencimento = c.getInt(c.getColumnIndex(CreditoTb.COLUMN_VENCIMENTO.getColuna()));
            limite = c.getDouble(c.getColumnIndex(CreditoTb.COLUMN_LIMITE.getColuna()));

            cartao = new CartaoDeCredito(nome,bandeira,tipo,dataVencimento,limite);
        }

        return cartao;
    }

    @Override
    public List<CartaoDeCredito> getList() {
        List<CartaoDeCredito> cartoes = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + Tabelas.TABLE_CREDITO.getNomeTabela(), new String[]{});
        String nome;
        String bandeira;
        String tipo;
        int dataVencimento;
        double limite;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id_ = c.getInt(c.getColumnIndex(CreditoTb.COLUMN_ID.getColuna()));
            nome = c.getString(c.getColumnIndex(CreditoTb.COLUMN_DESCRICAO.getColuna()));
            bandeira = c.getString(c.getColumnIndex(CreditoTb.COLUMN_BANDEIRA.getColuna()));
            tipo = c.getString(c.getColumnIndex(CreditoTb.COLUMN_TIPO.getColuna()));
            dataVencimento = c.getInt(c.getColumnIndex(CreditoTb.COLUMN_VENCIMENTO.getColuna()));
            limite = c.getDouble(c.getColumnIndex(CreditoTb.COLUMN_LIMITE.getColuna()));

            cartao = new CartaoDeCredito(nome,bandeira,tipo,dataVencimento,limite);
            cartao.setId(id_);
            cartoes.add(cartao);
        }
        return cartoes;
    }

    public double getLimiteTotal(){
        double saldo = 0;
        Cursor c = db.rawQuery("select sum(" + CreditoTb.COLUMN_LIMITE.getColuna() +") as "+CreditoTb.COLUMN_LIMITE.getColuna()+" from " + Tabelas.TABLE_CREDITO.getNomeTabela() + ";", new String[]{});
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            saldo = c.getDouble(c.getColumnIndex(CreditoTb.COLUMN_LIMITE.getColuna()));
        }

        return saldo;
    }

    public CartaoDeCredito getByName(String nome) {
        Cursor c = db.rawQuery("select * from " + Tabelas.TABLE_CREDITO.getNomeTabela() + "where "+ CreditoTb.COLUMN_DESCRICAO.getColuna()+" = ?;", new String[]{String.valueOf(nome)});
        String name;
        String bandeira;
        String tipo;
        int dataVencimento;
        double limite;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id_ = c.getInt(c.getColumnIndex(CreditoTb.COLUMN_ID.getColuna()));
            name = c.getString(c.getColumnIndex(CreditoTb.COLUMN_DESCRICAO.getColuna()));
            bandeira = c.getString(c.getColumnIndex(CreditoTb.COLUMN_BANDEIRA.getColuna()));
            tipo = c.getString(c.getColumnIndex(CreditoTb.COLUMN_TIPO.getColuna()));
            dataVencimento = c.getInt(c.getColumnIndex(CreditoTb.COLUMN_VENCIMENTO.getColuna()));
            limite = c.getDouble(c.getColumnIndex(CreditoTb.COLUMN_LIMITE.getColuna()));

            cartao = new CartaoDeCredito(name,bandeira,tipo,dataVencimento,limite);
        }

        return cartao;
    }
}
