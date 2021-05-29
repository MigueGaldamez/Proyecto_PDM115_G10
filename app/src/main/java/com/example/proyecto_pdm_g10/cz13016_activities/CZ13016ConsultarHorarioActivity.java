package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_pdm_g10.AreaDiplomado;
import com.example.proyecto_pdm_g10.ControlBDProyecto;
import com.example.proyecto_pdm_g10.Dia;
import com.example.proyecto_pdm_g10.R;
import com.example.proyecto_pdm_g10.cz13016_crud.CapacitacionCrud;
import com.example.proyecto_pdm_g10.cz13016_crud.HorarioCrud;
import com.example.proyecto_pdm_g10.cz13016_entities.Capacitacion;
import com.example.proyecto_pdm_g10.cz13016_entities.Horario;

public class CZ13016ConsultarHorarioActivity extends AppCompatActivity {

    EditText idHorario;
    EditText idHoraIni;
    EditText idHoraFin;
    EditText idCapacitacion;
    EditText idDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cz13016_consultar_horario);

        inicializarEditText();


    }




    private void inicializarEditText() {
        idHorario = findViewById(R.id.idHorario);
        idHoraIni = findViewById(R.id.idHoraIni);
        idHoraFin = findViewById(R.id.idHoraFin);
        idCapacitacion = findViewById(R.id.idCapacitacion);
        idDia = findViewById(R.id.idDia);

    }

    public void obtenerHorario(View view) {


        HorarioCrud helper = new HorarioCrud(this);
        ControlBDProyecto helperControl = new ControlBDProyecto(this);
        CapacitacionCrud helperCapacitacion = new CapacitacionCrud(this);


        helper.abrir();
        Horario horario = helper.extraerHorario(idHorario.getText().toString());
        helper.cerrar();

        if (horario != null){
            idHoraIni.setText(horario.getHoraInicio());
            idHoraFin.setText(horario.getHoraFin());

            helperCapacitacion.abrir();
            Capacitacion capa = helperCapacitacion.extraerCapacitacion(horario.getIdCapacitacion());
            helperCapacitacion.cerrar();


            if (capa != null){
                helperControl.abrir();
                AreaDiplomado areaDip = helperControl.consultarAreaDiplomado(capa.getIdAreaDip());
                Dia dia = helperControl.consultarDia(horario.getIdDia());
                helperControl.cerrar();
                if (areaDip != null && dia != null){
                    idCapacitacion.setText(areaDip.getNombre());
                    idDia.setText(dia.getNomDia()+"  "+dia.getFecha());
                }

            }

        }
        else {
            vaciarEditText();
            Toast.makeText(this, "ID "+idHorario.getText().toString()+" Sin registro", Toast.LENGTH_SHORT).show();
        }
    }

    private void vaciarEditText() {
        idHoraIni.setText("");
        idHoraFin.setText("");
        idCapacitacion.setText("");
        idDia.setText("");
    }
}