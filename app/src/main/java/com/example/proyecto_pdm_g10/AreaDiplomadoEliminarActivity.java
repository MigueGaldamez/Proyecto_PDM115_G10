package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AreaDiplomadoEliminarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdAreaDiplomado;


    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_diplomado_eliminar);

        helper = new ControlBDProyecto(this);
        editIdAreaDiplomado = (EditText) findViewById(R.id.editIdAreaDiplomado);

    }
    public void eliminarAreaDiplomado(View v){
        String regEliminadas;
        AreaDiplomado areaDiplomado=new AreaDiplomado();
        areaDiplomado.setIdAreaDiplomado(editIdAreaDiplomado.getText().toString());
        helper.abrir();
        regEliminadas=helper.eliminar(areaDiplomado);
        helper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
    }
}