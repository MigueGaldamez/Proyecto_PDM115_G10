package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DiaInsertarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdDia;
    EditText editNomDia;
    EditText editFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_insertar);
        helper = new ControlBDProyecto(this);
        editIdDia = (EditText) findViewById(R.id.editIdDia);
        editNomDia = (EditText) findViewById(R.id.editNomDia);
        editFecha = (EditText) findViewById(R.id.editFecha);
    }
    public void insertarDia(View v) {
        String idDia = editIdDia.getText().toString();
        String nomDia = editNomDia.getText().toString();
        String fecha = editFecha.getText().toString();
        String regInsertados;
        Dia dia = new Dia();
        dia.setIdDia(idDia);
        dia.setNomDia(nomDia);
        dia.setFecha(fecha);
        helper.abrir();
        regInsertados=helper.insertarDia(dia);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdDia.setText("");
        editNomDia.setText("");
        editFecha.setText("");
    }
}