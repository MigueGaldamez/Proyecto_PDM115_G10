package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CapacitadorInsertarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdCapacitador;
    EditText editNombres;
    EditText editApellidos;
    EditText editTelefono;
    EditText editCorreo;
    EditText editProfesion;
    EditText editIdEntidadCapacitadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacitador_insertar);

        helper = new ControlBDProyecto(this);
        editIdCapacitador = (EditText) findViewById(R.id.editIdCapacitador);
        editNombres = (EditText) findViewById(R.id.editNombres);
        editApellidos = (EditText) findViewById(R.id.editApellidos);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editCorreo = (EditText) findViewById(R.id.editCorreo);
        editProfesion = (EditText) findViewById(R.id.editProfesion);
        editIdEntidadCapacitadora = (EditText) findViewById(R.id.editIdEntidadCapacitadora);
    }
    public void insertarCapacitador(View v) {

        String idCapacitador = editIdCapacitador.getText().toString();
        String nombres = editNombres.getText().toString();
        String apellidos = editApellidos.getText().toString();
        String telefono = editTelefono.getText().toString();
        String correo = editCorreo.getText().toString();
        String profesion = editProfesion.getText().toString();
        String idEntidadCapacitadora = editIdEntidadCapacitadora.getText().toString();
        String regInsertados;

        Capacitador capacitador = new Capacitador();
        capacitador.setIdCapacitador(idCapacitador);
        capacitador.setNombres(nombres);
        capacitador.setApellidos(apellidos);
        capacitador.setTelefono(telefono);
        capacitador.setCorreo(correo);
        capacitador.setProfesion(profesion);
        capacitador.setIdEntidadCapacitadora(idEntidadCapacitadora);
        helper.abrir();
        regInsertados=helper.insertar(capacitador);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();



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