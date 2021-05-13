package com.example.proyecto_pdm_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    //Agregen las tablas y las activities de las tablas
    String[] menu={"Tabla AreaInteres","Tabla EntidadCapacitadora","Tabla Diplomado","Tabla Area Diplomado","Tabla Capacitador", "Tabla Empleado"};
    String[] activities={"AreaInteresMenuActivity","EntidadCapacitadoraMenuActivity","DiplomadoMenuActivity","AreaDiplomadoMenuActivity","CapacitadorMenuActivity","EmpleadoMenuActivity" };


    ControlBDProyecto BDhelper;
    //variable de la sesion de usuario
    String idsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menu));
        BDhelper=new ControlBDProyecto(this);

        //sirve para manejar el id de la sesion del usuario
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idsesion = extras.getString("idsesion");
            //The key argument here must match that used in the other activity
        }
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

            String nombreValue=activities[position];

            try{
                Class<?> clase=Class.forName("com.example.proyecto_pdm_g10."+nombreValue);
                Intent inte = new Intent(this,clase);
                inte.putExtra("idsesion",idsesion);
                this.startActivity(inte);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }

    }


}