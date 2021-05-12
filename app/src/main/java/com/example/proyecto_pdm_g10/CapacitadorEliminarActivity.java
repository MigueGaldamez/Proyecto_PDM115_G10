package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CapacitadorEliminarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdCapacitador;


    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacitador_eliminar);

        helper = new ControlBDProyecto(this);
        editIdCapacitador = (EditText) findViewById(R.id.editIdCapacitador);
    }
    public void eliminarCapacitador(View v){
        String regEliminadas;
        Capacitador capacitador=new Capacitador();
        capacitador.setIdCapacitador(editIdCapacitador.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(capacitador);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
}