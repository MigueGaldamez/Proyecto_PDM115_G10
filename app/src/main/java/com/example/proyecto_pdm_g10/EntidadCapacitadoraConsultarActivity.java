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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EntidadCapacitadoraConsultarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editCodigo;
    EditText editNombre;
    EditText editDescripcion;
    EditText editTelefono;
    EditText editCorreo;
    RadioButton radioExterna,radioInterna;

    private final String urlLocal = "http://192.168.1.5/ServiciosWeb%20PDM/entidadCapacitadora/ws_entidadCapacitadora_query.php";
    private final String urlHostingGratuito = "https://proyectopdm-g10.000webhostapp.com/entidadCapacitadora/ws_entidadCapacitadora_query.php";

    private final String urlLocal2 = "http://192.168.1.5/ServiciosWeb%20PDM/entidadCapacitadora/ws_entidadCapacitadora_todo.php";
    private final String urlHostingGratuito2 = "https://proyectopdm-g10.000webhostapp.com/entidadCapacitadora/ws_entidadCapacitadora_todo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entidad_capacitadora_consultar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        helper = new ControlBDProyecto(this);
        editCodigo = (EditText) findViewById(R.id.editCodigo);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editCorreo = (EditText) findViewById(R.id.editCorreo);

        radioExterna = (RadioButton) findViewById(R.id.radio_externa);
        radioInterna = (RadioButton) findViewById(R.id.radio_interna);

        helper.abrir();
        List<EntidadCapacitadora> listaentidadCapacitadora = helper.getEntidadCapacitadoraList();
        helper.cerrar();
        List<EntidadCapacitadora> listaEntidadCapacitadora_externa = ControladorServicio.obtenerListEntidadCapacitadoraExterno(urlHostingGratuito2,this);

        if(listaentidadCapacitadora.size() > 0){

            // Create the adapter to convert the array to views
            EntidadCapacitadoraAdapter adapter= new EntidadCapacitadoraAdapter(this, listaentidadCapacitadora);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.lvlItems);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "No hay entidades capacitadoras en la base", Toast.LENGTH_SHORT).show();
        }

        if(listaEntidadCapacitadora_externa.size() > 0){

            // Create the adapter to convert the array to views
            EntidadCapacitadoraAdapter adapter= new EntidadCapacitadoraAdapter(this, listaEntidadCapacitadora_externa);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.lvlItemsExternos);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "No hay entidades capacitadoras en la base Externa", Toast.LENGTH_SHORT).show();
        }
    }
    public class EntidadCapacitadoraAdapter extends ArrayAdapter<EntidadCapacitadora> {
        public EntidadCapacitadoraAdapter(Context context, List<EntidadCapacitadora> entidades) {
            super(context, 0, entidades);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            EntidadCapacitadora entidadCapacitadora = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_entidad_capacitadora, parent, false);
            }
            // Lookup view for data population
            TextView codigo = (TextView) convertView.findViewById(R.id.codigo);
            TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
            TextView descipcion = (TextView) convertView.findViewById(R.id.descripcion);
            TextView telefono= (TextView) convertView.findViewById(R.id.telefono);
            TextView correo= (TextView) convertView.findViewById(R.id.correo);
            RadioButton interna =(RadioButton) convertView.findViewById(R.id.radio_interna);
            RadioButton externa =(RadioButton) convertView.findViewById(R.id.radio_externa);


            // Populate the data into the template view using the data object
            codigo.setText(entidadCapacitadora.getCodigo());
            nombre.setText(entidadCapacitadora.getNombre());
            descipcion.setText(entidadCapacitadora.getDescripcion());
            telefono.setText(entidadCapacitadora.getTelefono());
            correo.setText(entidadCapacitadora.getCorreo());


            if(entidadCapacitadora.getTipo().equals("E"))
            {
                externa.setChecked(true);
            }
            if(entidadCapacitadora.getTipo().equals("I"))
            {
                interna.setChecked(true);
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }
    public void consultarEntidadCapacitadora(View v) {
        helper.abrir();
        EntidadCapacitadora entidadCapacitadora = helper.consultarEntidadCapacitadora(editCodigo.getText().toString());
        helper.cerrar();
        if(entidadCapacitadora == null)
            Toast.makeText(this, "Entidad capacitadora con codigo " + editCodigo.getText().toString() +
                    " no encontrada", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(entidadCapacitadora.getNombre());
            editDescripcion.setText(entidadCapacitadora.getDescripcion());
            editTelefono.setText(entidadCapacitadora.getTelefono());
            editCorreo.setText(entidadCapacitadora.getCorreo());

            if(entidadCapacitadora.getTipo().equals("E"))
            {
                radioExterna.setChecked(true);
            }
            if(entidadCapacitadora.getTipo().equals("I"))
            {
                radioInterna.setChecked(true);
            }
        }
    }
    public void consultarEntidadCapacitadoraExterna(View v) {

        String codigo = editCodigo.getText().toString();
        String url = null;
        EntidadCapacitadora entidadCapacitadora=null;
        switch (v.getId()) {
            case R.id.btn_Local:
                url = urlLocal+ "?id_entidad=" + codigo;
                entidadCapacitadora=ControladorServicio.consultarEntidadCapacitadoraExterna(url, this);
                break;
            case R.id.btn_Externo:
                url = urlHostingGratuito+ "?id_entidad=" + codigo;
                entidadCapacitadora=ControladorServicio.consultarEntidadCapacitadoraExterna(url, this);
                break;
        }
        if(entidadCapacitadora == null)
            Toast.makeText(this, "Entidad capacitadora con codigo " + editCodigo.getText().toString() +
                    " no encontrada", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(entidadCapacitadora.getNombre());
            editDescripcion.setText(entidadCapacitadora.getDescripcion());
            editTelefono.setText(entidadCapacitadora.getTelefono());
            editCorreo.setText(entidadCapacitadora.getCorreo());

            if(entidadCapacitadora.getTipo().equals("E"))
            {
                radioExterna.setChecked(true);
            }
            if(entidadCapacitadora.getTipo().equals("I"))
            {
                radioInterna.setChecked(true);
            }
        }
    }
    public void limpiarTexto(View v) {
        editCodigo.setText("");
        editNombre.setText("");
        editDescripcion.setText("");
        editTelefono.setText("");
        editCorreo.setText("");

    }
}