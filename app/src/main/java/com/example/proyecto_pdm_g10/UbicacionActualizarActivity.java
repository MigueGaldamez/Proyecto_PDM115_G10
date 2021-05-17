package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class UbicacionActualizarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editNombre;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_actualizar);
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
    public void actualizarUbicacion(View v)
    {
        Ubicacion ubicacion = new Ubicacion();

        String idF = spinner.getSelectedItem().toString();
        String[] facultadId = idF.split(" - ");

        ubicacion.setIdUbicacion(editId.getText().toString());
        ubicacion.setIdFacultad(facultadId[0].trim());
        ubicacion.setnombre(editNombre.getText().toString());

        helper.abrir();
        String estado = helper.actualizar(ubicacion);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editId.setText("");
        spinner.setSelection(0);
        editNombre.setText("");
    }
}