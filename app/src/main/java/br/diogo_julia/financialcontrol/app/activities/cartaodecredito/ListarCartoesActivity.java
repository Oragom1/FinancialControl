package br.diogo_julia.financialcontrol.app.activities.cartaodecredito;

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


import br.diogo_julia.financialcontrol.application.DAO.creditodaoimpl.CreditoDAO;
import br.diogo_julia.financialcontrol.app.adapters.CartoesListAdapter;
import br.diogo_julia.financialcontrol.dominio.cartaodecredito.CartaoDeCredito;

import java.util.List;

public class ListarCartoesActivity extends AppCompatActivity {

    //database classes
    SQLiteDatabase database;
    SQLiteOpenHelper dbHelper;


    //dao classes
    CreditoDAO creditoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cartoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        creditoDao = new CreditoDAO(ListarCartoesActivity.this);
        ListView minhaListView = (ListView) findViewById(R.id.lblListItemCartao);
        List<CartaoDeCredito> cartoes = creditoDao.getList();

        double limiteTotal = creditoDao.getLimiteTotal();
        TextView limiteTotalView = (TextView) findViewById(R.id.txtLimiteTotal);

        limiteTotalView.setText(String.valueOf(limiteTotal));

        // get data from the table by the ListAdapter
        ListAdapter adaptadorListaCartoes = new CartoesListAdapter(this, R.layout.list_item_cartoes, cartoes);
        minhaListView.setAdapter(adaptadorListaCartoes);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListarCartoesActivity.this, CadastroCartaoCreditoActivity.class);
                startActivity(i);
            }
        });

        final LinearLayout resumo = (LinearLayout) findViewById(R.id.resumo);
        findViewById(R.id.conteiner).post(new Runnable() {
                                              public void run() {
                                                  int altura = fab.getHeight();
                                                  //Toast.makeText(ListarCartoesActivity.this, "Altura: " + altura, Toast.LENGTH_LONG).show();
                                                  resumo.getLayoutParams().height = altura;
                                              }

                                          }
        );
    }

    @Override
    protected void onPause() {
        Log.d("ACTIVITY_PAUSE", "Atividade " + ListarCartoesActivity.class + " pausada.");
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        Log.d("ACTIVITY_RETORNADA", "Atividade " + ListarCartoesActivity.class + " retomada.");
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        Log.d("ACTIVITY_DESTRUIDA", "Atividade " + ListarCartoesActivity.class + " destruida.");
        super.onDestroy();
    }

}
