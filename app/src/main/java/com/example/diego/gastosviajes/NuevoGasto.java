package com.example.diego.gastosviajes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by diego on 14/07/15.
 */
public class NuevoGasto extends Activity implements View.OnClickListener{

    EditText cuanto;
    EditText concepto;
    TextView fecha;
    QuotesDataSource controlador;
    String categoria;
    ImageButton save;
    ImageButton calendario;
    Calendar myCalendar = Calendar.getInstance();
    String fech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nuevo_gasto);

        setTitle("Nuevo Gasto");
        Bundle gastos = getIntent().getExtras();
        categoria = gastos.getString("categoria");

        cuanto = (EditText) findViewById(R.id.cantidad);
        concepto = (EditText) findViewById(R.id.concept);
        fecha = (TextView) findViewById(R.id.date);

        save = (ImageButton) findViewById(R.id.save);
        calendario = (ImageButton) findViewById(R.id.calendar);

        save.setOnClickListener(this);

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);
//        fecha.setText(sdf.format(myCalendar.getTime()));
        fech=sdf.format(myCalendar.getTime());
        Log.v("hola","fecaga11 "+fech);

        fecha.setText(sdf.format(myCalendar.getTime()));

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NuevoGasto.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);
        fecha.setText(sdf.format(myCalendar.getTime()));
        fech = sdf.format(myCalendar.getTime());
    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.aceptar:

                String concept = concepto.getText().toString();
                Double cuantia = Double.parseDouble(cuanto.getText().toString());
                fech = fecha.getText().toString();



                controlador.insertar(concept, cuantia, fech, categoria);

                //Enviar la información
                setResult(RESULT_OK);

                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.save){
            controlador = new QuotesDataSource(this);
            String concept = concepto.getText().toString();
            Double cuantia = Double.parseDouble(cuanto.getText().toString());
            if(fech=="") {
                fech = fecha.getText().toString();
            }
            Log.v("hola","cuantia "+cuantia);
            Log.v("hola","conce "+concept);
            Log.v("hola","fecaga "+fech);
            Log.v("hola", "catal " + categoria);

            controlador.insertar(concept, cuantia, fech, categoria);
            Intent backData = new Intent();
            backData.putExtra("titulo", 2);
            //Enviar la información
            setResult(RESULT_OK, backData);

        }
        finish();
    }
}
