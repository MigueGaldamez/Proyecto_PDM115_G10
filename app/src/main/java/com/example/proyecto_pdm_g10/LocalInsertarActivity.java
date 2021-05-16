package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LocalInsertarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    Spinner spinnerUbicacion;
    Spinner spinnerTipoUbicacion;
    EditText editNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_insertar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        spinnerUbicacion = (Spinner)findViewById(R.id.idUbicacion);
        spinnerTipoUbicacion = (Spinner)findViewById(R.id.idTipoUbicacion);
        editNombre = (EditText) findViewById(R.id.editNombre);

        helper.abrir();
        String[] camposUbicacion = {"id","nombre"};
        helper.consultarListaObjeto(2,"ubicacion",camposUbicacion);
        ArrayAdapter<CharSequence> adaptadorUbicacion = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,helper.listaObjeto);
        spinnerUbicacion.setAdapter(adaptadorUbicacion);

        String[] camposTipoUbicacion = {"id","nombre"};
        helper.consultarListaObjeto(3,"tipoUbicacion",camposTipoUbicacion);
        ArrayAdapter<CharSequence> adaptadorTipoUbicacion = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,helper.listaObjeto);
        spinnerTipoUbicacion.setAdapter(adaptadorTipoUbicacion);
        helper.cerrar();

    }
    public void insertarLocal(View v)
    {
        String regInsertados;
        String id = editId.getText().toString();

        String idU = spinnerUbicacion.getSelectedItem().toString();
        String[] ubicacionId = idU.split(" - ");
        String idT = spinnerTipoUbicacion.getSelectedItem().toString();
        String[] tipoUbicacionId = idT.split(" - ");

        String nombre=editNombre.getText().toString();
        Local local= new Local();
        local.setIdLocal(id);
        local.setIdUbicacion(ubicacionId[0].trim());
        local.setIdTipoUbicacion(tipoUbicacionId[0].trim());
        local.setNombre(nombre);
        helper.abrir();
        regInsertados=helper.insertar(local);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v)
    {
        editId.setText("");
        spinnerUbicacion.setSelection(0);
        spinnerTipoUbicacion.setSelection(0);
        editNombre.setText("");
    }

}