package br.diogo_julia.financialcontrol.application.DAO.contadaoimpl;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import br.diogo_julia.financialcontrol.application.DAO.AbstractDAO;
import br.diogo_julia.financialcontrol.application.DAO.IDAO;
import br.diogo_julia.financialcontrol.dominio.categoria.Categoria;
import br.diogo_julia.financialcontrol.dominio.conta.Conta;
import br.diogo_julia.financialcontrol.dominio.resumo.Resumo;
import br.diogo_julia.financialcontrol.app.utils.TipoConta;
import br.diogo_julia.financialcontrol.application.tabelasbanco.ContaTb;
import br.diogo_julia.financialcontrol.application.tabelasbanco.Tabelas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContaDAO extends AbstractDAO implements IDAO<Conta> {
    StringBuilder sb;
    Conta conta;
    public ContaDAO(Context context) {
        super(context);
    }

    @Override
    public long insert(Conta object) {
        sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(Tabelas.TABLE_CONTAS.getNomeTabela() + "(");
        sb.append(ContaTb.COLUMN_CATEGORIA.getColuna() + ", ");
        sb.append(ContaTb.COLUMN_TIPO.getColuna() + ", ");
        sb.append(ContaTb.COLUMN_DATA.getColuna() + ", ");
        sb.append(ContaTb.COLUMN_VALOR.getColuna() + ", ");
        sb.append(ContaTb.COLUMN_CARTAO.getColuna() + ", ");
        sb.append(ContaTb.COLUMN_QTDPARCELAS.getColuna() + ") VALUES('");
        sb.append(object.getCategoria().getNome() + "', '");
        sb.append(object.getTipoConta() + "', '");
        sb.append(object.getData().getTime() + "', '");
        sb.append(object.getValorConta() + "', '");
        sb.append(object.getCartao() + "', '");
        sb.append(object.getQtdParcelas() + "');");

        db.execSQL(sb.toString());
        Log.d("BD", "conta inserida");
        return 0;
    }

    @Override
    public long delete(Conta object) {
        db.beginTransaction();
        try{
            db.execSQL("DELETE FROM " + Tabelas.TABLE_CONTAS.getNomeTabela() + "WHERE " + ContaTb.COLUMN_ID.getColuna() +" = " + object.getId() + ";");
            db.setTransactionSuccessful();
            return 1;
        }finally {
            db.endTransaction();
            return 0;
        }
    }

    @Override
    public long update(Conta object) {
        return 0;
    }

    @Override
    public Conta getById(long id) {
        List<Conta> contas = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + Tabelas.TABLE_CONTAS.getNomeTabela() + "where id = ?;", new String[]{String.valueOf(id)});

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id_ = c.getInt(c.getColumnIndex(ContaTb.COLUMN_ID.getColuna()));
            Long lData = c.getLong(c.getColumnIndex(ContaTb.COLUMN_DATA.getColuna()));
            Date data = new Date();
            data.setTime(lData);
            String categoriaString = c.getString(c.getColumnIndex(ContaTb.COLUMN_CATEGORIA.getColuna()));
            Categoria categoria = new Categoria(categoriaString);
            double valor = c.getDouble(c.getColumnIndex(ContaTb.COLUMN_VALOR.getColuna()));
            String tipo = c.getString(c.getColumnIndex(ContaTb.COLUMN_TIPO.getColuna()));
            String cartao = c.getString(c.getColumnIndex(ContaTb.COLUMN_CARTAO.getColuna()));
            int qtdParcela = c.getInt(c.getColumnIndex(ContaTb.COLUMN_QTDPARCELAS.getColuna()));


            if(data != null && !categoria.equals(null) && !tipo.equals(null)){
                if(cartao.equals(null)||cartao.equals(""))
                    conta = new Conta(data, categoria, valor, tipo, id_);
                else
                    conta = new Conta(data, categoria, valor, tipo, id_, cartao, qtdParcela);
            }
        }


        return conta;
    }

    @Override
    public List<Conta> getList() {
        List<Conta> contas = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + Tabelas.TABLE_CONTAS.getNomeTabela(), new String[]{});

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id_ = c.getInt(c.getColumnIndex(ContaTb.COLUMN_ID.getColuna()));
            Long lData = c.getLong(c.getColumnIndex(ContaTb.COLUMN_DATA.getColuna()));
            Date data = new Date();
            data.setTime(lData);
            String categoriaString = c.getString(c.getColumnIndex(ContaTb.COLUMN_CATEGORIA.getColuna()));
            Categoria categoria = new Categoria(categoriaString);
            double valor = c.getDouble(c.getColumnIndex(ContaTb.COLUMN_VALOR.getColuna()));
            String tipo = c.getString(c.getColumnIndex(ContaTb.COLUMN_TIPO.getColuna()));
            String cartao = c.getString(c.getColumnIndex(ContaTb.COLUMN_CARTAO.getColuna()));
            int qtdParcela = c.getInt(c.getColumnIndex(ContaTb.COLUMN_QTDPARCELAS.getColuna()));


            if(data != null && !categoria.equals(null) && !tipo.equals(null)){
                if(cartao != null ||cartao == "")
                    conta = new Conta(data, categoria, valor, tipo, id_, cartao, qtdParcela);
                else
                    conta = new Conta(data, categoria, valor, tipo, id_);
            }
            contas.add(conta);
        }
        return contas;
    }

    public double getSaldo(){
        double saldo = 0;
        Cursor c = db.rawQuery("select sum(" + ContaTb.COLUMN_VALOR.getColuna() +" as "+ ContaTb.COLUMN_VALOR.getColuna()+") from " + Tabelas.TABLE_CONTAS.getNomeTabela() + "  where ", new String[]{});
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            saldo = c.getDouble(c.getColumnIndex(ContaTb.COLUMN_VALOR.getColuna()));
        }
        return saldo;
    }

    public List<Conta> getListCredito() {
        List<Conta> contas = new ArrayList<>();
        Cursor c = db.rawQuery("select * from " + Tabelas.TABLE_CONTAS.getNomeTabela() + " where " + ContaTb.COLUMN_TIPO.getColuna() + " like ?;", new String[]{TipoConta.CARTAO_CREDITO.getTipo()});

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id_ = c.getInt(c.getColumnIndex(ContaTb.COLUMN_ID.getColuna()));
            Long lData = c.getLong(c.getColumnIndex(ContaTb.COLUMN_DATA.getColuna()));
            Date data = new Date();
            data.setTime(lData);
            String categoriaString = c.getString(c.getColumnIndex(ContaTb.COLUMN_CATEGORIA.getColuna()));
            Categoria categoria = new Categoria(categoriaString);
            double valor = c.getDouble(c.getColumnIndex(ContaTb.COLUMN_VALOR.getColuna()));
            String tipo = c.getString(c.getColumnIndex(ContaTb.COLUMN_TIPO.getColuna()));
            String cartao = c.getString(c.getColumnIndex(ContaTb.COLUMN_CARTAO.getColuna()));
            int qtdParcela = c.getInt(c.getColumnIndex(ContaTb.COLUMN_QTDPARCELAS.getColuna()));


            if(data != null && !categoria.equals(null) && !tipo.equals(null)){
                if(cartao != null ||cartao == "")
                    conta = new Conta(data, categoria, valor, tipo, id_, cartao, qtdParcela);
                else
                    conta = new Conta(data, categoria, valor, tipo, id_);
            }
            contas.add(conta);
        }
        return contas;
    }

    public List<Resumo> getListResumoPorCategoria() {
        List<Resumo> resumo = new ArrayList<>();
        String sql;
        sb = new StringBuilder();
        sb.append("select distinct c.");
        sb.append(ContaTb.COLUMN_CATEGORIA.getColuna());
        sb.append(", aux.soma from ");
        sb.append(Tabelas.TABLE_CONTAS.getNomeTabela());
        sb.append(" c inner join (select ");
        sb.append(ContaTb.COLUMN_CATEGORIA.getColuna());
        sb.append(", sum(");
        sb.append(ContaTb.COLUMN_VALOR.getColuna());
        sb.append(") as soma from ");
        sb.append(Tabelas.TABLE_CONTAS.getNomeTabela());
        sb.append(" group by ");
        sb.append(ContaTb.COLUMN_CATEGORIA.getColuna());
        sb.append(") aux on c.");
        sb.append(ContaTb.COLUMN_CATEGORIA.getColuna());
        sb.append(" = aux.");
        sb.append(ContaTb.COLUMN_CATEGORIA.getColuna());
        sb.append(" and c.");
        sb.append(ContaTb.COLUMN_TIPO.getColuna());
        sb.append(" != '");
        sb.append(TipoConta.ENTRADA.getTipo());
        sb.append("';");
        sql = sb.toString();

        Cursor c = db.rawQuery(sql, new String[]{});

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String categoria = c.getString(c.getColumnIndex(ContaTb.COLUMN_CATEGORIA.getColuna()));
            double valor = c.getDouble(c.getColumnIndex("soma"));

            if(!categoria.equals(null) && valor != 0){
                resumo.add(new Resumo(categoria, valor));
            }
        }
        return resumo;
    }
}
