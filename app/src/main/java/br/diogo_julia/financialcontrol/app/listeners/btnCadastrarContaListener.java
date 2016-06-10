package br.diogo_julia.financialcontrol.app.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Date;

import br.diogo_julia.financialcontrol.R;
import br.diogo_julia.financialcontrol.app.activities.conta.ListarContasActivity;
import br.diogo_julia.financialcontrol.app.utils.Mensagem;
import br.diogo_julia.financialcontrol.app.utils.TipoConta;
import br.diogo_julia.financialcontrol.application.DAO.contadaoimpl.ContaDAO;
import br.diogo_julia.financialcontrol.dominio.cartaodecredito.CartaoDeCredito;
import br.diogo_julia.financialcontrol.dominio.categoria.Categoria;
import br.diogo_julia.financialcontrol.dominio.conta.Conta;

public class btnCadastrarContaListener extends Activity implements View.OnClickListener {

    Context context;

    //position of the list of categories
    int position = 0;
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


    //list of categories
    String[] selecaoCategorias;

    ContaDAO contaDao;

    Mensagem mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public btnCadastrarContaListener(Context context) {

        this.context = context;

        setContentView(R.layout.activity_contas_mensal);
        EditText edtTxtValor = (EditText) findViewById(R.id.txtValorConta);
        DatePicker dataPick = (DatePicker) findViewById(R.id.datePicker);
        RadioGroup rdGroup = (RadioGroup) findViewById(R.id.rdGrpTipoConta);
        EditText edtTxtCartao = (EditText) findViewById(R.id.txtCartao);
        EditText edtTxtQtdParcelas = (EditText) findViewById(R.id.txtQtdParcelas);
        mensagem = new Mensagem(context);

    }

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
                    contaDao = new ContaDAO(context);
                    contaDao.insert(conta);
                    Intent intent = new Intent(context, ListarContasActivity.class);
                    startActivity(intent);
                } else
                    mensagem.makeMsg("Os campos cartao e quantidade de parcelas, favor preencher os campos em falta.");

            } else {
                conta = new Conta(data, categoria, valorConta, tipoConta);
                mensagem.makeMsgDados(conta.getCategoria().getNome(), conta.getValorConta(), conta.getTipoConta(), conta.getData());
                contaDao = new ContaDAO(context);
                contaDao.insert(conta);
                Intent intent = new Intent(context, ListarContasActivity.class);
                startActivity(intent);
            }
        } else
            mensagem.makeMsg("Todos os campos sao obrigatorios, favor preencher os campos em falta.");

    }
}
