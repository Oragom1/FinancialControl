package br.diogo_julia.financialcontrol.app.preparadoresdelista;

import android.content.Context;

import java.util.List;

public abstract class AbstractPreparador {

    Context context;

    public AbstractPreparador(Context context) {
        this.context = context;
    }

    public abstract String[] prepare();
    public abstract List prepareList();
}
