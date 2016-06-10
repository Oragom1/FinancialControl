package br.diogo_julia.financialcontrol.app.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SingleChoiceBandeira extends DialogFragment {

    int position = 0;
    String[] selecao;

    /** Declarando a interface, para invocar a funcao callback implementado na activity  */
    AlertPositiveListenerBandeiras alertPositiveListener;


    public void onPositiveClick(int position) {
        this.position = position;
    }


    /** Interface a ser implementada pela atividade que chama este fragmento, para o botao de OK */
    public interface AlertPositiveListenerBandeiras {
        public void onPositiveClickBandeiras(int position);
    }

    /** Esse metodo callback eh executado quando esse fragmento eh anexado a activity
     * essa funcao garante que a atividade alvo implementa a interface AlertPositiveListenerBandeiras
     * */
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            alertPositiveListener = (AlertPositiveListenerBandeiras) activity;
        }catch(ClassCastException e){
            // The hosting activity does not implemented the interface AlertPositiveListenerBandeiras
            throw new ClassCastException(activity.toString() + " Eh necessario implementar o AlertPositiveListenerBandeiras");
        }
    }

    OnClickListener positiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog)dialog;
            int position = alert.getListView().getCheckedItemPosition();
            alertPositiveListener.onPositiveClickBandeiras(position);
        }
    };

    OnClickListener negativeListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            return;
        }
    };


    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");

        selecao = prepareListData();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Escolha a bandeira");
        builder.setPositiveButton("OK", positiveListener);
        builder.setNegativeButton("Cancelar", negativeListener);
        builder.setSingleChoiceItems(prepareListData(), position, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog alert = (AlertDialog) dialog;
                int position = alert.getListView().getCheckedItemPosition();
                String valor = selecao[position];
            }
        });

        AlertDialog dialog = builder.create();
        registerForContextMenu(dialog.getListView());
        return dialog;
    }




    private String[] prepareListData(){
        // adicionando os nodes

        List<String> bandeiras = new ArrayList();
        bandeiras.add("Visa");
        bandeiras.add("MasterCard");
        bandeiras.add("Elo");
        bandeiras.add("Dinners");

        return (String[]) bandeiras.toArray(new String[0]);
    }

}
