package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_pdm_g10.Local;
import com.example.proyecto_pdm_g10.R;
import com.example.proyecto_pdm_g10.cz13016_crud.CapacitacionCrud;
import com.example.proyecto_pdm_g10.cz13016_entities.Capacitacion;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class CZ13016InsertarCapacitacion extends AppCompatActivity {
    CapacitacionCrud helper;


    EditText idCapacitacion;
    EditText idPrecio;
    EditText idLocal;
    EditText idAreaDip;
    EditText idAreaIn;
    EditText idCapacitador;
    EditText idDescripcion;


   Bundle myBundleRetornado;
   Bundle myBundle;

    SharedPreferences.Editor editor;

    int opcion;

    Context thisContex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cz13016_insertar_capacitacion);

        thisContex = this;
        SharedPreferences sharprefs = getSharedPreferences("thisInstance", thisContex.MODE_PRIVATE);


       helper  = new CapacitacionCrud(this);

       myBundle = new Bundle();

       inicializarDatos();
       detEditTex();



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

        SharedPreferences sharprefs;


        if (myBundleRetornado != null){
            int opcion = myBundleRetornado.getInt("op");
            ArrayList<String> lista = myBundleRetornado.getStringArrayList("lista");

            switch (opcion){

                case 1:
                   // idLocal.setText(myBundleRetornado.getString("idItem"));
                    sharprefs = getPreferences(thisContex.MODE_PRIVATE);
                    editor = sharprefs.edit();

                    editor.putString("idL",myBundleRetornado.getString("idItem"));
                    editor.putString("localKey",myBundleRetornado.getString("foreignKey"));
                    editor.commit();
                    getDataSave();

                    Toast.makeText(this, myBundleRetornado.getString("foreignKey"), Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    sharprefs = getPreferences(thisContex.MODE_PRIVATE);
                    editor = sharprefs.edit();

                    editor.putString("idAdip",myBundleRetornado.getString("idItem"));
                    editor.putString("areaDiKey",myBundleRetornado.getString("foreignKey"));
                    editor.commit();
                    getDataSave();

                    Toast.makeText(this, myBundleRetornado.getString("foreignKey"), Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    sharprefs = getPreferences(thisContex.MODE_PRIVATE);
                    editor = sharprefs.edit();

                    editor.putString("idAin",myBundleRetornado.getString("idItem"));
                    editor.putString("areaInDiKey",myBundleRetornado.getString("foreignKey"));
                    editor.commit();
                    getDataSave();

                    Toast.makeText(this, myBundleRetornado.getString("foreignKey"), Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                     sharprefs = getPreferences(thisContex.MODE_PRIVATE);
                     editor = sharprefs.edit();

                    editor.putString("idCor",myBundleRetornado.getString("idItem"));
                    editor.putString("capacitorKey",myBundleRetornado.getString("foreignKey"));
                    editor.commit();
                    getDataSave();

                    Toast.makeText(this, myBundleRetornado.getString("foreignKey"), Toast.LENGTH_SHORT).show();
                    break;

            }
        }

    }


    public void guardarCap(View view) {

        SharedPreferences sharprefs = getPreferences(thisContex.MODE_PRIVATE);


        String regInsertados = "none";
        Capacitacion cap = new Capacitacion();

        if (!(idCapacitacion.getText().toString().isEmpty() || idPrecio.getText().toString().isEmpty() || idLocal.getText().toString().isEmpty() || idAreaDip.getText().toString().isEmpty() || idAreaIn.getText().toString().isEmpty() || idCapacitador.getText().toString().isEmpty() || idDescripcion.getText().toString().isEmpty())){

            cap.setIdCapacitacion(Integer.parseInt(idCapacitacion.getText().toString()));
            cap.setDescrip(idDescripcion.getText().toString());
            cap.setPrecio(Float.parseFloat(idPrecio.getText().toString()));

            cap.setIdLocal(sharprefs.getString("localKey",""));/**/
            cap.setIdAreaDip(sharprefs.getString("areaDiKey",""));
            cap.setIdAreaIn(sharprefs.getString("areaInDiKey",""));
            cap.setIdCapacitador(sharprefs.getString("capacitorKey",""));
            // db.execSQL("CREATE TABLE capacitacion(idCapacitacion INTEGER NOT NULL PRIMARY KEY,descripcion VARCHAR(100),precio REAL,idLocal INTEGER, idAreasDip CHAR(5), idAreaIn VARCHAR(7), idCapacitador CHAR(5));");
            helper.abrir();
            regInsertados = helper.insertarCapacitacion(cap);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();

    }

    public void limpiarText(View view) {
        idCapacitacion.setText("");
        idPrecio.setText("");
        idLocal.setText("");
        idAreaDip.setText("");
        idAreaIn.setText("");
        idCapacitador.setText("");
        idDescripcion.setText("");

    }

    public void obtenerLocal(View view) {
        setTearPara();
        opcion = 1;
       // myBundle.putInt("opcion", opcion);
        Intent myIntent = new Intent(CZ13016InsertarCapacitacion.this, cz13016ObtenerRegistroCapacitacion.class);
        //onSaveInstanceState();
        myIntent.putExtra("opcion", opcion);
        myIntent.putExtra("activity",1);
        startActivity(myIntent);
    }


    public void obtenerAreaDis(View view) {
        setTearPara();
        opcion = 2;
        Intent myIntent = new Intent(this, cz13016ObtenerRegistroCapacitacion.class);
        //onSaveInstanceState();
        myIntent.putExtra("opcion", opcion);
        myIntent.putExtra("activity",1);
        startActivity(myIntent);
    }

    public void obtenerAreaInte(View view) {
        setTearPara();
        opcion = 3;
        Intent myIntent = new Intent(this, cz13016ObtenerRegistroCapacitacion.class);
        myIntent.putExtra("opcion", opcion);
        myIntent.putExtra("activity",1);
        startActivity(myIntent);
    }

    public void obtenerCapacitador(View view) {
        setTearPara();
        opcion = 4;

        Intent myIntent = new Intent(this, cz13016ObtenerRegistroCapacitacion.class);
        myIntent.putExtra("opcion", opcion);
        myIntent.putExtra("activity",1);
        startActivity(myIntent);
    }

    private void setTearPara() {
        SharedPreferences sharprefs = getPreferences(thisContex.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharprefs.edit();

        editor.putString("idCon",idCapacitacion.getText().toString());
        editor.putString("idP",idPrecio.getText().toString());
        editor.putString("idL",idLocal.getText().toString());
        editor.putString("idAdip",idAreaDip.getText().toString());
        editor.putString("idAin",idAreaIn.getText().toString());
        editor.putString("idCor",idCapacitador.getText().toString());
        editor.putString("descrip",idDescripcion.getText().toString());
        editor.commit();

    }

    private void getDataSave(){

        SharedPreferences sharprefs = getPreferences(thisContex.MODE_PRIVATE);


        idCapacitacion = findViewById(R.id.idCapacitacion);
        idCapacitacion.setText(sharprefs.getString("idCon",""));

        idPrecio = findViewById(R.id.idPrecio);
        idPrecio.setText(sharprefs.getString("idP",""));

        idLocal = findViewById(R.id.idLocal);
        idLocal.setText(sharprefs.getString("idL",""));


        idAreaDip = findViewById(R.id.idAreaDip);
        idAreaDip.setText(sharprefs.getString("idAdip",""));

        idAreaIn = findViewById(R.id.idAreaIn);
        idAreaIn.setText(sharprefs.getString("idAin",""));

        idCapacitador = findViewById(R.id.idCapacitador);
        idCapacitador.setText(sharprefs.getString("idCor",""));

        idDescripcion = findViewById(R.id.idDescripcion);
        idDescripcion.setText(sharprefs.getString("descrip",""));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (editor != null){
            editor.remove("thisInstance").commit();
        }
        Intent myIntent = new Intent(this,CZ13016CapacitacionMenuActivity.class);
        startActivity(myIntent);
    }

    public void llenadoTemporal(View view) {
        //***********LOCALES*************************************************************************
        //db.execSQL("CREATE TABLE local(id VARCHAR(7) NOT NULL ,idUbicacion VARCHAR(7) NOT NULL, idTipoUbicacion VARCHAR(7) NOT NULL, nombre VARCHAR(35) NOT NULL ,PRIMARY KEY(id, idUbicacion, idTipoUbicacion));");
        Local l= new Local();
        l.setIdLocal("xxxxxxx");
        l.setIdTipoUbicacion("lllllll");
        l.setIdUbicacion("ccccccc");
        l.setNombre("Salon el Espino");

        Local l1= new Local();
        l1.setIdLocal("xxxxkkk");
        l1.setIdTipoUbicacion("llllllj");
        l1.setIdUbicacion("cccccww");
        l1.setNombre("Salon el Marmol");

        helper.abrir();
        helper.insertarLocal(l);
        String incert =  helper.insertarLocal(l1);
        helper.cerrar();

        Toast.makeText(this, " Local "+ incert, Toast.LENGTH_SHORT).show();
        //*******************************************************************************************

    }
}