package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_pdm_g10.AreaDiplomado;
import com.example.proyecto_pdm_g10.AreaInteres;
import com.example.proyecto_pdm_g10.Capacitador;
import com.example.proyecto_pdm_g10.ControlBDProyecto;
import com.example.proyecto_pdm_g10.Local;
import com.example.proyecto_pdm_g10.R;
import com.example.proyecto_pdm_g10.cz13016_crud.CapacitacionCrud;
import com.example.proyecto_pdm_g10.cz13016_entities.Capacitacion;

public class CZ13016ConsultarCapacitacionActivity extends AppCompatActivity {

    CapacitacionCrud helper = new CapacitacionCrud(this);
    ControlBDProyecto helperControlBd = new ControlBDProyecto(this);

    EditText idCapacitacion;
    EditText idPrecio;
    EditText idLocal;
    EditText idAreaDip;
    EditText idAreaIn;
    EditText idCapacitador;
    EditText idDescripcion;
    EditText asistenciaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cz13016_consultar_capacitacion);
        inicializarDatos();
    }

    private void inicializarDatos() {
        idCapacitacion = findViewById(R.id.idCapacitacion);
        idPrecio = findViewById(R.id.idPrecio);
        idLocal = findViewById(R.id.idLocal);
        idAreaDip = findViewById(R.id.idAreaDip);
        idAreaIn = findViewById(R.id.idAreaIn);
        idCapacitador = findViewById(R.id.idCapacitador);
        idDescripcion = findViewById(R.id.idDescripcion);
        asistenciaTotal = findViewById(R.id.idAsistencia);
    }

    public void getCapacitacionItem(View view) {


        AreaDiplomado areaDiplomado  = new AreaDiplomado();

        if (!idCapacitacion.getText().toString().isEmpty()){
            Integer id = Integer.parseInt(idCapacitacion.getText().toString());
            Capacitacion capacitacion = new Capacitacion();
            helper.abrir();
            capacitacion = helper.extraerCapacitacion(id);
            helper.cerrar();
            if (capacitacion != null){

                helper.abrir();
                Local localItem = helper.extraerLocal(capacitacion.getIdLocal());
                helper.cerrar();

                helperControlBd.abrir();
                areaDiplomado = helperControlBd.consultarAreaDiplomado(capacitacion.getIdAreaDip());
                AreaInteres areaInteres = helperControlBd.consultarAreaInteres(capacitacion.getIdAreaIn()) ;
                Capacitador capacitador = helperControlBd.consultarCapacitador(capacitacion.getIdCapacitador());
                helperControlBd.cerrar();

                idCapacitacion.setText(capacitacion.getIdCapacitacion().toString());

                idPrecio.setText(String.valueOf(capacitacion.getPrecio()));

                idLocal.setText(localItem.getNombre());


                idAreaDip.setText(areaDiplomado.getNombre());


                idAreaIn.setText(areaInteres.getNombre());

                idCapacitador.setText(capacitador.getNombres()+capacitador.getApellidos());

                idDescripcion.setText(capacitacion.getDescrip());

                asistenciaTotal.setText(capacitacion.getAsistenciaTotal().toString());


            }else
                Toast.makeText(this, "No se encontre ningun registro con el id "+ id, Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(this, "Campo vacio ingrese un id ", Toast.LENGTH_SHORT).show();

    }


    public void limpiarEdiText(View view) {
        idCapacitacion.setText("");
        idPrecio.setText("");
        idLocal.setText("");
        idAreaDip.setText("");
        idAreaIn.setText("");
        idCapacitador.setText("");
        idDescripcion.setText("");
        asistenciaTotal.setText("");
    }
}