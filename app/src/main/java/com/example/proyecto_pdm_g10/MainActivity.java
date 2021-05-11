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

import com.example.proyecto_pdm_g10.cz13016_activities.CZ13016InsertarCapacitacion;

public class MainActivity extends ListActivity {

    //Agregen las tablas y las activities de las tablas
    String[] menu={"Tabla AreaInteres","Tabla EntidadCapacitadora", "01 Tabla Capacitacion"};
    String[] activities={"AreaInteresMenuActivity","EntidadCapacitadoraMenuActivity", "CZ13016InsertarCapacitacion"};

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
            String miOpcion = nombreValue.substring(0,7);
            Toast.makeText(this, miOpcion, Toast.LENGTH_SHORT).show();

            try{
                if (miOpcion.equals("CZ13016")){
                    Class<?> clase=Class.forName("com.example.proyecto_pdm_g10.cz13016_activities."+nombreValue);
                    Intent inte = new Intent(this,clase);
                    inte.putExtra("idsesion",idsesion);
                    this.startActivity(inte);
                }else {
                    Class<?> clase=Class.forName("com.example.proyecto_pdm_g10."+nombreValue);
                    Intent inte = new Intent(this,clase);
                    inte.putExtra("idsesion",idsesion);
                    this.startActivity(inte);
                }

            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }

    }


}