package com.example.proyecto_pdm_g10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class TipoUbicacionEliminarActivity extends Activity
{
    EditText editId;
    ControlBDProyecto controlhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_ubicacion_eliminar);
        controlhelper=new ControlBDProyecto (this);
        editId=(EditText)findViewById(R.id.editid);
    }
    public void eliminarTipoUbicacion(View v)
    {
        String regEliminadas;
        TipoUbicacion tipoUbicacion=new TipoUbicacion();
        tipoUbicacion.setId(editId.getText().toString());
        controlhelper.abrir();
        regEliminadas=controlhelper.eliminar(tipoUbicacion);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
}