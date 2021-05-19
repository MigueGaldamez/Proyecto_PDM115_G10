package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AreaDiplomadoConsultarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdAreaDiplomado;
    EditText editNombre;
    EditText editDescripcion;
    EditText editIdDiplomado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_diplomado_consultar);

        helper = new ControlBDProyecto(this);
        editIdAreaDiplomado = (EditText) findViewById(R.id.editIdAreaDiplomado);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editIdDiplomado = (EditText) findViewById(R.id.editIdDiplomado);

        helper.abrir();
        List<AreaDiplomado> listaareaDiplomado = helper.getAreaDiplomadoList();
        helper.cerrar();

        if(listaareaDiplomado.size() > 0){

            // Create the adapter to convert the array to views
            AreaDiplomadoAdapter adapter= new AreaDiplomadoAdapter(this, listaareaDiplomado);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.lvlItems);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "No hay Areas de diplomados en la base", Toast.LENGTH_SHORT).show();
        }
    }
    public class AreaDiplomadoAdapter extends ArrayAdapter<AreaDiplomado> {
        public AreaDiplomadoAdapter(Context context, List<AreaDiplomado> diplomados) {
            super(context, 0, diplomados);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            AreaDiplomado areaDiplomado = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_area_diplomado, parent, false);
            }
            // Lookup view for data population
            TextView codigo = (TextView) convertView.findViewById(R.id.codigo);
            TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
            TextView descipcion = (TextView) convertView.findViewById(R.id.descripcion);
            TextView iddiplomado = (TextView) convertView.findViewById(R.id.codigodiplomado);
            // Populate the data into the template view using the data object
            codigo.setText(areaDiplomado.getIdAreaDiplomado());
            nombre.setText(areaDiplomado.getNombre());
            descipcion.setText(areaDiplomado.getDescripcion());
            iddiplomado.setText(areaDiplomado.getIdDiplomado());
            // Return the completed view to render on screen
            return convertView;
        }
    }
    public void consultarAreaDiplomado(View v) {
        helper.abrir();
        AreaDiplomado areaDiplomado = helper.consultarAreaDiplomado(editIdAreaDiplomado.getText().toString());
        helper.cerrar();
        if(areaDiplomado == null)
            Toast.makeText(this, "Area Diplomado con codigo" + editIdAreaDiplomado.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editIdDiplomado.setText(areaDiplomado.getIdDiplomado());
            editDescripcion.setText(areaDiplomado.getDescripcion());
            editNombre.setText(areaDiplomado.getNombre());

        }
    }
    public void limpiarTexto(View v) {
        editIdDiplomado.setText("");
        editNombre.setText("");
        editDescripcion.setText("");
        editIdAreaDiplomado.setText("");
    }
}