package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_pdm_g10.R;
import com.example.proyecto_pdm_g10.cz13016_crud.CapacitacionCrud;

public class CZ13016EliminarCapacitacionActivity extends AppCompatActivity {

    CapacitacionCrud helper;

    EditText idCapacitacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cz13016_eliminar_capacitacion);

        idCapacitacion = findViewById(R.id.idCapacitacion);

        helper  = new CapacitacionCrud(this);

    }

    public void eliminarCApacitacion(View v){
        Integer idCap = Integer.parseInt(idCapacitacion.getText().toString());
        String regInsertados = "none";
        helper.abrir();
        regInsertados = helper.eliminarCapacitacion(idCap);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
}