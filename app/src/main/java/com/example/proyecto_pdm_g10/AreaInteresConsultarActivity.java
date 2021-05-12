package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AreaInteresConsultarActivity extends Activity {

    ControlBDProyecto helper;
    EditText editCodigo;
    EditText editNombre;
    EditText editDescripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_interes_consultar);
        helper = new ControlBDProyecto(this);
        editCodigo = (EditText) findViewById(R.id.editCodigo);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
    }
    public void consultarAreaInteres(View v) {
        helper.abrir();
        AreaInteres areaInteres = helper.consultarAreaInteres(editCodigo.getText().toString());
        helper.cerrar();
        if(areaInteres == null)
            Toast.makeText(this, "Area de interes con codigo " + editCodigo.getText().toString() +
                    " no encontrada", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(areaInteres.getNombre());
            editDescripcion.setText(areaInteres.getDescripcion());

        }
    }
    public void limpiarTexto(View v) {
        editCodigo.setText("");
        editNombre.setText("");
        editDescripcion.setText("");

    }
}