package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AreaDiplomadoConsultarActivity extends Activity {
    ControlBDProyecto helper;
    EditText editIdAreaDiplomado;
    EditText editNombre;
    EditText editDescripcion;
    EditText editIdDiplomado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_diplomado_consultar);

        helper = new ControlBDProyecto(this);
        editIdAreaDiplomado = (EditText) findViewById(R.id.editIdAreaDiplomado);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editDescripcion = (EditText) findViewById(R.id.editDescripcion);
        editIdDiplomado = (EditText) findViewById(R.id.editIdDiplomado);
    }
    public void consultarAreaDiplomado(View v) {
        helper.abrir();
        AreaDiplomado areaDiplomado = helper.consultarAreaDiplomado(editIdAreaDiplomado.getText().toString());
        helper.cerrar();
        if(areaDiplomado == null)
            Toast.makeText(this, "Area Diplomado con codigo" + editIdAreaDiplomado.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editIdDiplomado.setText(areaDiplomado.getIdDiplomado());
            editDescripcion.setText(areaDiplomado.getDescripcion());
            editNombre.setText(areaDiplomado.getNombre());

        }
    }
    public void limpiarTexto(View v) {
        editIdDiplomado.setText("");
        editNombre.setText("");
        editDescripcion.setText("");
        editIdAreaDiplomado.setText("");
    }
}