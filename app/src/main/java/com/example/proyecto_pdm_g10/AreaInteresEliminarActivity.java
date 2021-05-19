package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AreaInteresEliminarActivity extends Activity {
    EditText editCodigo;
    ControlBDProyecto helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_interes_eliminar);

        helper = new ControlBDProyecto(this);
        editCodigo = (EditText) findViewById(R.id.editCodigo);

    }
    public void eliminarAreaInteres(View v){
        String regEliminadas;
        AreaInteres areaInteres=new AreaInteres();
        areaInteres.setCodigo(editCodigo.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(areaInteres);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
}