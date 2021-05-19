package com.example.proyecto_pdm_g10;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class TipoUbicacionInsertarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editNombre;
    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_ubicacion_insertar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }
    public void insertarTipoUbicacion(View v)
    {
        String id = editId.getText().toString();
        String nombre=editNombre.getText().toString();
        String regInsertados;
        TipoUbicacion tipoUbicacion=new TipoUbicacion();
        tipoUbicacion.setId(id);
        tipoUbicacion.setNombre(nombre);
        helper.abrir();
        regInsertados=helper.insertar(tipoUbicacion);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v)
    {
        editId.setText("");
        editNombre.setText("");
    }
}