package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.proyecto_pdm_g10.AreaDiplomado;
import com.example.proyecto_pdm_g10.ControlBDProyecto;
import com.example.proyecto_pdm_g10.R;
import com.example.proyecto_pdm_g10.cz13016_crud.CapacitacionCrud;
import com.example.proyecto_pdm_g10.cz13016_entities.Capacitacion;

import java.util.ArrayList;
import java.util.List;

public class CZ13016ObtenerRegistroHorarioActivity extends AppCompatActivity {

    Bundle myBundleRetorno = new Bundle();
    Bundle  myBundle;
    ListView listCapacitaciones;
    ArrayList<String> listItem = new ArrayList<>();
    List<Capacitacion> listCapacitacion = new ArrayList<>();
    List<Capacitacion> listCapacitacionValida = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cz13016_obtener_registro_horario);

        listCapacitaciones = findViewById(R.id.listCapacitaciones);
        myBundle = this.getIntent().getExtras();

        setListCapacitacion();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, listItem);
        listCapacitaciones.setAdapter(adapter);

        listCapacitaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer foreign_key = listCapacitacionValida.get(position).getIdCapacitacion();

                myBundleRetorno.putString("idItem",listItem.get(position));
                myBundleRetorno.putInt("foreignKey", foreign_key);

                int activity = myBundle.getInt("activity");
                if (activity == 1) {
                    Intent myIntent = new Intent(CZ13016ObtenerRegistroHorarioActivity.this, CZ13016InsertarHorarioActivity.class);
                    myIntent.putExtras(myBundleRetorno);
                    startActivity(myIntent);
                }else {
                    Intent myIntent = new Intent(CZ13016ObtenerRegistroHorarioActivity.this, CZ13016ActualizarHorarioActivity.class);
                    myIntent.putExtras(myBundleRetorno);
                    startActivity(myIntent);
                }

            }
        });

    }

    private void setListCapacitacion() {
        CapacitacionCrud helper = new CapacitacionCrud(this);
        ControlBDProyecto helper1 = new ControlBDProyecto(this);

        helper.abrir();
        listCapacitacion =  helper.allCapacitacion();
        helper.cerrar();



        for (int i=0; i<listCapacitacion.size(); i++){

            helper1.abrir();
            AreaDiplomado areaDiplomado = helper1.consultarAreaDiplomado(listCapacitacion.get(i).getIdAreaDip());
            helper1.cerrar();

            if (areaDiplomado != null){
                listItem.add(areaDiplomado.getNombre());
                listCapacitacionValida.add(listCapacitacion.get(i));
            }

        }

    }
}