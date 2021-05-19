package com.example.proyecto_pdm_g10;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class LocalEliminarActivity extends Activity
{
    EditText editId;
    ControlBDProyecto controlhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_eliminar);
        controlhelper=new ControlBDProyecto (this);
        editId=(EditText)findViewById(R.id.editid);
    }
    public void eliminarLocal(View v)
    {
        String regEliminadas;
        Local local=new Local();
        local.setIdLocal(editId.getText().toString());
        controlhelper.abrir();
        regEliminadas=controlhelper.eliminar(local);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
}