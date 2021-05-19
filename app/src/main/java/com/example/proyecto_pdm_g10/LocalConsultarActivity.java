package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LocalConsultarActivity extends Activity
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
        setContentView(R.layout.activity_local_consultar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editIdUbicacion = (EditText) findViewById(R.id.editidUbicacion);
        editIdTipoUbicacion = (EditText) findViewById(R.id.editidTipoUbicacion);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }
    public void consultarLocal(View v)
    {
        helper.abrir();
        Local local = helper.consultarLocal(editId.getText().toString());
        helper.cerrar();
        if(local == null)
        {
            Toast.makeText(this, "Local con id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        }
        else
        {
            editIdUbicacion.setText(local.getIdUbicacion());
            editIdTipoUbicacion.setText(local.getIdTipoUbicacion());
            editNombre.setText(local.getNombre());
        }
    }
    public void limpiarTexto(View v)
    {
        editId.setText("");
        editIdUbicacion.setText("");
        editIdTipoUbicacion.setText("");
        editNombre.setText("");
    }
}