package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CapacitadorConsultarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdCapacitador;
    EditText editNombres;
    EditText editApellidos;
    EditText editTelefono;
    EditText editCorreo;
    EditText editProfesion;
    EditText editIdEntidadCapacitadora;

    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;

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
    }
}