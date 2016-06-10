package br.diogo_julia.financialcontrol.app.activities.cartaodecredito;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import br.diogo_julia.financialcontrol.R;
import br.diogo_julia.financialcontrol.app.fragments.SingleChoiceBandeira;
import br.diogo_julia.financialcontrol.app.utils.Mensagem;
import br.diogo_julia.financialcontrol.application.DAO.creditodaoimpl.CreditoDAO;
import br.diogo_julia.financialcontrol.application.MainActivity;
import br.diogo_julia.financialcontrol.dominio.cartaodecredito.CartaoDeCredito;

public class CadastroCartaoCreditoActivity extends Activity implements SingleChoiceBandeira.AlertPositiveListenerBandeiras {

    FloatingActionButton btnCadastrar;
    FloatingActionButton btnCancelar;
    FloatingActionButton btnExcluir;

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
    int position = 0;

    //database classes
    SQLiteDatabase database;
    SQLiteOpenHelper dbHelper;

    //dao classes
    CreditoDAO creditoDao;

    Mensagem mensagem;
    String[] selecaoBandeiras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mensagem = new Mensagem(CadastroCartaoCreditoActivity.this);
        setContentView(R.layout.activity_cadastro_cartao_credito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_cadastro_cartao_credito);
        creditoDao = new CreditoDAO(CadastroCartaoCreditoActivity.this);

        onClickListenerButton();

        if (savedInstanceState != null) {
            nome = getIntent().getExtras().getString("descricao");
            CartaoDeCredito cartao = creditoDao.getByName(nome);
            edtTxtNome = (EditText) findViewById(R.id.edtTxtNome);
            edtTxtLimite = (EditText) findViewById(R.id.edtTxtLimite);
            edtTxtFatura = (EditText) findViewById(R.id.edtTxtFatura);
            rdGroupTipo = (RadioGroup) findViewById(R.id.rdGrpInternacional);
            rdGroupBandeira = (RadioGroup) findViewById(R.id.rdGrpBandeira);

            edtTxtNome.setText(String.valueOf(cartao.getNome()));
            edtTxtLimite.setText(String.valueOf(cartao.getLimite()));
            edtTxtFatura.setText(String.valueOf(cartao.getDataVencimento()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro_conta, menu);
        return true;
    }

    public void onClickListenerButton() {

        edtTxtNome = (EditText) findViewById(R.id.edtTxtNome);
        edtTxtLimite = (EditText) findViewById(R.id.edtTxtLimite);
        edtTxtFatura = (EditText) findViewById(R.id.edtTxtFatura);
        rdGroupTipo = (RadioGroup) findViewById(R.id.rdGrpInternacional);
        rdGroupBandeira = (RadioGroup) findViewById(R.id.rdGrpBandeira);


        btnCadastrar = (FloatingActionButton) findViewById(R.id.btnCadastrar);
        btnCancelar = (FloatingActionButton) findViewById(R.id.btnCancelar);
        btnExcluir = (FloatingActionButton) findViewById(R.id.btnExcluir);

        this.btnCadastrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtTxtLimite.getText().equals("")) {
                            edtTxtLimite.setText("0");
                            limite = Double.valueOf(edtTxtLimite.getText().toString());
                        } else {
                            limite = Double.valueOf(edtTxtLimite.getText().toString());
                        }

                        int selected_id = rdGroupTipo.getCheckedRadioButtonId();
                        rdButtonTipo = (RadioButton) findViewById(selected_id);
                        tipo = rdButtonTipo.getText().toString();


                        nome = edtTxtNome.getText().toString();
                        fatura = Integer.parseInt(edtTxtFatura.getText().toString());

                        CartaoDeCredito cartao = new CartaoDeCredito(nome, bandeira, tipo, fatura, limite);

                        if (!nome.isEmpty() || limite != 0 || fatura != 0 || !tipo.isEmpty() || !bandeira.isEmpty()) {
                            if (fatura <= 0 || fatura > 31) {
                                mensagem.makeMsg("Data de Vencimento da fatura deve ser entre 1 e 31.");
                            } else {
                                mensagem.makeMsg("Descricao: " + cartao.getNome() +
                                        "\n" + "Limite: " + cartao.getLimite() +
                                        "\n" + "Dia de Vencimento: " + cartao.getDataVencimento() +
                                        "\n" + "Tipo: " + cartao.getTipo() +
                                        "\n" + "Bandeira: " + cartao.getBandeira());
                                creditoDao.insert(cartao);
                                Intent intent = new Intent(CadastroCartaoCreditoActivity.this, br.diogo_julia.financialcontrol.app.activities.cartaodecredito.ListarCartoesActivity.class);
                                startActivity(intent);
                            }
                        } else
                            mensagem.makeMsg("Todos os campos sao obrigatorios, favor preencher os campos em falta.");
                    }

                }
        );

        this.btnCancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(CadastroCartaoCreditoActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        Log.d("ACTIVITY_PAUSE", "Atividade " + CadastroCartaoCreditoActivity.class + " pausada.");
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        Log.d("ACTIVITY_RETORNADA", "Atividade " + CadastroCartaoCreditoActivity.class + " retomada.");
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        Log.d("ACTIVITY_DESTRUIDA", "Atividade " + CadastroCartaoCreditoActivity.class + " destruida.");
        super.onDestroy();
    }


    @Override
    public void onPositiveClickBandeiras(int position) {
        this.position = position;
        selecaoBandeiras = prepareListData();
        EditText bandeira = (EditText) findViewById(R.id.edtTxtBandeira);
        bandeira.setText(selecaoBandeiras[this.position]);
    }

    public void abrirPopupBandeiras(View v) {
        SingleChoiceBandeira choice = new SingleChoiceBandeira();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        choice.setArguments(bundle);
        choice.show(getFragmentManager(), "Escolha a bandeira");
    }


    private String[] prepareListData() {
        // adicionando os nodes

        List<String> bandeiras = new ArrayList();
        bandeiras.add("Visa");
        bandeiras.add("MasterCard");
        bandeiras.add("Elo");
        bandeiras.add("Dinners");

        return (String[]) bandeiras.toArray(new String[0]);
    }
}
