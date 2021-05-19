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

public class CapacitadorConsultarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdCapacitador;
    EditText editNombres;
    EditText editApellidos;
    EditText editTelefono;
    EditText editCorreo;
    EditText editProfesion;
    EditText editIdEntidadCapacitadora;
    EditText editCapacitaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacitador_consultar);
        helper = new ControlBDProyecto(this);
        editIdCapacitador = (EditText) findViewById(R.id.editIdCapacitador);
        editNombres = (EditText) findViewById(R.id.editNombres);
        editApellidos = (EditText) findViewById(R.id.editApellidos);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editCorreo = (EditText) findViewById(R.id.editCorreo);
        editProfesion = (EditText) findViewById(R.id.editProfesion);
        editIdEntidadCapacitadora = (EditText) findViewById(R.id.editIdEntidadCapacitadora);
        editCapacitaciones = (EditText) findViewById(R.id.editCapacitaciones);

        helper.abrir();
        List<Capacitador> listacapacitador = helper.getCapacitadorList();
        helper.cerrar();

        if(listacapacitador.size() > 0){

            // Create the adapter to convert the array to views
            CapacitadorAdapter adapter= new CapacitadorAdapter(this, listacapacitador);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.lvlItems);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "No hay Areas de diplomados en la base", Toast.LENGTH_SHORT).show();
        }
    }
    public class CapacitadorAdapter extends ArrayAdapter<Capacitador> {
        public CapacitadorAdapter(Context context, List<Capacitador> capacitadors) {
            super(context, 0, capacitadors);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Capacitador capacitador = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_capacitador, parent, false);
            }
            // Lookup view for data population
            TextView codigo = (TextView) convertView.findViewById(R.id.codigocapacitador);
            TextView nombres = (TextView) convertView.findViewById(R.id.nombres);
            TextView apellidos = (TextView) convertView.findViewById(R.id.apellidos);

            TextView telefono = (TextView) convertView.findViewById(R.id.telefono);
            TextView correo = (TextView) convertView.findViewById(R.id.correo);
            TextView profesion = (TextView) convertView.findViewById(R.id.profesion);
            TextView codigoentidad = (TextView) convertView.findViewById(R.id.identidadcapacitadora);
            TextView capacitaciones = (TextView) convertView.findViewById(R.id.capacitaciones);
            // Populate the data into the template view using the data object
            codigo.setText(capacitador.getIdCapacitador());
            nombres.setText(capacitador.getNombres());
            apellidos.setText(capacitador.getApellidos());
            telefono.setText(capacitador.getTelefono());
            correo.setText(capacitador.getCorreo());
            profesion.setText(capacitador.getProfesion());
            capacitaciones.setText(capacitador.getCapacitacionesDadas().toString());


            // Return the completed view to render on screen
            codigoentidad.setText(capacitador.getIdEntidadCapacitadora());
            return convertView;
        }
    }
    public void consultarCapacitador(View v) {
        helper.abrir();
        Capacitador capacitador = helper.consultarCapacitador(editIdCapacitador.getText().toString());
        helper.cerrar();
        if(capacitador == null)
            Toast.makeText(this, "Capacitador con codigo" + editIdCapacitador.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{

            editNombres.setText(capacitador.getNombres());
            editApellidos.setText(capacitador.getApellidos());
            editTelefono.setText(capacitador.getTelefono());
            editCorreo.setText(capacitador.getCorreo());
            editProfesion.setText(capacitador.getProfesion());
            editIdEntidadCapacitadora.setText(capacitador.getIdEntidadCapacitadora());
            editCapacitaciones.setText(capacitador.getCapacitacionesDadas().toString());
        }
    }
    public void limpiarTexto(View v) {
        editIdCapacitador.setText("");
        editNombres.setText("");
        editApellidos.setText("");
        editTelefono.setText("");
        editCorreo.setText("");
        editProfesion.setText("");
        editIdEntidadCapacitadora.setText("");
        editCapacitaciones.setText("");
    }
}