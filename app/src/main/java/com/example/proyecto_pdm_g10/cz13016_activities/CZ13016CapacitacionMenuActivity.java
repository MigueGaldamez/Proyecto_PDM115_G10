package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proyecto_pdm_g10.MainActivity;
import com.example.proyecto_pdm_g10.R;

public class CZ13016CapacitacionMenuActivity extends ListActivity {

    ListView capacitacionMenu;

    String[] menu = {"Insertar Capacitacion", "Elininar Capacitacin","Actualizar Capacitacion", "Consultar Capacitaicon"};
    String[] activities = {"CZ13016InsertarCapacitacion","CZ13016EliminarCapacitacionActivity","CZ13016ActualizarCapacitacionActivity", "CZ13016ConsultarCapacitacionActivity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));

        capacitacionMenu = findViewById(R.id.capacitacionMenu);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String nombreValue=activities[position];

        try{
                Class<?> clase=Class.forName("com.example.proyecto_pdm_g10.cz13016_activities."+nombreValue);
                Intent inte = new Intent(this,clase);
                this.startActivity(inte);

        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }
}