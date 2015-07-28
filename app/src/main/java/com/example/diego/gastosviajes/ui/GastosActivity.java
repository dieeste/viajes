package com.example.diego.gastosviajes.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.diego.gastosviajes.R;
import com.example.diego.gastosviajes.bd.QuotesDataSource;
import com.example.diego.gastosviajes.model.Gasto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego on 13/07/15.
 */
public class GastosActivity extends Activity {

    public final static int ADD_REQUEST_CODE = 1;

    private AsListAdapter adapter;
    private QuotesDataSource dataSource;
    TextView txtConcepto;
    TextView txtCantidad;
    List<Gasto> listaGastos;
    ListView listViewGastos;
    Button btnAnadirGasto;
    Gasto gasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gastos_detalles);
        initialize();
    }

    public void initialize() {
        initComponents();
        initGastos();
        initQuotesDataSource();
        executeAsyncTaskCargarGastos();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void executeAsyncTaskCargarGastos() {
        AsyncTaskCargarGastos asyncTaskCargarGastos = new
                AsyncTaskCargarGastos();
        asyncTaskCargarGastos.execute();
    }

    public void initComponents() {
        findView();
        setListeners();
    }


    public void findView() {
        btnAnadirGasto = (Button) findViewById(R.id.btnAnadirGasto);
        txtCantidad = (TextView) findViewById(R.id.cantidad);
        txtConcepto = (TextView) findViewById(R.id.concepto);
        listViewGastos = (ListView) findViewById(R.id.listGastosDetalle);
    }

    public void setListeners() {
        btnAnadirGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent anade = new Intent(getApplicationContext(), NuevoGastoActivity.class);
                anade.putExtra("categoria", gasto.getCategoria());
                startActivityForResult(anade, ADD_REQUEST_CODE);
            }
        });
    }

    public void initGastos() {
        Bundle gastosBundle = getIntent().getExtras();
        gasto = new Gasto();
        gasto.setCategoria(GastosFijosActivity.TIPO_CATEGORIA);
        listaGastos = new ArrayList<Gasto>();

    }

    public void initQuotesDataSource() {
        dataSource = new QuotesDataSource(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            executeAsyncTaskCargarGastos();
        }
    }

    static class ViewHolder {
        TextView txtConcepto;
        TextView txtCantidad;
    }

    public void setAdapter() {
        adapter = new AsListAdapter(this, listaGastos);
        listViewGastos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private class AsListAdapter extends BaseAdapter {

        protected Activity activity;
        protected List<Gasto> items;

        public AsListAdapter(Activity activity, List<Gasto> items) {
            this.activity = activity;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            View row = convertView;

            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inf.inflate(R.layout.item_list_gastos, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.txtCantidad = (TextView) row.findViewById(R.id
                        .cantidad);
                viewHolder.txtConcepto = (TextView) row.findViewById(R.id
                        .concepto);
                row.setTag(viewHolder);
            }

            Gasto gasto = items.get(position);
            viewHolder = (ViewHolder) row.getTag();
            viewHolder.txtCantidad.setText(String.valueOf(gasto.getCantidad()));
            viewHolder.txtConcepto.setText(String.valueOf(gasto.getConcepto()));
            return row;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return items.get(position).getId();
        }

    }


    protected class AsyncTaskCargarGastos extends
            AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            listaGastos.addAll(dataSource.cargarGastos(gasto.getCategoria()));
            return null;
        }

        @Override
        protected void onPostExecute(final Void result) {
            setAdapter();
        }

    }


}
