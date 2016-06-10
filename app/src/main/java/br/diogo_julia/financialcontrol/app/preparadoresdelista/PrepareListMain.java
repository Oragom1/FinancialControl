package br.diogo_julia.financialcontrol.app.preparadoresdelista;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.PieChart;
import br.diogo_julia.financialcontrol.app.adapters.ExpandableListAdapter;
import br.diogo_julia.financialcontrol.application.DAO.IDAO;
import br.diogo_julia.financialcontrol.application.DAO.categoriadaoimpl.CategoriaDAO;
import br.diogo_julia.financialcontrol.application.DAO.contadaoimpl.ContaDAO;
import br.diogo_julia.financialcontrol.application.menu.CartaoDeCreditoMenu;
import br.diogo_julia.financialcontrol.application.menu.ControleDeContas;
import br.diogo_julia.financialcontrol.application.menu.Graficos;
import br.diogo_julia.financialcontrol.application.menu.MenuPrincipal;
import br.diogo_julia.financialcontrol.application.menu.PlanejamentoFinanceiro;
import br.diogo_julia.financialcontrol.application.menu.ResumoMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrepareListMain extends AbstractPreparador {

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

    //list of cards
    String[] selecaoCategorias;
    IDAO dao;

    public PrepareListMain(Context context) {
        super(context);
        this.dao = new CategoriaDAO(context);
    }

    @Override
    public String[] prepare() {
        return new String[0];
    }

    @Override
    public List prepareList(){
        return null;
    }


    public List prepareListChild(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // adicionando os dados principais
        listDataHeader.add(MenuPrincipal.CONTROLE_DE_CONTAS.getNome());
        listDataHeader.add(MenuPrincipal.CARTAO_DE_CREDITO.getNome());
        listDataHeader.add(MenuPrincipal.PLANEJAMENTO_FINANCEIRO.getNome());
        listDataHeader.add(MenuPrincipal.RESUMO.getNome());
        listDataHeader.add(MenuPrincipal.GRAFICOS.getNome());

        // adicionando os nodes
        List<String> controleDeContas = new ArrayList<String>();
        controleDeContas.add(ControleDeContas.CONTAS.getNome());

        List<String> cartaoDeCredito = new ArrayList<String>();
        cartaoDeCredito.add(CartaoDeCreditoMenu.FATURA.getNome());
        cartaoDeCredito.add(CartaoDeCreditoMenu.CARTOES.getNome());

        List<String> planejamento = new ArrayList<String>();
        planejamento.add(PlanejamentoFinanceiro.MENSAL.getNome());
        planejamento.add(PlanejamentoFinanceiro.INVESTIMENTOS.getNome());

        List<String> resumo = new ArrayList<String>();
        resumo.add(ResumoMenu.DETALHES.getNome());

        List<String> graficos = new ArrayList<String>();
        graficos.add(Graficos.GASTOS.getNome());

        listDataChild.put(listDataHeader.get(MenuPrincipal.CONTROLE_DE_CONTAS.getNum()), controleDeContas);
        listDataChild.put(listDataHeader.get(MenuPrincipal.CARTAO_DE_CREDITO.getNum()), cartaoDeCredito);
        listDataChild.put(listDataHeader.get(MenuPrincipal.PLANEJAMENTO_FINANCEIRO.getNum()), planejamento);
        listDataChild.put(listDataHeader.get(MenuPrincipal.RESUMO.getNum()), resumo);
        listDataChild.put(listDataHeader.get(MenuPrincipal.GRAFICOS.getNum()), graficos);

        return null;
    }

    public List prepareListHeader(){
        listDataHeader = new ArrayList<String>();

        // adicionando os dados principais
        listDataHeader.add(MenuPrincipal.CONTROLE_DE_CONTAS.getNome());
        listDataHeader.add(MenuPrincipal.CARTAO_DE_CREDITO.getNome());
        listDataHeader.add(MenuPrincipal.PLANEJAMENTO_FINANCEIRO.getNome());
        listDataHeader.add(MenuPrincipal.RESUMO.getNome());
        listDataHeader.add(MenuPrincipal.GRAFICOS.getNome());

        return listDataHeader;
    }
}
