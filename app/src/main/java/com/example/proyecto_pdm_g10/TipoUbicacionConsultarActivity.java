package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TipoUbicacionConsultarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editNombre;
    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_ubicacion_consultar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }
    public void consultarTipoUbicacion(View v)
    {
        helper.abrir();
        TipoUbicacion tipoUbicacion = helper.consultarTipoUbicacion(editId.getText().toString());
        helper.cerrar();
        if(tipoUbicacion == null)
            Toast.makeText(this, "Tipo de Ubicacion con id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(tipoUbicacion.getNombre());
        }
    }
    public void limpiarTexto(View v)
    {
        editId.setText("");
        editNombre.setText("");
    }
}