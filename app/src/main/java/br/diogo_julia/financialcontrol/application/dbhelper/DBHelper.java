package br.diogo_julia.financialcontrol.application.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.diogo_julia.financialcontrol.application.tabelasbanco.CategoriasTb;
import br.diogo_julia.financialcontrol.application.tabelasbanco.GenerateStringCreateTable;
import br.diogo_julia.financialcontrol.application.tabelasbanco.Tabelas;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "controle.db";
    public static final int DATABASE_VERSION = 4;

    private static DBHelper singleton = null;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(singleton != null) {
            throw new IllegalStateException("DBHelper so pode ser instanciado uma vez.");
        }
    }

    public static DBHelper getInstance(Context context) {
        if (singleton == null){
            singleton = new DBHelper(context);
        }
        return  singleton;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {

            GenerateStringCreateTable geradorTabelas = new GenerateStringCreateTable(db);
            geradorTabelas.gerar();

            db.execSQL(populaTabelaCategoriasIniciais("Alimentacao"));
            db.execSQL(populaTabelaCategoriasIniciais("Salario"));
            db.execSQL(populaTabelaCategoriasIniciais("Transporte"));
            db.execSQL(populaTabelaCategoriasIniciais("Lazer"));
            Log.d("POPULE_TABLE", "dados inseridos na tabela categoria");

            db.setTransactionSuccessful();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion != oldVersion){
            db.execSQL("drop table " + Tabelas.TABLE_CREDITO.getNomeTabela());
        }
        onCreate(db);
    }

    public String populaTabelaCategoriasIniciais(String nome){
        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO ");
        sb.append(Tabelas.TABLE_CATEGORIAS.getNomeTabela() + "(");
        sb.append(CategoriasTb.COLUMN_NAME.getColuna() + ")");
        sb.append(" VALUES('"+ nome +"');");

        String retorno = sb.toString();
        return retorno;
    }

}
