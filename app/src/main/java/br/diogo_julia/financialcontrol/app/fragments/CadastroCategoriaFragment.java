package br.diogo_julia.financialcontrol.app.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;


import br.diogo_julia.financialcontrol.application.DAO.categoriadaoimpl.CategoriaDAO;
import br.diogo_julia.financialcontrol.dominio.categoria.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CadastroCategoriaFragment extends DialogFragment {

    SingleChoiceCategoria.AlertPositiveListener alertPositiveListener;
    private EditText mEditText;
    private Categoria categoria;
    private CategoriaDAO categoriaDao;

    String[] selecaoCategorias;

    int position = 0;

    SQLiteDatabase database;
    SQLiteOpenHelper dbHelper;

    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        try{
            alertPositiveListener = (SingleChoiceCategoria.AlertPositiveListener) activity;
        }catch(ClassCastException e){
            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " must implement AlertPositiveListener");
        }
    }


    public CadastroCategoriaFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        categoriaDao = new CategoriaDAO(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_cadastro_categoria,null);
        mEditText = (EditText) view.findViewById(R.id.txtCadastroCategoria);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("Cadastro de Categoria");
        builder.setPositiveButton("OK", positiveListener);
        builder.setNegativeButton("Cancelar", negativeListener);

        return builder.create();
    }

    public void onPositiveClick(int position) {
        this.position = position;

        /** Getting the reference of the textview from the main layout */
        TextView categoria = mEditText;

        /** Setting the selected android version in the textview */
        categoria.setText(selecaoCategorias[this.position]);
    }

    private void prepareListData(){
        // adicionando os nodes
        List<Categoria> categorias = new ArrayList();
        categorias = categoriaDao.getList();
        List<String> categoriasString = new ArrayList();

        for(Categoria c : categorias){
            categoriasString.add(c.getNome());
        }

        selecaoCategorias = (String[]) categoriasString.toArray(new String[0]);
    }

    DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            categoriaDao = new CategoriaDAO(getActivity());
            categoria = new Categoria(mEditText.getText().toString());
            categoriaDao.insert(categoria);
            prepareListData();

            int position = selecaoCategorias.length-1;
            alertPositiveListener.onPositiveClick(position);

        }
    };

    DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            getDialog().dismiss();
        }
    };
}
