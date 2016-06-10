package br.diogo_julia.financialcontrol.app.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.diogo_julia.financialcontrol.R;
import br.diogo_julia.financialcontrol.app.activities.cartaodecredito.CadastroCartaoCreditoActivity;
import br.diogo_julia.financialcontrol.app.utils.Mensagem;
import br.diogo_julia.financialcontrol.application.DAO.creditodaoimpl.CreditoDAO;
import br.diogo_julia.financialcontrol.dominio.cartaodecredito.CartaoDeCredito;

public class SingleChoiceCartao extends DialogFragment {

    SQLiteDatabase database;
    SQLiteOpenHelper dbHelper;
    int position = 0;
    CreditoDAO creditoDao;
    String[] selecao;

    EditText edtTxtNome;
    EditText edtTxtLimite;
    EditText edtTxtFatura;
    RadioGroup rdGroupTipo;
    RadioButton rdButtonTipo;
    RadioGroup rdGroupBandeira;
    RadioButton rdButtonBandeira;

    String nome;
    Double limite;
    int fatura;
    String bandeira;
    String tipo;

    /** Declaring the interface, to invoke a callback function in the implementing activity class */
    AlertPositiveListenerCartao alertPositiveListener;


    public void onPositiveClickCartao(int position) {
        this.position = position;
    }



    /** An interface to be implemented in the hosting activity for "OK" button click listener */
    public interface AlertPositiveListenerCartao {
        public void onPositiveClickCartao(int position);
    }

    /** This is a callback method executed when this fragment is attached to an activity.
     *  This function ensures that, the hosting activity implements the interface AlertPositiveListener
     * */
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        try{
            alertPositiveListener = (AlertPositiveListenerCartao) activity;
        }catch(ClassCastException e){
            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " must implement AlertPositiveListener");
        }
    }

    OnClickListener positiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog)dialog;
            int position = alert.getListView().getCheckedItemPosition();
            alertPositiveListener.onPositiveClickCartao(position);
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

    OnClickListener adicionarCartaoListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.content_cadastro_cartao_credito_fragment,null);
            edtTxtNome = (EditText) view.findViewById(R.id.edtTxtNome);
            edtTxtLimite = (EditText) view.findViewById(R.id.edtTxtLimite);
            edtTxtFatura = (EditText) view.findViewById(R.id.edtTxtFatura);
            rdGroupTipo = (RadioGroup) view.findViewById(R.id.rdGrpInternacional);
            rdGroupBandeira=(RadioGroup) view.findViewById(R.id.rdGrpBandeira);

            new AlertDialog.Builder(getActivity())
                    .setTitle("Cartao")
                    .setView(view)
                    .setIcon(android.R.drawable.ic_input_add)
                    .setPositiveButton("Adicionar", new OnClickListener() {
                        Activity activity = getActivity();
                        public void onClick(DialogInterface dialog, int whichButton) {
                            AlertDialog alert = (AlertDialog) dialog;

                            if(edtTxtLimite.getText().equals("")){
                                edtTxtLimite.setText("0");
                                limite = Double.valueOf(edtTxtLimite.getText().toString());
                            }else{
                                limite = Double.valueOf(edtTxtLimite.getText().toString());
                            }

                            int selected_id = rdGroupTipo.getCheckedRadioButtonId();
                            rdButtonTipo = (RadioButton) alert.findViewById(selected_id);
                            tipo = rdButtonTipo.getText().toString();

                            int selected_id_bandeira = rdGroupBandeira.getCheckedRadioButtonId();
                            rdButtonBandeira = (RadioButton) alert.findViewById(selected_id_bandeira);
                                bandeira = rdButtonBandeira.getText().toString();

                            nome = edtTxtNome.getText().toString();
                            fatura = Integer.parseInt(edtTxtFatura.getText().toString());

                            CartaoDeCredito cartao = new CartaoDeCredito(nome,bandeira,tipo,fatura,limite);
                            Mensagem msg = new Mensagem(activity);

                                if(!cartao.getNome().isEmpty() || cartao.getLimite() != 0 || cartao.getDataVencimento() != 0 || !cartao.getTipo().isEmpty() || !cartao.getBandeira().isEmpty()){
                                    if(fatura <= 0 || fatura > 31){
                                        msg.makeMsg("Data de Vencimento da fatura deve ser entre 1 e 31.");
                                    }else {
                                        msg.makeMsg("Descricao: " + cartao.getNome() +
                                                "\n" + "Limite: " + cartao.getLimite() +
                                                "\n" + "Dia de Vencimento: " + cartao.getDataVencimento() +
                                                "\n" + "Tipo: " + cartao.getTipo() +
                                                "\n" + "Bandeira: " + cartao.getBandeira());
                                        creditoDao = new CreditoDAO(getActivity());
                                        creditoDao.insert(cartao);
                                        selecao = prepareListData();
                                        position = selecao.length - 1;
                                        alertPositiveListener.onPositiveClickCartao(position);
                                    }
                                }else{

                                    msg.makeMsg("Todos os campos sao obrigatorios, favor preencher os campos em falta.");
                                }
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }
    };

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");

        creditoDao = new CreditoDAO(getActivity());
        selecao = prepareListData();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Escolha o Cartao");
        builder.setPositiveButton("OK", positiveListener);
        builder.setNegativeButton("Cancelar", negativeListener);
        builder.setNeutralButton("Novo Cartao", adicionarCartaoListener);
        builder.setSingleChoiceItems(prepareListData(), position, new OnClickListener() {
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
                                .setMessage("Realmente deseja excluir o cartao " + selecao[pos] + "?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new OnClickListenerParam(pos, selecao))
                                .setNegativeButton(android.R.string.no, null).show();
                        break;
                    case 1:
                        Intent i = new Intent(getActivity(), CadastroCartaoCreditoActivity.class);
                        i.putExtra("descricao", selecao[pos]);
                        startActivity(i);
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

    public class OnClickListenerParam implements DialogInterface.OnClickListener {
        public String[] selecao;
        public int param;
        public OnClickListenerParam(int param, String[] selecao) {
            this.param = param;
            this.selecao = selecao;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(creditoDao.deleteByName(selecao[param]) == 1 )
                Toast.makeText(getActivity(), "Cartao - " + selecao[param] + " - Excluido com sucesso.", Toast.LENGTH_SHORT).show();
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

        }
    }


    private String[] prepareListData(){
        // adicionando os nodes
        List<CartaoDeCredito> cartoes = new ArrayList();
        cartoes = creditoDao.getList();
        List<String> cartoesString = new ArrayList();

        for(CartaoDeCredito c : cartoes){
            cartoesString.add(c.getNome());
        }
        return (String[]) cartoesString.toArray(new String[0]);
    }

    public void abrirPopupCadastroCartoes(View v){
        CadastroCategoriaFragment cadastrarCategoriaDialog = new CadastroCategoriaFragment();
        cadastrarCategoriaDialog.show(getFragmentManager(), "fragment_edit_name");
    }

    public void mensagem(String msg){
        AlertDialog.Builder mensagem = new AlertDialog.Builder(getActivity());
        mensagem.setMessage(msg);
        mensagem.setIcon(android.R.drawable.ic_dialog_alert);
        mensagem.setNeutralButton("OK", null);
        mensagem.setTitle("Dados do Cartao");
        mensagem.show();
    }


}
