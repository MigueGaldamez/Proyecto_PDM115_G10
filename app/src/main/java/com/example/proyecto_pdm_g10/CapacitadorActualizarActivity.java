package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CapacitadorActualizarActivity extends Activity {

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
        setContentView(R.layout.activity_capacitador_actualizar);

        helper = new ControlBDProyecto(this);
        editIdCapacitador = (EditText) findViewById(R.id.editIdCapacitador);
        editNombres = (EditText) findViewById(R.id.editNombres);
        editApellidos = (EditText) findViewById(R.id.editApellidos);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editCorreo = (EditText) findViewById(R.id.editCorreo);
        editProfesion = (EditText) findViewById(R.id.editProfesion);
        editIdEntidadCapacitadora = (EditText) findViewById(R.id.editIdEntidadCapacitadora);
    }
    public void actualizarCapacitador(View v) {
        Capacitador capacitador = new Capacitador();
        capacitador.setIdCapacitador(editIdCapacitador.getText().toString());
        capacitador.setNombres(editNombres.getText().toString());
        capacitador.setApellidos(editApellidos.getText().toString());
        capacitador.setTelefono(editTelefono.getText().toString());
        capacitador.setCorreo(editCorreo.getText().toString());
        capacitador.setProfesion(editProfesion.getText().toString());
        capacitador.setIdEntidadCapacitadora(editIdEntidadCapacitadora.getText().toString());

        helper.abrir();
        String estado = helper.actualizar(capacitador);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
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