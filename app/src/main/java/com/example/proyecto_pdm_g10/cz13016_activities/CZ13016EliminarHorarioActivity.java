package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_pdm_g10.R;
import com.example.proyecto_pdm_g10.cz13016_crud.HorarioCrud;

public class CZ13016EliminarHorarioActivity extends AppCompatActivity {

    EditText idHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cz13016_eliminar_horario);

        idHora = findViewById(R.id.idHora);
    }

    public void eliminarHorario(View view) {
        HorarioCrud helper = new HorarioCrud(this);
        helper.abrir();
        String resultado = helper.eliminarHorario(Integer.parseInt(idHora.getText().toString()));
        helper.cerrar();

        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();

    }
}