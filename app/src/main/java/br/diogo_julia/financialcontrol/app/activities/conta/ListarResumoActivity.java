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

import br.diogo_julia.financialcontrol.dominio.resumo.Resumo;
import br.diogo_julia.financialcontrol.application.MainActivity;

import br.diogo_julia.financialcontrol.application.DAO.contadaoimpl.ContaDAO;
import br.diogo_julia.financialcontrol.app.adapters.ResumoContasListAdapter;

import java.util.List;

public class ListarResumoActivity extends AppCompatActivity {

    //database classes
    SQLiteDatabase database;
    SQLiteOpenHelper dbHelper;

    //dao classes
    ContaDAO contaDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_contas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView saldo = (TextView) findViewById(R.id.txtTotal);

        contaDao = new ContaDAO(ListarResumoActivity.this);
        ListView minhaListView = (ListView) findViewById(R.id.lblListItem);
        List<Resumo> contas = contaDao.getListResumoPorCategoria();
        double somaTotal=0;
        for(Resumo c: contas){
            somaTotal += c.getSoma();
        }
        saldo.setText(String.valueOf(somaTotal).toString());
        // get data from the table by the ListAdapter
        ListAdapter adaptadorListaContas = new ResumoContasListAdapter(this, R.layout.list_item_resumo, contas);
        minhaListView.setAdapter(adaptadorListaContas);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnVoltar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListarResumoActivity.this, MainActivity.class);
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
        Log.d("ACTIVITY_PAUSE","Atividade " + ListarResumoActivity.class + " pausada.");
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
}
