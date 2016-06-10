package br.diogo_julia.financialcontrol.application;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.diogo_julia.financialcontrol.R;
import br.diogo_julia.financialcontrol.app.activities.cartaodecredito.ListarCartoesActivity;
import br.diogo_julia.financialcontrol.app.activities.conta.ListarContasActivity;
import br.diogo_julia.financialcontrol.app.activities.conta.ListarContasCreditoActivity;
import br.diogo_julia.financialcontrol.app.activities.conta.ListarResumoActivity;
import br.diogo_julia.financialcontrol.app.adapters.ExpandableListAdapter;
import br.diogo_julia.financialcontrol.app.charts.ChartCategoria;
import br.diogo_julia.financialcontrol.app.utils.TipoConta;
import br.diogo_julia.financialcontrol.application.DAO.contadaoimpl.ContaDAO;
import br.diogo_julia.financialcontrol.application.menu.MenuP;
import br.diogo_julia.financialcontrol.dominio.conta.Conta;

public class MainActivity extends Activity {

    //database classes
    SQLiteDatabase database;
    SQLiteOpenHelper dbHelper;

    //dao classes
    ContaDAO contaDao;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Intent i;

    private LinearLayout layout;
    private PieChart mChart;
    private float[] yData;
    private String[] xData;
    private ChartCategoria chartCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contaDao = new ContaDAO(this);
        chartCategoria = new ChartCategoria(MainActivity.this);
        mChart = chartCategoria.configuraGrafico();

        expListView = (ExpandableListView) findViewById(R.id.lvlExp);
        try {
            prepareListData();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        TextView saldo = (TextView) findViewById(R.id.txtSaldo);
        List<Conta> contas = contaDao.getList();
        double somaSaldo=0;
        double somaSaida=0;
        for(Conta c: contas){
            if(c.getTipoConta().equals(TipoConta.ENTRADA.getTipo())){
                somaSaldo += c.getValorConta();
            }
            else if(c.getTipoConta().equals(TipoConta.SAIDA.getTipo())){
                somaSaida += c.getValorConta();
            }
        }
        //atualizando o valor do Saldo em tela
        saldo.setText("R$  ".concat(String.valueOf(somaSaldo - somaSaida).toString()));

        // adaptador customizado para o ExpandableListView
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                switch (groupPosition) {
                    case 0:
                        switch (childPosition) {
                            case 0:
                                i = new Intent(MainActivity.this, ListarContasActivity.class);
                                startActivity(i);
                                break;

                        }
                        break;
                    case 1:
                        switch (childPosition) {
                            case 0:
                                i = new Intent(MainActivity.this, ListarContasCreditoActivity.class);
                                startActivity(i);
                                break;
                            case 1:
                                i = new Intent(MainActivity.this, ListarCartoesActivity.class);
                                startActivity(i);
                                break;


                        }
                        break;
                    case 2:
                        switch (childPosition) {
                            case 0:
                                Intent i = new Intent(MainActivity.this, ListarResumoActivity.class);
                                startActivity(i);
                                break;
                            case 1:
                                Toast.makeText(
                                        getApplicationContext(),
                                        listDataHeader.get(groupPosition)
                                                + " : "
                                                + listDataChild.get(
                                                listDataHeader.get(groupPosition)).get(
                                                childPosition), Toast.LENGTH_SHORT)
                                        .show();
                                break;

                        }
                        break;
                    /*case 3:
                        switch (childPosition) {
                            case 0:
                                Intent i = new Intent(MainActivity.this, ListarResumoActivity.class);
                                startActivity(i);
                                break;
                        }
                        break;
                    case 4:
                        switch (childPosition) {
                            case 0:
                                Intent i = new Intent(MainActivity.this, ResumoCategoriasGrafico.class);
                                startActivity(i);
                                break;

                        }
                        break;*/
                }


                return false;
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int ultimoItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != ultimoItem) {
                    expListView.collapseGroup(ultimoItem);
                }
                ultimoItem = groupPosition;
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        layout = (LinearLayout) findViewById(R.id.chart);
        layout.addView(mChart);

    }


    /**
     * Metodo para gerar a lista de Menu automaticamente
     * atraves dos Enums de Menu
     * Atualizando o listDataHeader e o listDataChild
     * Enum utilizado:
     *      MenuP - Enum com os Menus Principais e referencia para os respectivos Enums de submenu.
     *
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void prepareListData() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // adicionando os dados principais

        Iterator<MenuP> menus = EnumSet.allOf(MenuP.class).iterator();
        while(menus.hasNext()) {
            List<String> sub = new ArrayList<>();
            MenuP menu = menus.next();
            listDataHeader.add(menu.getNomeTabela());
            Iterator subMenus = EnumSet.allOf(menu.getEnumeracao()).iterator();
            while(subMenus.hasNext()) {
                Object enumSubmenu = subMenus.next();

                sub.add(enumSubmenu.toString());

            }
            listDataChild.put(listDataHeader.get(menu.getNum()), sub);
        }
    }

    /**
     *
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
