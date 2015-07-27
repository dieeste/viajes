package com.example.diego.gastosviajes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * Created by diego on 10/07/15.
 */
public class GastosFijos extends Activity implements View.OnClickListener{
    private int[] COLORS = new int[] { Color.RED, Color.CYAN ,Color.MAGENTA };

    private  double[] VALUES;

    private String[] NAME_LIST;

    private CategorySeries mSeries = new CategorySeries("");

    private DefaultRenderer mRenderer = new DefaultRenderer();

    private GraphicalView mChartView;

    private QuotesDataSource dataSource;
    Double home;
    Double transport;
    Double eat;

    ImageButton alojamientos;
    ImageButton transportes;
    ImageButton comidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gastos_fijos);
        setTitle("Gastos fijos");

        dataSource = new QuotesDataSource(this);

        Cursor aloja = dataSource.suma("Alojamiento");

        if(aloja.moveToFirst()) {
            home= aloja.getDouble(0);
        }
        Cursor transpor = dataSource.suma("Transporte");

        if(transpor.moveToFirst()) {
            transport= transpor.getDouble(0);
        }
        Cursor food = dataSource.suma("Comida");

        if(food.moveToFirst()) {
            eat= food.getDouble(0);
            //jejeej
        }
        VALUES = new double[] { home, transport, eat };
        NAME_LIST = new String[] { "A", "B", "C" };

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(0,255, 65, 156));
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.setMargins(new int[]{20, 30, 15, 0});
        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setShowLegend(false);
        mRenderer.setStartAngle(90);
        mRenderer.setPanEnabled(false);

        for (int i = 0; i < VALUES.length; i++) {
            mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            mRenderer.addSeriesRenderer(renderer);
        }

        if (mChartView != null) {
            mChartView.repaint();
        }

        alojamientos = (ImageButton) findViewById(R.id.alojamiento);
        transportes = (ImageButton) findViewById(R.id.transporte);
        comidas = (ImageButton) findViewById(R.id.comida);

        alojamientos.setOnClickListener(this);
        transportes.setOnClickListener(this);
        comidas.setOnClickListener(this);
    }
public void cargarGrafica(){
        Cursor aloja = dataSource.suma("Alojamiento");

        if(aloja.moveToFirst()) {
            home= aloja.getDouble(0);
        }
        Cursor transpor = dataSource.suma("Transporte");

        if(transpor.moveToFirst()) {
            transport= transpor.getDouble(0);
        }
        Cursor food = dataSource.suma("Comida");

        if(food.moveToFirst()) {
            eat= food.getDouble(0);
        }
        VALUES = new double[] { home, transport, eat };
    Log.v("hola", "entramos");
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
            mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);

            mChartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();

                    if (seriesSelection != null) {

                            switch (seriesSelection.getPointIndex()) {
                                case 0:
                                    Intent alojamiento = new Intent(GastosFijos.this, Gastos.class);
                                    alojamiento.putExtra("categoria", "Alojamiento");
                                    startActivity(alojamiento);
                                    break;
                                case 1:
                                    Intent transporte = new Intent(GastosFijos.this, Gastos.class);
                                    transporte.putExtra("categoria", "Transporte");
                                    startActivity(transporte);
                                    break;
                                case 2:
                                    Intent comida = new Intent(GastosFijos.this, Gastos.class);
                                    comida.putExtra("categoria", "Comida");
                                    startActivity(comida);
                                    break;
                            }
                    }
                }
            });

            mChartView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                    if (seriesSelection == null) {
                        Toast.makeText(GastosFijos.this,"No chart element was long pressed", Toast.LENGTH_SHORT);
                        return false;
                    } else {
                        Toast.makeText(GastosFijos.this, "Chart element data point index " + seriesSelection.getPointIndex() + " was long pressed", Toast.LENGTH_SHORT);
                        return true;
                    }
                }
            });
            layout.addView(mChartView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        }
        else {
                mChartView.repaint();
        }
    }

    @Override
    public void onClick(View boton) {
        switch (boton.getId()) {
            case R.id.alojamiento:
                Intent alojamiento = new Intent(GastosFijos.this, Gastos.class);
                alojamiento.putExtra("categoria","Alojamiento");
                startActivity(alojamiento);
                break;
            case R.id.transporte:
                Intent transporte = new Intent(GastosFijos.this, Gastos.class);
                transporte.putExtra("categoria","Transporte");
                startActivity(transporte);
                break;
            case R.id.comida:
                Intent comida = new Intent(GastosFijos.this, Gastos.class);
                comida.putExtra("categoria", "Comida");
                startActivity(comida);
                break;
        }
    }
}
