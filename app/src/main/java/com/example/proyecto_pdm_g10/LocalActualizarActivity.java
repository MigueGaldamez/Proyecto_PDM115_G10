package com.example.proyecto_pdm_g10;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class LocalActualizarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editIdUbicacion;
    EditText editIdTipoUbicacion;
    EditText editNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_actualizar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editIdUbicacion = (EditText) findViewById(R.id.editidUbicacion);
        editIdTipoUbicacion = (EditText) findViewById(R.id.editidTipoUbicacion);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }
    public void actualizarLocal(View v)
    {
        Local local= new Local();
        local.setIdLocal(editId.getText().toString());
        local.setIdUbicacion(editIdUbicacion.getText().toString());
        local.setIdTipoUbicacion(editIdTipoUbicacion.getText().toString());
        local.setNombre(editNombre.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(local);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v)
    {
        editId.setText("");
        editIdUbicacion.setText("");
        editIdTipoUbicacion.setText("");
        editNombre.setText("");
    }
}