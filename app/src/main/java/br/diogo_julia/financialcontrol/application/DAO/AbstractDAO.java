package br.diogo_julia.financialcontrol.application.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.diogo_julia.financialcontrol.application.dbhelper.DBHelper;

public abstract class AbstractDAO{

    public SQLiteDatabase db;
    private DBHelper dbHelper;

    public AbstractDAO(Context context) {
        this.dbHelper = DBHelper.getInstance(context);
        this.db = dbHelper.getWritableDatabase();
    }

}
