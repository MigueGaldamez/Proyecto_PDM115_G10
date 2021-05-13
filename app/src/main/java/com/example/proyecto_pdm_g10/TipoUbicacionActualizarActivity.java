package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TipoUbicacionActualizarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editNombre;
    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_ubicacion_actualizar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }
    public void actualizarTipoUbicacion(View v)
    {
        TipoUbicacion tipoUbicacion = new TipoUbicacion();
        tipoUbicacion.setId(editId.getText().toString());
        tipoUbicacion.setNombre(editNombre.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(tipoUbicacion);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v)
    {
        editId.setText("");
        editNombre.setText("");
    }
}