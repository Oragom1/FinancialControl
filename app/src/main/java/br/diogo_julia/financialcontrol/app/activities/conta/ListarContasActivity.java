package br.diogo_julia.financialcontrol.app.activities.conta;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.diogo_julia.financialcontrol.R;
import br.diogo_julia.financialcontrol.app.adapters.ContasListAdapter;
import br.diogo_julia.financialcontrol.app.utils.TipoConta;
import br.diogo_julia.financialcontrol.application.DAO.contadaoimpl.ContaDAO;
import br.diogo_julia.financialcontrol.dominio.conta.Conta;

public class ListarContasActivity extends AppCompatActivity {

    //database classes
    SQLiteDatabase database;
    SQLiteOpenHelper dbHelper;

    //dao classes
    ContaDAO contaDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas_mensal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView saldo = (TextView) findViewById(R.id.txtSaldo);

        contaDao = new ContaDAO(this);
        ListView minhaListView = (ListView) findViewById(R.id.lblListItem);
        List<Conta> contas = contaDao.getList();
        double somaSaldo = 0;
        double somaSaida = 0;
        for (Conta c : contas) {
            if (c.getTipoConta().equals(TipoConta.ENTRADA.getTipo())) {
                somaSaldo += c.getValorConta();
            } else if (c.getTipoConta().equals(TipoConta.SAIDA.getTipo())) {
                somaSaida += c.getValorConta();
            }
        }
        saldo.setText(String.valueOf(somaSaldo - somaSaida).toString());
        // get data from the table by the ListAdapter
        ListAdapter adaptadorListaContas = new ContasListAdapter(this, R.layout.list_item_contas, contas);
        minhaListView.setAdapter(adaptadorListaContas);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListarContasActivity.this, br.diogo_julia.financialcontrol.app.activities.conta.CadastroContaActivity.class);
                startActivity(i);
            }
        });
        final LinearLayout resumo = (LinearLayout) findViewById(R.id.resumo);
        findViewById(R.id.conteiner).post(new Runnable() {
                                              public void run() {
                                                  int altura = fab.getHeight();
                                                 // Toast.makeText(ListarContasActivity.this, "Altura: " + altura, Toast.LENGTH_LONG).show();
                                                  resumo.getLayoutParams().height = altura;
                                              }

                                          }
        );
    }

    @Override
    protected void onPause() {
        Log.d("ACTIVITY_PAUSE", "Atividade " + ListarContasActivity.class + " pausada.");
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        Log.d("ACTIVITY_RETORNADA", "Atividade " + ListarContasActivity.class + " retomada.");
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        Log.d("ACTIVITY_DESTRUIDA", "Atividade " + ListarContasActivity.class + " destruida.");
        super.onDestroy();
    }

}
