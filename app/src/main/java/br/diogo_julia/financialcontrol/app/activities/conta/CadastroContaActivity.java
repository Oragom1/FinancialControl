package br.diogo_julia.financialcontrol.app.activities.conta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.diogo_julia.financialcontrol.R;
import br.diogo_julia.financialcontrol.app.fragments.SingleChoiceBandeira;
import br.diogo_julia.financialcontrol.app.fragments.SingleChoiceCartao;
import br.diogo_julia.financialcontrol.app.fragments.SingleChoiceCategoria;
import br.diogo_julia.financialcontrol.app.fragments.picker.DatePickerFragment;
import br.diogo_julia.financialcontrol.app.preparadoresdelista.PrepareListCartoes;
import br.diogo_julia.financialcontrol.app.preparadoresdelista.PrepareListCategorias;
import br.diogo_julia.financialcontrol.app.utils.Mensagem;
import br.diogo_julia.financialcontrol.app.utils.TipoConta;
import br.diogo_julia.financialcontrol.application.DAO.categoriadaoimpl.CategoriaDAO;
import br.diogo_julia.financialcontrol.application.DAO.contadaoimpl.ContaDAO;
import br.diogo_julia.financialcontrol.application.DAO.creditodaoimpl.CreditoDAO;
import br.diogo_julia.financialcontrol.application.MainActivity;
import br.diogo_julia.financialcontrol.dominio.cartaodecredito.CartaoDeCredito;
import br.diogo_julia.financialcontrol.dominio.categoria.Categoria;
import br.diogo_julia.financialcontrol.dominio.conta.Conta;

public class CadastroContaActivity extends Activity implements SingleChoiceCategoria.AlertPositiveListener, DatePickerFragment.DatePickerFragmentListener, SingleChoiceCartao.AlertPositiveListenerCartao, SingleChoiceBandeira.AlertPositiveListenerBandeiras {

    //position of the list of categories
    //attr for register
    int position = 0;
    int positionCartao = 0;
    int positionBandeira = 0;
    Date data;
    double valorConta;
    String tipoConta;
    int qtdParcelas;


    //work classes
    Categoria categoria;
    Conta conta;
    CartaoDeCredito cartao;


    //view attr
    EditText edtTxtValor;
    DatePicker dataPick;
    RadioGroup rdGroup;
    RadioButton rdButton;
    EditText edtTxtQtdParcelas;
    EditText edtTxtCartao;


    FloatingActionButton btnCadastrar;
    FloatingActionButton btnCancelar;
    FloatingActionButton btnExcluir;



    //list of categories
    PrepareListCategorias preparadorListaCategorias;
    String[] selecaoCategorias;
    String[] selecaoBandeiras;

    //list of cards
    PrepareListCartoes preparadorListaCartoes;
    String[] selecaoCartoes;

    //dao classes
    CategoriaDAO categoriaDao;
    ContaDAO contaDao;
    CreditoDAO creditoDao;

    Mensagem mensagem;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contas);
        mensagem = new Mensagem(CadastroContaActivity.this);

        categoriaDao = new CategoriaDAO(this);
        creditoDao= new CreditoDAO(this);
        preparadorListaCategorias = new PrepareListCategorias(CadastroContaActivity.this);
        preparadorListaCartoes = new PrepareListCartoes(CadastroContaActivity.this);

        selecaoCartoes = preparadorListaCartoes.prepare();
        selecaoCategorias = preparadorListaCategorias.prepare();

        onClickListenerButton();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_cadastro_contas);
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro_conta, menu);
        return true;
    }

    /**
     *
     */
    public void onClickListenerButton(){

        edtTxtValor = (EditText) findViewById(R.id.txtValorConta);
        dataPick = (DatePicker) findViewById(R.id.datePicker);
        rdGroup = (RadioGroup) findViewById(R.id.rdGrpTipoConta);
        edtTxtCartao = (EditText) findViewById(R.id.txtCartao);
        edtTxtQtdParcelas = (EditText) findViewById(R.id.txtQtdParcelas);

        btnCadastrar = (FloatingActionButton) findViewById(R.id.btnCadastrar);
        btnCancelar = (FloatingActionButton) findViewById(R.id.btnCancelar);
        btnExcluir = (FloatingActionButton) findViewById(R.id.btnExcluir);

        this.btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTxtValor.getText().equals("")) {
                    edtTxtValor.setText("0");
                    valorConta = Double.valueOf(edtTxtValor.getText().toString());
                } else {
                    valorConta = Double.valueOf(edtTxtValor.getText().toString());
                }
                categoria = new Categoria(selecaoCategorias[position]);
                int selected_id = rdGroup.getCheckedRadioButtonId();
                rdButton = (RadioButton) findViewById(selected_id);

                tipoConta = rdButton.getText().toString();


                if (!categoria.getNome().isEmpty() && data != null && valorConta != 0 && !tipoConta.isEmpty()) {

                    cartao = new CartaoDeCredito();
                    if (tipoConta.equals(TipoConta.CARTAO_CREDITO.getTipo())) {
                        if (edtTxtCartao.getText().equals("")) {
                            edtTxtCartao.setText("");
                            cartao.setNome(edtTxtCartao.getText().toString());
                        } else
                            cartao.setNome(edtTxtCartao.getText().toString());

                        if (edtTxtQtdParcelas.getText().equals("")) {
                            edtTxtQtdParcelas.setText("0");
                            qtdParcelas = Integer.parseInt(edtTxtQtdParcelas.getText().toString());
                        } else
                            qtdParcelas = Integer.parseInt(edtTxtQtdParcelas.getText().toString());

                        if (!cartao.getNome().equals("") || qtdParcelas != 0) {
                            conta = new Conta(data, categoria, valorConta, tipoConta, cartao.getNome(), qtdParcelas);
                            contaDao = new ContaDAO(CadastroContaActivity.this);
                            contaDao.insert(conta);
                            Intent intent = new Intent(CadastroContaActivity.this, ListarContasActivity.class);
                            startActivity(intent);
                        } else
                            mensagem.makeMsg("Os campos cartao e quantidade de parcelas, favor preencher os campos em falta.");

                    } else {
                        conta = new Conta(data, categoria, valorConta, tipoConta);
                        mensagem.makeMsgDados(conta.getCategoria().getNome(), conta.getValorConta(), conta.getTipoConta(), conta.getData());
                        contaDao = new ContaDAO(CadastroContaActivity.this);
                        contaDao.insert(conta);
                        Intent intent = new Intent(CadastroContaActivity.this, ListarContasActivity.class);
                        startActivity(intent);
                    }
                } else
                    mensagem.makeMsg("Todos os campos sao obrigatorios, favor preencher os campos em falta.");

            }
        });

        this.btnCancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(CadastroContaActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }
        );
    }

    /**
     *
     * @param v
     */
    public void abrirPopupCategorias(View v){
        SingleChoiceCategoria choice = new SingleChoiceCategoria();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        choice.setArguments(bundle);
        choice.show(getFragmentManager(), "Escolha a categoria");
    }

    /**
     *
     * @param v
     */
    public void abrirPopupCartoes(View v){
        SingleChoiceCartao choice = new SingleChoiceCartao();
        Bundle bundle = new Bundle();
        bundle.putInt("position", positionCartao);
        choice.setArguments(bundle);
        choice.show(getFragmentManager(), "Escolha o cartao");
    }

    /**
     *
     * @param v
     */
    public void goneLayoutCartao(View v){
        LinearLayout linearCartao = (LinearLayout) findViewById(R.id.linearCartao);
        linearCartao.setVisibility(LinearLayout.GONE);
    }

    /**
     *
     * @param v
     */
    public void visibleLayoutCartao(View v){
        LinearLayout linearCartao = (LinearLayout) findViewById(R.id.linearCartao);
        linearCartao.setVisibility(LinearLayout.VISIBLE);
    }

    /**
     *
     * @param v
     */
    public void abrirPopupDatePicker(View v){
        DatePickerFragment fragment = DatePickerFragment.newInstance(this);
        fragment.show(getFragmentManager(), "Data da Conta");
    }

    /**
     * Define o comportamento ao escolher uma categoria no metodo abrirPopupCategorias
     * @param position
     */
    @Override
    public void onPositiveClick(int position) {
        this.position = position;
        selecaoCategorias = preparadorListaCategorias.prepare();
        EditText categoria = (EditText) findViewById(R.id.txtCategoria);
        categoria.setText(selecaoCategorias[this.position]);

    }

    /**
     * Metodo para definir o comportamento ao escolher um cartao de credito no metodo abrirPopupCartoes
     * @param position
     */
    @Override
    public void onPositiveClickCartao(int position) {
        this.positionCartao = position;
        selecaoCartoes = preparadorListaCartoes.prepare();
        EditText cartao = (EditText) findViewById(R.id.txtCartao);
        cartao.setText(selecaoCartoes[this.positionCartao]);

    }

    /**
     * Metodo para definir o comportamento ao definir uma data no popup
     * @param date
     * @see abrirPopupDatePicker
     */
    @Override
    public void onDateSet(Date date) {
        /** Getting the reference of the textview from the main layout */
        EditText datePicker = (EditText) findViewById(R.id.txtDatePicker);

        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        data = date;
        datePicker.setText(dt.format(date));
    }

    public void abrirPopupBandeiras(View v){
        SingleChoiceBandeira choice = new SingleChoiceBandeira();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        choice.setArguments(bundle);
        choice.show(getFragmentManager(), "Escolha a bandeira");
    }

    @Override
    protected void onPause() {
        Log.d("ACTIVITY_PAUSE", "Atividade " + ListarResumoActivity.class + " pausada.");
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        Log.d("ACTIVITY_RETORNADA", "Atividade " + ListarResumoActivity.class + " retomada.");
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        Log.d("ACTIVITY_DESTRUIDA", "Atividade " + ListarResumoActivity.class + " destruida.");
        super.onDestroy();
    }

    @Override
    public void onPositiveClickBandeiras(int position) {
        this.positionBandeira = position;
        selecaoBandeiras = prepareListData();
        EditText bandeira = (EditText) findViewById(R.id.edtTxtBandeira);
        bandeira.setText(selecaoBandeiras[this.position]);
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
