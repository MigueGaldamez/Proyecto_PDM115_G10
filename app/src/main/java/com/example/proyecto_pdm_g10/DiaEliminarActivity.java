package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DiaEliminarActivity extends Activity {

    ControlBDProyecto helper;
    EditText editIdDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_eliminar);
        helper = new ControlBDProyecto(this);
        editIdDia = (EditText) findViewById(R.id.editIdDia);
    }
    public void eliminarDia(View v){
        String regEliminadas;
        Dia dias=new Dia();
        dias.setIdDia(editIdDia.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminarDia(dias);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
}