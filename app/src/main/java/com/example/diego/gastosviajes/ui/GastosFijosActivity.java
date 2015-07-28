package com.example.diego.gastosviajes.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.diego.gastosviajes.R;
import com.example.diego.gastosviajes.model.CategoriaGastos;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * Created by diego on 10/07/15.
 */
public class GastosFijosActivity extends Activity implements View.OnClickListener{
    private static int[] COLORS = new int[] { Color.RED, Color.CYAN ,Color.MAGENTA };

    private static double[] VALUES = new double[] { 10, 11, 12 };

    private static String[] NAME_LIST = new String[] { "A", "B", "C" };

    private CategorySeries mSeries = new CategorySeries("");

    private DefaultRenderer mRenderer = new DefaultRenderer();

    private GraphicalView mChartView;

    public static  String TIPO_CATEGORIA;

    ImageButton aloja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gastos_fijos);

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

        aloja = (ImageButton) findViewById(R.id.alojamiento);
        aloja.setOnClickListener(this);
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

                    if (seriesSelection == null) {
                        Toast.makeText(GastosFijosActivity.this, "No chart element was clicked", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(GastosFijosActivity.this,"Chart element data point index "+ (seriesSelection.getPointIndex()+1) + " was clicked" + " point value="+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
                        if (seriesSelection.getPointIndex()==1) {
                            Toast.makeText(GastosFijosActivity.this, "Chart element data point index asdf", Toast.LENGTH_SHORT);
                            Intent alojamiento = new Intent(GastosFijosActivity.this, GastosActivity.class);
                            startActivity(alojamiento);
                        }
                    }
                }
            });

            mChartView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                    if (seriesSelection == null) {
                        Toast.makeText(GastosFijosActivity.this,"No chart element was long pressed", Toast.LENGTH_SHORT);
                        return false;
                    } else {
                        Toast.makeText(GastosFijosActivity.this, "Chart element data point index " + seriesSelection.getPointIndex() + " was long pressed", Toast.LENGTH_SHORT);
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
                Intent alojamiento = new Intent(GastosFijosActivity.this, GastosActivity.class);
                TIPO_CATEGORIA = CategoriaGastos.ALOJAMIENTO.getNombreCategoria();
                startActivity(alojamiento);
                break;
        }
    }
}
