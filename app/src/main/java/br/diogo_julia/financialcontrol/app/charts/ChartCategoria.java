package br.diogo_julia.financialcontrol.app.charts;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import br.diogo_julia.financialcontrol.application.DAO.contadaoimpl.ContaDAO;
import br.diogo_julia.financialcontrol.dominio.resumo.Resumo;

import java.util.ArrayList;
import java.util.List;

public class ChartCategoria {
    private LinearLayout layout;
    private PieChart mChart;
    private float[] yData;
    private String[] xData;
    //dao classes
    ContaDAO contaDao;
    Context context;

    public ChartCategoria(Context context){
        this.context = context;
        contaDao = new ContaDAO(context);
    }

    public PieChart configuraGrafico(){

        List<String> xCats = new ArrayList<>();
        List<Double> yVals = new ArrayList<>();
        List<Resumo> listaResumo = contaDao.getListResumoPorCategoria();
        double somaTotal=0;
        for(Resumo c: listaResumo){
            xCats.add(c.getCategoria());
            yVals.add(c.getSoma());
        }

        xData = xCats.toArray(new String[0]);
        Double[] yDouble = yVals.toArray(new Double[0]);
        yData = new float[yVals.size()];
        for(int i = 0; i < yDouble.length; i++){
            yData[i] = yVals.get(i).floatValue();
        }

        mChart = new PieChart(context);



        //layout.setBackgroundColor(Color.LTGRAY);

        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Resumo por Categorias");

        //Configurando o grafico
        mChart.setTransparentCircleRadius(20);
        mChart.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(60f);

        //set rotacao por toque
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                if (entry == null)
                    return;
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

        return mChart;
    }

    private void addData(){
        List<Entry> yVals = new ArrayList<Entry>();

        for(int i = 0; i < yData.length;i++)
            yVals.add(new Entry(yData[i],i));

        List<String> xVals = new ArrayList<>();

        for(int i=0;i<xData.length;i++)
            xVals.add(xData[i]);

        // criando o dataSet
        PieDataSet dataSet = new PieDataSet(yVals, "Categorias");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add cores
        List<Integer> colors = new ArrayList<>();
        for(int c : ColorTemplate.JOYFUL_COLORS) {
            if(c != Color.parseColor("#fef778"))
                colors.add(c);
        }

        for(int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);



        colors.add(Color.parseColor("#f7ff1f"));


        //for(int c : ColorTemplate.LIBERTY_COLORS)
        //  colors.add(c);

        //for(int c : ColorTemplate.PASTEL_COLORS)
        //    colors.add(c);

        //for(int c : ColorTemplate.VORDIPLOM_COLORS)
        //    colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        // instanciando o grafico
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(11f);

        mChart.setData(data);

        // desmarcando as marcacoes
        mChart.highlightValue(null);

        // atualizando o grafico
        mChart.invalidate();

    }
}
