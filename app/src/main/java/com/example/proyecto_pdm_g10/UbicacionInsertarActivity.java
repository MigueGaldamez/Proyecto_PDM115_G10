package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class UbicacionInsertarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editNombre;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_insertar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editNombre = (EditText) findViewById(R.id.editNombre);
        spinner = (Spinner)findViewById(R.id.idFacultad);

        helper.abrir();
        String[] campos = {"id","nombre"};
        helper.consultarListaObjeto(1,"facultad",campos);
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,helper.listaObjeto);
        spinner.setAdapter(adaptador);
        helper.cerrar();
    }
    public void insertarUbicacion(View v) {
        String regInsertados;
        String id=editId.getText().toString();
        String nombre=editNombre.getText().toString();

        String idF = spinner.getSelectedItem().toString();
        String[] facultadId = idF.split(" - ");

        Ubicacion ubicacion= new Ubicacion();
        ubicacion.setIdUbicacion(id);
        ubicacion.setIdFacultad(facultadId[0].trim());
        ubicacion.setnombre(nombre);
        helper.abrir();
        regInsertados=helper.insertar(ubicacion);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editId.setText("");
        spinner.setSelection(0);
        editNombre.setText("");
    }

}