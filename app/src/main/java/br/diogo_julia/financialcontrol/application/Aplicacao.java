package br.diogo_julia.financialcontrol.application;

import android.app.Application;

import br.diogo_julia.financialcontrol.application.dbhelper.DBHelper;

public class Aplicacao extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.getInstance(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBHelper.getInstance(this).close();
    }
}
