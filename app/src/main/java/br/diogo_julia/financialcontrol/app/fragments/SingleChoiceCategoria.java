package br.diogo_julia.financialcontrol.app.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import br.diogo_julia.financialcontrol.application.DAO.categoriadaoimpl.CategoriaDAO;

import br.diogo_julia.financialcontrol.dominio.categoria.Categoria;

import java.util.ArrayList;
import java.util.List;

public class SingleChoiceCategoria extends DialogFragment {

    SQLiteDatabase database;
    SQLiteOpenHelper dbHelper;
    int position = 0;
    CategoriaDAO categoriaDao;
    String[] selecao;

    /** Declarando a interface, para invocar a funcao callback implementado na activity  */
    AlertPositiveListener alertPositiveListener;


    public void onPositiveClick(int position) {
        this.position = position;
    }

    public class OnClickListenerParam implements DialogInterface.OnClickListener {
        public String[] selecao;
        public int param;
        public OnClickListenerParam(int param, String[] selecao) {
            this.param = param;
            this.selecao = selecao;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(categoriaDao.deleteByName(selecao[param]) == 1 ){
                Toast.makeText(getActivity(), "Categoria - " + selecao[param] + " - Excluida com sucesso.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            else
                Toast.makeText(getActivity(), "Erro ao excluir.", Toast.LENGTH_SHORT).show();

            this.selecao = prepareListData();
        }
    }

    public class OnClickListenerEdit implements DialogInterface.OnClickListener {
        public String[] selecao;
        public int param;
        public OnClickListenerEdit(int param, String[] selecao) {
            this.param = param;
            this.selecao = selecao;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog) dialog;
            EditText mEditText = (EditText) alert.findViewById(R.id.txtCadastroCategoria);
            Categoria categoria = new Categoria(mEditText.getText().toString());

            if(categoriaDao.getByName(categoria.getNome()) != null){
                Toast.makeText(getActivity(), "Nao foi possivel alterar pois ja existe uma categoria com esse nome.", Toast.LENGTH_SHORT).show();
            }else{
                categoriaDao.updateByName(mEditText.getText().toString() ,selecao[param]);
                Toast.makeText(getActivity(), "Categoria - " + selecao[param] + " - Alterada com sucesso.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            this.selecao = prepareListData();

            position = selecao.length-1;
            alertPositiveListener.onPositiveClick(position);
        }
    }

    /** Interface a ser implementada pela atividade que chama este fragmento, para o botao de OK */
    public interface AlertPositiveListener {
        public void onPositiveClick(int position);
    }

    /** Esse metodo callback eh executado quando esse fragmento eh anexado a activity
     * essa funcao garante que a atividade alvo implementa a interface AlertPositiveListener
     * */
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        try{
            alertPositiveListener = (AlertPositiveListener) activity;
        }catch(ClassCastException e){
            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " Eh necessario implementar o AlertPositiveListener");
        }
    }

    OnClickListener positiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog)dialog;
            int position = alert.getListView().getCheckedItemPosition();
            alertPositiveListener.onPositiveClick(position);
        }
    };

    OnClickListener negativeListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            return;
        }
    };


    AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView v1, View v, int index, long id) {
         return false;
        }
    };

    OnClickListener adicionarCategoriaListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //abrirPopupCadastroCategoria(getView());
            View view = getActivity().getLayoutInflater().inflate(R.layout.activity_cadastro_categoria,null);
            EditText mEditText = (EditText) view.findViewById(R.id.txtCadastroCategoria);
            new AlertDialog.Builder(getActivity())
                    .setTitle("Categoria")
                    .setView(view)
                    .setMessage("Digite o nome da categoria")
                    .setIcon(android.R.drawable.ic_input_add)
                    .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                        Activity activity = getActivity();
                        public void onClick(DialogInterface dialog, int whichButton) {
                            AlertDialog alert = (AlertDialog) dialog;
                            EditText mEditText = (EditText) alert.findViewById(R.id.txtCadastroCategoria);
                            Categoria categoria = new Categoria(mEditText.getText().toString());

                                categoriaDao.insert(categoria);
                                Toast.makeText(activity, "Categoria " + categoria.getNome() + " foi inserida com sucesso com sucesso.", Toast.LENGTH_SHORT).show();
                            selecao = prepareListData();

                            position = selecao.length-1;
                            alertPositiveListener.onPositiveClick(position);
                            dialog.dismiss();

                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }
    };

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");

        categoriaDao = new CategoriaDAO(getActivity());
        selecao = prepareListData();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Escolha a categoria");
        builder.setPositiveButton("OK", positiveListener);
        builder.setNegativeButton("Cancelar", negativeListener);
        builder.setNeutralButton("Nova Categoria", adicionarCategoriaListener);
        builder.setSingleChoiceItems(prepareListData(), position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog alert = (AlertDialog) dialog;
                int position = alert.getListView().getCheckedItemPosition();
                String valor = selecao[position];
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getListView().setOnItemLongClickListener(longClickListener);
        registerForContextMenu(dialog.getListView());
        return dialog;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        String[] menuItens = getResources().getStringArray(R.array.menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        MenuItem.OnMenuItemClickListener listener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int pos = info.position;
                switch (item.getItemId()){
                    case 0:
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Atenção!")
                                .setMessage("Realmente deseja excluir a categoria " + selecao[pos] + "?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new OnClickListenerParam(pos, selecao))
                                .setNegativeButton(android.R.string.no, null).show();
                        break;
                    case 1:
                        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_cadastro_categoria,null);
                        EditText mEditText = (EditText) view.findViewById(R.id.txtCadastroCategoria);
                        mEditText.setText(categoriaDao.getByName(selecao[pos]).getNome());
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Categoria")
                                .setView(view)
                                .setMessage("Digite o nome da categoria")
                                .setIcon(android.R.drawable.ic_input_add)
                                .setPositiveButton("Adicionar", new OnClickListenerEdit(pos,selecao))
                                .setNegativeButton(android.R.string.no, null).show();
                        break;
                }
                return true;
            }
        };

        for (int i = 0 ; i < menuItens.length ; i++) {
            menu.add(Menu.NONE, i, i, menuItens[i]);
            menu.getItem(i).setOnMenuItemClickListener(listener);
        }
    }


    private String[] prepareListData(){
        // adicionando os nodes
        List<Categoria> categorias = new ArrayList();
        categorias = categoriaDao.getList();
        List<String> categoriasString = new ArrayList();

        for(Categoria c : categorias){
            categoriasString.add(c.getNome());
        }
        return (String[]) categoriasString.toArray(new String[0]);
    }

    public void abrirPopupCadastroCategoria(View v){
        CadastroCategoriaFragment cadastrarCategoriaDialog = new CadastroCategoriaFragment();
        cadastrarCategoriaDialog.show(getFragmentManager(), "fragment_edit_name");
    }


}
