package com.example.proyecto_pdm_g10;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class FacultadConsultarActivity extends Activity
{
    ControlBDProyecto helper;
    EditText editId;
    EditText editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultad_consultar);
        helper = new ControlBDProyecto(this);
        editId = (EditText) findViewById(R.id.editid);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }
    public void consultarFacultad(View v) {
        helper.abrir();
        Facultad facultad = helper.consultarFacultad(editId.getText().toString());
        helper.cerrar();
        if(facultad == null)
            Toast.makeText(this, "Facultad con id " +
                    editId.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(facultad.getnombre());
        }
    }
    public void limpiarTexto(View v)
    {
        editId.setText("");
        editNombre.setText("");
    }
}