package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_pdm_g10.R;
import com.example.proyecto_pdm_g10.cz13016_crud.CapacitacionCrud;
import com.example.proyecto_pdm_g10.cz13016_entities.Capacitacion;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class CZ13016InsertarCapacitacion extends AppCompatActivity {
    CapacitacionCrud helper;

    ArrayList<String> list = new ArrayList<>();

    EditText idCapacitacion;
    EditText idPrecio;
    EditText idLocal;
    EditText idAreaDip;
    EditText idAreaIn;
    EditText idCapacitador;
    EditText idDescripcion;


   Bundle myBundleRetornado;
   Bundle myBundle;

    int opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cz13016_insertar_capacitacion);
       helper  = new CapacitacionCrud(this);

       myBundle = new Bundle();

       detEditTex();
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

    }



    private void detEditTex() {
         myBundleRetornado = this.getIntent().getExtras();
        if (myBundleRetornado != null){
            int opcion = myBundleRetornado.getInt("op");
            ArrayList<String> lista = myBundleRetornado.getStringArrayList("lista");

            switch (opcion){

                case 1:
                    EditText idLocal = findViewById(R.id.idLocal);
                    idLocal.setText(myBundleRetornado.getString("idItem"));




                    idCapacitacion = findViewById(R.id.idCapacitacion);
                    idCapacitacion.setText(lista.get(0).toString());

                    idPrecio = findViewById(R.id.idPrecio);
                    idPrecio.setText(lista.get(1).toString());

                    idAreaDip = findViewById(R.id.idAreaDip);
                    idAreaDip.setText(lista.get(3).toString());

                    idAreaIn = findViewById(R.id.idAreaIn);
                    idAreaIn.setText(lista.get(4).toString());

                    idCapacitador = findViewById(R.id.idCapacitador);
                    idCapacitador.setText(lista.get(5).toString());

                    idDescripcion = findViewById(R.id.idDescripcion);
                    idDescripcion.setText(lista.get(6).toString());

                    break;
                case 2:
                    EditText idAreaDip = findViewById(R.id.idAreaDip);
                    idAreaDip.setText(myBundleRetornado.getString("idItem"));



                    idCapacitacion = findViewById(R.id.idCapacitacion);
                    idCapacitacion.setText(lista.get(0).toString());

                    idPrecio = findViewById(R.id.idPrecio);
                    idPrecio.setText(lista.get(1).toString());

                    idLocal = findViewById(R.id.idLocal);
                    idLocal.setText(lista.get(2).toString());

                    idAreaIn = findViewById(R.id.idAreaIn);
                    idAreaIn.setText(lista.get(4).toString());

                    idCapacitador = findViewById(R.id.idCapacitador);
                    idCapacitador.setText(lista.get(5).toString());

                    idDescripcion = findViewById(R.id.idDescripcion);
                    idDescripcion.setText(lista.get(6).toString());

                    break;
                case 3:
                    EditText idAreaIn = findViewById(R.id.idAreaIn);
                    idAreaIn.setText(myBundleRetornado.getString("idItem"));

                    idCapacitacion = findViewById(R.id.idCapacitacion);
                    idCapacitacion.setText(lista.get(0).toString());

                    idPrecio = findViewById(R.id.idPrecio);
                    idPrecio.setText(lista.get(1).toString());

                    idLocal = findViewById(R.id.idLocal);
                    idLocal.setText(lista.get(2).toString());

                    idAreaDip = findViewById(R.id.idAreaDip);
                    idAreaDip.setText(lista.get(3).toString());

                    idCapacitador = findViewById(R.id.idCapacitador);
                    idCapacitador.setText(lista.get(5).toString());

                    idDescripcion = findViewById(R.id.idDescripcion);
                    idDescripcion.setText(lista.get(6).toString());

                  /* list.add(idCapacitacion.getText().toString());
                    list.add(idPrecio.getText().toString());
                    list.add(idLocal.getText().toString());
                    list.add(idAreaDip.getText().toString());
                    list.add(idAreaIn.getText().toString());
                    list.add(idCapacitador.getText().toString());
                    list.add(idDescripcion.getText().toString());*/

                    break;
                case 4:
                    EditText idCapacitador = findViewById(R.id.idCapacitador);
                    idCapacitador.setText(myBundleRetornado.getString("idItem"));

                    idCapacitacion = findViewById(R.id.idCapacitacion);
                    idCapacitacion.setText(lista.get(0).toString());

                    idPrecio = findViewById(R.id.idPrecio);
                    idPrecio.setText(lista.get(1).toString());

                    idLocal = findViewById(R.id.idLocal);
                    idLocal.setText(lista.get(2).toString());


                    idAreaDip = findViewById(R.id.idAreaDip);
                    idAreaDip.setText(lista.get(3).toString());

                    idAreaIn = findViewById(R.id.idAreaIn);
                    idAreaIn.setText(lista.get(4).toString());


                    idDescripcion = findViewById(R.id.idDescripcion);
                    idDescripcion.setText(lista.get(6).toString());

                    break;

            }
        }

    }

    private void completarEditText(int opcion) {
        switch (opcion){

            case 1:
                EditText idLocal = findViewById(R.id.idLocal);
                idLocal.setText(myBundleRetornado.getString("item"));
                break;
            case 2:
                EditText idAreaDip = findViewById(R.id.idAreaDip);
                idAreaDip.setText(myBundleRetornado.getString("item"));
                break;
            case 3:
                EditText idAreaIn = findViewById(R.id.idAreaIn);
                idAreaIn.setText(myBundleRetornado.getString("item"));
                break;
            case 4:
                EditText idCapacitador = findViewById(R.id.idCapacitador);
                idCapacitador.setText(myBundleRetornado.getString("item"));
                break;

        }
    }

    public void guardarCap(View view) {
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

    public void obtenerLocal(View view) {
        setTearPara();
        opcion = 1;
       // myBundle.putInt("opcion", opcion);
        Intent myIntent = new Intent(CZ13016InsertarCapacitacion.this, cz13016ObtenerRegistroCapacitacion.class);
        //onSaveInstanceState();
        myIntent.putExtra("opcion", opcion);
        myIntent.putExtra("listacapa", list);
        startActivity(myIntent);
    }


    public void obtenerAreaDis(View view) {
        setTearPara();
        opcion = 2;
        Intent myIntent = new Intent(this, cz13016ObtenerRegistroCapacitacion.class);
        //onSaveInstanceState();
        myIntent.putExtra("opcion", opcion);
        myIntent.putExtra("listacapa", list);
        startActivity(myIntent);
    }

    public void obtenerAreaInte(View view) {
        setTearPara();
        opcion = 3;
        Intent myIntent = new Intent(this, cz13016ObtenerRegistroCapacitacion.class);
        myIntent.putExtra("opcion", opcion);
        myIntent.putExtra("listacapa", list);
        startActivity(myIntent);
    }

    public void obtenerCapacitador(View view) {
        setTearPara();
        opcion = 4;

        Intent myIntent = new Intent(this, cz13016ObtenerRegistroCapacitacion.class);
        myIntent.putExtra("opcion", opcion);
        myIntent.putExtra("listacapa", list);
        startActivity(myIntent);
    }

    private void setTearPara() {
        list.add(idCapacitacion.getText().toString());
        list.add(idPrecio.getText().toString());
        list.add(idLocal.getText().toString());
        list.add(idAreaDip.getText().toString());
        list.add(idAreaIn.getText().toString());
        list.add(idCapacitador.getText().toString());
        list.add(idDescripcion.getText().toString());

    }
}