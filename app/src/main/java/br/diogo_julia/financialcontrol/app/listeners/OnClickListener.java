package br.diogo_julia.financialcontrol.app.listeners;

import android.content.DialogInterface;

public class OnClickListener implements DialogInterface.OnClickListener {

    public String[] selecao;
    public int param;
    public OnClickListener(int param, String[] selecao) {
        this.param = param;
        this.selecao = selecao;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        //categoriaDao.deleteByName(selecao[position]);
    }
}
