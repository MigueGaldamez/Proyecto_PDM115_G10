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

public class CapacitadorActualizarActivity extends Activity {

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
        setContentView(R.layout.activity_capacitador_actualizar);

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
    public void actualizarCapacitador(View v) {
        Capacitador capacitador = new Capacitador();
        capacitador.setIdCapacitador(editIdCapacitador.getText().toString());
        capacitador.setNombres(editNombres.getText().toString());
        capacitador.setApellidos(editApellidos.getText().toString());
        capacitador.setTelefono(editTelefono.getText().toString());
        capacitador.setCorreo(editCorreo.getText().toString());
        capacitador.setProfesion(editProfesion.getText().toString());
        String idF = spinner.getSelectedItem().toString();
        String[] entidadCapacitadoraId = idF.split(" - ");
        capacitador.setIdEntidadCapacitadora(entidadCapacitadoraId[0].trim());

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
        spinner.setSelection(0);
    }
}