package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DiplomadoEliminarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdDiplomado;
    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diplomado_eliminar);
        helper = new ControlBDProyecto(this);
        editIdDiplomado = (EditText) findViewById(R.id.editIdDiplomado);

    }
    public void eliminarDiplomado(View v){
        String regEliminadas;
        Diplomado diplomado=new Diplomado();
        diplomado.setIdDiplomado(editIdDiplomado.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(diplomado);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
}