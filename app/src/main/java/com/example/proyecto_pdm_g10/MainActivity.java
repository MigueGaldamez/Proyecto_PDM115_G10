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

    String[] menu={"Tabla AreaInteres","Tabla EntidadCapacitadora", "Logearse","LLenar Base de Datos"};
    String[] activities={"AreaInteresMenuActivity","EntidadCapacitadoraMenuActivity","LoginActivity"};


    ControlBDProyecto BDhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menu));
        BDhelper=new ControlBDProyecto(this);

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        if(position!=3){

            String nombreValue=activities[position];

            try{
                Class<?> clase=Class.forName("com.example.proyecto_pdm_g10."+nombreValue);
                Intent inte = new Intent(this,clase);
                this.startActivity(inte);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }


        }else{

            //CODIGO PARA LLENAR BASE DE DATOS
            BDhelper.abrir();
            String tost=BDhelper.llenarBDUsuario();
            BDhelper.cerrar();
            Toast.makeText(this, tost, Toast.LENGTH_SHORT).show();
        }
    }


}