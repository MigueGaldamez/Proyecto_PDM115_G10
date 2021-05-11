package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_pdm_g10.R;
import com.example.proyecto_pdm_g10.cz13016_crud.CapacitacionCrud;
import com.example.proyecto_pdm_g10.cz13016_entities.Capacitacion;

public class CZ13016InsertarCapacitacion extends AppCompatActivity {
    CapacitacionCrud helper;

    EditText idPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cz13016_insertar_capacitacion);
       helper  = new CapacitacionCrud(this);
    }

    public void guardarCap(View view) {
        idPrecio = findViewById(R.id.idPrecio);
        String regInsertados = "none";
        Capacitacion cap = new Capacitacion();
        cap.setIdCapacitacion(11);
        cap.setDescrip("Descripcion del proyecto");
        cap.setPrecio(50);
        cap.setIdLocal(2);
        cap.setIdAreaDip(1);
        cap.setIdAreaIn(2);
        cap.setIdCapacitador(7);

       helper.abrir();
       regInsertados = helper.insertarCapacitacion(cap);
       helper.cerrar();
       Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarText(View view) {
    }
}