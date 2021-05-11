package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AreaDiplomadoActualizarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdAreaDiplomado;
    EditText editNombre;
    EditText editDescripcion;
    EditText editIdDiplomado;

    ControlBDProyecto BDhelper = new ControlBDProyecto(this);
    String idsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_diplomado_actualizar);
        helper = new ControlBDProyecto(this);
        editIdAreaDiplomado = (EditText) findViewById(R.id.editIdAreaDiplomado);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editIdDiplomado = (EditText) findViewById(R.id.editIdDiplomado);
    }
    public void actualizarAreaDiplomado(View v) {
        AreaDiplomado areaDiplomado = new AreaDiplomado();
        areaDiplomado.setIdAreaDiplomado(editIdAreaDiplomado.getText().toString());
        areaDiplomado.setNombre(editNombre.getText().toString());
        areaDiplomado.setDescripcion(editDescripcion.getText().toString());
        areaDiplomado.setIdDiplomado(editIdDiplomado.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(areaDiplomado);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void limpiarTexto(View v) {
        editIdDiplomado.setText("");
        editNombre.setText("");
        editDescripcion.setText("");
        editIdAreaDiplomado.setText("");
    }
}