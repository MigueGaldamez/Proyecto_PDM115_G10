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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AreaInteresConsultarActivity extends Activity {

    ControlBDProyecto helper;
    EditText editCodigo;
    EditText editNombre;
    EditText editDescripcion;

    private final String urlLocal = "http://192.168.1.5/ServiciosWeb%20PDM/areaInteres/ws_areaInteres_query.php";
    private final String urlHostingGratuito = "https://proyectopdm-g10.000webhostapp.com/areaInteres/ws_areaInteres_query.php";

    private final String urlLocal2 = "http://192.168.1.5/ServiciosWeb%20PDM/areaInteres/ws_areaInteres_todo.php";
    private final String urlHostingGratuito2 = "https://proyectopdm-g10.000webhostapp.com/areaInteres/ws_areaInteres_todo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_interes_consultar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        helper = new ControlBDProyecto(this);
        editCodigo = (EditText) findViewById(R.id.editCodigo);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);

        helper.abrir();
        List<AreaInteres> listaareaInteres = helper.getAreaInteresList();
        helper.cerrar();
        List<AreaInteres> listaareaInteres_externa = ControladorServicio.obtenerListAreaInteresExterno(urlHostingGratuito2,this);

        if(listaareaInteres.size() > 0){

            // Create the adapter to convert the array to views
            AreaInteresAdapter adapter = new AreaInteresAdapter(this, listaareaInteres);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.lvlItems);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "No hay empleados en la base", Toast.LENGTH_SHORT).show();
        }
        if(listaareaInteres_externa.size() > 0){

            // Create the adapter to convert the array to views
            AreaInteresAdapter adapter = new AreaInteresAdapter(this, listaareaInteres_externa);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.lvlItemsExternos);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "No hay empleados en la base externa", Toast.LENGTH_SHORT).show();
        }
    }
    public class AreaInteresAdapter extends ArrayAdapter<AreaInteres> {
        public AreaInteresAdapter(Context context, List<AreaInteres> areas) {
            super(context, 0, areas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            AreaInteres areaInteres = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_area_interes, parent, false);
            }
            // Lookup view for data population
            TextView codigo = (TextView) convertView.findViewById(R.id.codigo);
            TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
            TextView descipcion = (TextView) convertView.findViewById(R.id.descripcion);
            // Populate the data into the template view using the data object
            codigo.setText(areaInteres.getCodigo());
            nombre.setText(areaInteres.getNombre());
            descipcion.setText(areaInteres.getNombre());
            // Return the completed view to render on screen
            return convertView;
        }
    }

    public void consultarAreaInteres(View v) {
        helper.abrir();
        AreaInteres areaInteres = helper.consultarAreaInteres(editCodigo.getText().toString());
        helper.cerrar();
        if(areaInteres == null)
            Toast.makeText(this, "Area de interes con codigo " + editCodigo.getText().toString() +
                    " no encontrada", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(areaInteres.getNombre());
            editDescripcion.setText(areaInteres.getDescripcion());

        }
    }

    public void consultarAreaInteresExterno(View v) {

        String codigo = editCodigo.getText().toString();
        String url = null;
        AreaInteres areaInteres=null;
        switch (v.getId()) {
            case R.id.btn_Local:
                url = urlLocal+ "?id_area=" + codigo;
                areaInteres=ControladorServicio.consultarAreaInteresExterna(url, this);
                break;
            case R.id.btn_Externo:
                url = urlHostingGratuito+ "?id_area=" + codigo;
                areaInteres=ControladorServicio.consultarAreaInteresExterna(url, this);
                break;
        }
        if(areaInteres == null)
            Toast.makeText(this, "Area de interes con codigo " + editCodigo.getText().toString() +
                    " no encontrada", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(areaInteres.getNombre());
            editDescripcion.setText(areaInteres.getDescripcion());

        }
    }

    public void limpiarTexto(View v) {
        editCodigo.setText("");
        editNombre.setText("");
        editDescripcion.setText("");

    }
}