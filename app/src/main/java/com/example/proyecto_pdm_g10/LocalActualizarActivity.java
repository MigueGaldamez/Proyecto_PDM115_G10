package com.example.proyecto_pdm_g10;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class LocalActualizarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    Spinner spinnerUbicacion;
    Spinner spinnerTipoUbicacion;
    EditText editNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_actualizar);
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
    public void actualizarLocal(View v)
    {
        Local local= new Local();

        String idU = spinnerUbicacion.getSelectedItem().toString();

        String[] ubicacionId = idU.split(" - ");
        String idT = spinnerTipoUbicacion.getSelectedItem().toString();
        String[] tipoUbicacionId = idT.split(" - ");

        local.setIdLocal(editId.getText().toString());
        local.setIdUbicacion(ubicacionId[0].trim());
        local.setIdTipoUbicacion(tipoUbicacionId[0].trim());
        local.setNombre(editNombre.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(local);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v)
    {
        editId.setText("");
        spinnerUbicacion.setSelection(0);
        spinnerTipoUbicacion.setSelection(0);
        editNombre.setText("");
    }
}