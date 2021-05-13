package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LocalInsertarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editIdUbicacion;
    EditText editIdTipoUbicacion;
    EditText editNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_insertar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editIdUbicacion = (EditText) findViewById(R.id.editidUbicacion);
        editIdTipoUbicacion = (EditText) findViewById(R.id.editidTipoUbicacion);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }
    public void insertarLocal(View v)
    {
        String regInsertados;
        String id=editId.getText().toString();
        String idUbicacion=editIdUbicacion.getText().toString();
        String idTipoUbicacion=editIdTipoUbicacion.getText().toString();
        String nombre=editNombre.getText().toString();
        Local local= new Local();
        local.setIdLocal(id);
        local.setIdUbicacion(idUbicacion);
        local.setIdTipoUbicacion(idTipoUbicacion);
        local.setNombre(nombre);
        helper.abrir();
        regInsertados=helper.insertar(local);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v)
    {
        editId.setText("");
        editIdUbicacion.setText("");
        editIdTipoUbicacion.setText("");
        editNombre.setText("");
    }

}