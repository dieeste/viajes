package com.example.diego.gastosviajes;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by diego on 13/07/15.
 */
public class Gastos extends ListActivity implements View.OnClickListener {

    private SimpleCursorAdapter adapter;

    private QuotesDataSource dataSource;
    ImageButton anadir;
    public final static int ADD_REQUEST_CODE = 1;
    String categoria;
    TextView totalGastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle gastos = getIntent().getExtras();
        categoria = gastos.getString("categoria");
        setTitle(categoria);

        dataSource = new QuotesDataSource(this);

        setContentView(R.layout.gastos_detalles);

        anadir = (ImageButton) findViewById(R.id.anadir);

        anadir.setOnClickListener(this);

        totalGastos = (TextView) findViewById(R.id.total);
        Cursor tot = dataSource.suma(categoria);
        if(tot.moveToFirst()) {
            totalGastos.setText("TOTAL GASTADO: " + String.valueOf(tot.getDouble(0)));
        }


        adapter = new SimpleCursorAdapter(this,
                R.layout.listas,
//                android.R.layout.two_line_list_item,
                dataSource.cargarGastos(categoria),
                new String[]{QuotesDataSource.ColumnQuotes.CONCEPTO_GASTO, QuotesDataSource.ColumnQuotes.CANTIDAD_GASTO,QuotesDataSource.ColumnQuotes.FECHA_GASTO},
                new int[]{R.id.concepto, R.id.cantidad,R.id.fechas},
//                new int[]{android.R.id.text1,android.R.id.text2},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);
    }


    @Override
    public void onClick(View boton) {
        switch (boton.getId()) {
            case R.id.anadir:
                Intent anade = new Intent(this, NuevoGasto.class);
                anade.putExtra("categoria",categoria);
                startActivityForResult(anade, ADD_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {

            Cursor tot = dataSource.suma(categoria);
            if(tot.moveToFirst()) {
                totalGastos.setText("TOTAL GASTADO: "+String.valueOf(tot.getDouble(0)));
            }
            adapter = new SimpleCursorAdapter(this,
                    R.layout.listas,
//                android.R.layout.two_line_list_item,
                    dataSource.cargarGastos(categoria),
                    new String[]{QuotesDataSource.ColumnQuotes.CONCEPTO_GASTO, QuotesDataSource.ColumnQuotes.CANTIDAD_GASTO,QuotesDataSource.ColumnQuotes.FECHA_GASTO},
                    new int[]{R.id.concepto, R.id.cantidad,R.id.fechas},
//                new int[]{android.R.id.text1,android.R.id.text2},
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            setListAdapter(adapter);
        }
    }
}
