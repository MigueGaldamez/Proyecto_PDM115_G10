package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DiplomadoConsultarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdDiplomado;
    EditText editTitulo;
    EditText editDescripcion;
    EditText editCapacidades;

    private final String urlLocal = "http://192.168.1.5/ServiciosWeb%20PDM/diplomado/ws_diplomado_query.php";
    private final String urlHostingGratuito = "https://proyectopdm-g10.000webhostapp.com/diplomado/ws_diplomado_query.php";

    private final String urlLocal2 = "http://192.168.1.5/ServiciosWeb%20PDM/diplomado/ws_diplomado_todo.php";
    private final String urlHostingGratuito2 = "https://proyectopdm-g10.000webhostapp.com/diplomado/ws_diplomado_todo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diplomado_consultar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        helper = new ControlBDProyecto(this);
        editIdDiplomado = (EditText) findViewById(R.id.editIdDiplomado);
        editTitulo = (EditText) findViewById(R.id.editTitulo);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editCapacidades = (EditText) findViewById(R.id.editCapacidades);

        helper.abrir();
        List<Diplomado> listadiplomado = helper.getDiplomadoList();
        helper.cerrar();
        List<Diplomado> listaDiplomado_externa = ControladorServicio.obtenerListaDiplomadoExterno(urlHostingGratuito2,this);

        if(listadiplomado.size() > 0){

            // Create the adapter to convert the array to views
            DiplomadoAdapter adapter = new DiplomadoAdapter(this, listadiplomado);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.lvlItems);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "No hay diplomados en la base", Toast.LENGTH_SHORT).show();
        }
        if(listaDiplomado_externa.size() > 0){

            // Create the adapter to convert the array to views
            DiplomadoAdapter adapter= new DiplomadoAdapter(this, listaDiplomado_externa);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.lvlItemsExternos);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "No hay entidades capacitadoras en la base Externa", Toast.LENGTH_SHORT).show();
        }

    }
    public class DiplomadoAdapter extends ArrayAdapter<Diplomado> {
        public DiplomadoAdapter(Context context, List<Diplomado> diplomados) {
            super(context, 0, diplomados);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Diplomado diplomado = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_diplomado, parent, false);
            }
            // Lookup view for data population
            TextView codigo = (TextView) convertView.findViewById(R.id.codigodiplomado);
            TextView nombre = (TextView) convertView.findViewById(R.id.titulo);
            TextView descipcion = (TextView) convertView.findViewById(R.id.descripcion);
            TextView capacidades = (TextView) convertView.findViewById(R.id.capacidades);

            // Populate the data into the template view using the data object
            codigo.setText(diplomado.getIdDiplomado());
            nombre.setText(diplomado.getTitulo());
            descipcion.setText(diplomado.getDescripcion());
            capacidades.setText(diplomado.getCapacidades());
            // Return the completed view to render on screen
            return convertView;
        }
    }
    public void consultarDiplomado(View v) {
        helper.abrir();
        Diplomado diplomado = helper.consultarDiplomado(editIdDiplomado.getText().toString());
        helper.cerrar();
        if(diplomado == null)
            Toast.makeText(this, "Diplomado con codigo" + editIdDiplomado.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editCapacidades.setText(diplomado.getCapacidades());
            editDescripcion.setText(diplomado.getDescripcion());
            editTitulo.setText(diplomado.getTitulo());

        }
    }
    public void consultarDiplomadoExterna(View v) {

        String codigo = editIdDiplomado.getText().toString();
        String url = null;
        Diplomado diplomado=null;
        switch (v.getId()) {
            case R.id.btn_Local:
                url = urlLocal+ "?id_diplomado=" + codigo;
                diplomado=ControladorServicio.consultarDiplomadoExterno(url, this);
                break;
            case R.id.btn_Externo:
                url = urlHostingGratuito+ "?id_diplomado=" + codigo;
                diplomado=ControladorServicio.consultarDiplomadoExterno(url, this);
                break;
        }
        if(diplomado == null)
            Toast.makeText(this, "Entidad capacitadora con codigo " + editIdDiplomado.getText().toString() +
                    " no encontrada", Toast.LENGTH_LONG).show();
        else{
            editTitulo.setText(diplomado.getTitulo());
            editDescripcion.setText(diplomado.getDescripcion());
            editCapacidades.setText(diplomado.getCapacidades());

        }
    }
    public void limpiarTexto(View v) {
        editIdDiplomado.setText("");
        editTitulo.setText("");
        editDescripcion.setText("");
        editCapacidades.setText("");
    }
}