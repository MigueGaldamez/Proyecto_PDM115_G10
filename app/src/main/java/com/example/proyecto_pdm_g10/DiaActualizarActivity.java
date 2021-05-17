package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DiaActualizarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdDia;
    EditText editNomDia;
    EditText editFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_actualizar);

        helper = new ControlBDProyecto(this);
        editIdDia = (EditText) findViewById(R.id.editIdDia);
        editNomDia = (EditText) findViewById(R.id.editNomDia);
        editFecha = (EditText) findViewById(R.id.editFecha);
    }
    public void actualizarDia(View v) {
        Dia dias = new Dia();
        dias.setIdDia(editIdDia.getText().toString());
        dias.setNomDia(editNomDia.getText().toString());
        dias.setFecha(editFecha.getText().toString());
        helper.abrir();
        String estado = helper.actualizarDia(dias);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdDia.setText("");
        editNomDia.setText("");
        editFecha.setText("");
    }
}