package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CapacitadorInsertarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdCapacitador;
    EditText editNombres;
    EditText editApellidos;
    EditText editTelefono;
    EditText editCorreo;
    EditText editProfesion;

    Spinner spinner;

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
        spinner = (Spinner)findViewById(R.id.editIdEntidadCapacitadora);

        helper.abrir();
        String[] campos = {"codigo","nombre"};
        helper.consultarListaObjeto(4,"entidadCapacitadora",campos);
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,helper.listaObjeto);
        spinner.setAdapter(adaptador);
        helper.cerrar();
    }
    public void insertarCapacitador(View v) {

        String idCapacitador = editIdCapacitador.getText().toString();
        String nombres = editNombres.getText().toString();
        String apellidos = editApellidos.getText().toString();
        String telefono = editTelefono.getText().toString();
        String correo = editCorreo.getText().toString();
        String profesion = editProfesion.getText().toString();

        String idF = spinner.getSelectedItem().toString();
        String[] entidadCapacitadoraId = idF.split(" - ");

        String regInsertados;

        Capacitador capacitador = new Capacitador();
        capacitador.setIdCapacitador(idCapacitador);
        capacitador.setNombres(nombres);
        capacitador.setApellidos(apellidos);
        capacitador.setTelefono(telefono);
        capacitador.setCorreo(correo);
        capacitador.setProfesion(profesion);
        capacitador.setIdEntidadCapacitadora(entidadCapacitadoraId[0].trim());
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
        spinner.setSelection(0);
    }



}