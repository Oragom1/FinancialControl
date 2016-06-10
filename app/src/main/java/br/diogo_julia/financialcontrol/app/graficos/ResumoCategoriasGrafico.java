package br.diogo_julia.financialcontrol.app.graficos;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import br.diogo_julia.financialcontrol.R;

public class ResumoCategoriasGrafico extends AppCompatActivity {
    private RelativeLayout layout;
    private PieChart mChart;
    private float[] yData = { 5, 10, 15, 30, 40 };
    private String[] xData = { "Sony", "Huawei", "LG", "Apple", "Samsung" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_categorias_grafico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layout = (RelativeLayout) findViewById(R.id.relativeLayout);
        mChart = new PieChart(this);

        layout.addView(mChart);

        layout.setBackgroundColor(Color.LTGRAY);

        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Smartphones Market Share");

        //Configurando o grafico
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(15);
        mChart.setTransparentCircleRadius(20);

        //set rotacao por toque
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        //setando o listener de selecao
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                if(entry == null)
                    return;
                Toast.makeText(ResumoCategoriasGrafico.this,xData[entry.getXIndex()] + " = " + entry.getVal() + "%",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //add dados
        addData();

        //customizacao das legendas
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(15);
        l.setYEntrySpace(10);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void addData(){
        List<Entry> yVals = new ArrayList<Entry>();

        for(int i = 0; i < yData.length;i++)
            yVals.add(new Entry(yData[i],i));

        List<String> xVals = new ArrayList<>();

        for(int i=0;i<xData.length;i++)
            xVals.add(xData[i]);

        // criando o dataSet
        PieDataSet dataSet = new PieDataSet(yVals, "Teste");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add cores
        List<Integer> colors = new ArrayList<>();

        for(int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for(int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for(int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for(int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        for(int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        for(int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        dataSet.setColors(colors);

        // instanciando o grafico
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(Color.GRAY);
        data.setValueTextSize(11f);

        mChart.setData(data);

        // desmarcando as marcacoes
        mChart.highlightValue(null);

        // atualizando o grafico
        mChart.invalidate();

    }

}
