package br.diogo_julia.financialcontrol.app.utils;

import android.app.AlertDialog;
import android.content.Context;

import java.util.Date;

public class Mensagem {

    private Context context;

    public Mensagem(Context context){
        this.context = context;
    }

    public void makeMsg(String msg){

        AlertDialog.Builder mensagem = new AlertDialog.Builder(context);
        mensagem.setMessage(msg);
        mensagem.setIcon(android.R.drawable.ic_dialog_alert);
        mensagem.setNeutralButton("OK", null);
        mensagem.setTitle("Dados");
        mensagem.show();
    }

    public void makeMsgDados(String nome, double valor, String tipo, Date data){

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        df.format("dd/mm/YYYY", data);

        AlertDialog.Builder mensagem = new AlertDialog.Builder(
               context);
        mensagem.setMessage("Nome: " + nome
                + "\nValor: " + valor
                + "\nTipo: " + tipo
                + "\nData: " + df.format("dd/MM/yyyy", data));
        mensagem.setNeutralButton("OK", null);
        mensagem.setTitle("Dados da conta");
        mensagem.show();
    }
}
