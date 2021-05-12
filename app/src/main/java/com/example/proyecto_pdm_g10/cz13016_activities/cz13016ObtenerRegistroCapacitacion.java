package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.proyecto_pdm_g10.AreaInteres;
import com.example.proyecto_pdm_g10.R;
import com.example.proyecto_pdm_g10.cz13016_crud.CapacitacionCrud;

import java.util.ArrayList;
import java.util.List;

public class cz13016ObtenerRegistroCapacitacion extends AppCompatActivity {

    CapacitacionCrud helper;
    List<AreaInteres> listAreain;

    ListView listRegistros;
    ArrayList<String> listItem = new ArrayList<>();
    Bundle myBundleRetorno = new Bundle();
    Bundle  myBundle;
    int op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cz13016_obtener_registro_capacitacion2);

        myBundle = this.getIntent().getExtras();

        listRegistros = findViewById(R.id.listRegistros);

        helper  = new CapacitacionCrud(this);

        llenarListItem();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, listItem);
        listRegistros.setAdapter(adapter);

        listRegistros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent myIntent = new Intent(cz13016ObtenerRegistroCapacitacion.this, CZ13016InsertarCapacitacion.class);
                myBundleRetorno.putInt("op", op);
                myBundleRetorno.putString("idItem",listItem.get(position));
                myBundleRetorno.putStringArrayList("lista", myBundle.getStringArrayList("listacapa"));
               // editText();
                myIntent.putExtras(myBundleRetorno);
                startActivity(myIntent);

            }
        });
    }

    private void editText() {
        myBundleRetorno.putString("idC", myBundle.getString("idCapa").toString());
        myBundleRetorno.putString("idP", myBundle.getString("idPre").toString());
        myBundleRetorno.putString("idL",myBundle.getString("idLoc").toString());
        myBundleRetorno.putString("idA",myBundle.getString("idAreaDi"));
        myBundleRetorno.putString("idAre",myBundle.getString("idAreaIn"));
        myBundleRetorno.putString("idCapa",myBundle.getString("idCapacit"));
        myBundleRetorno.putString("idD",myBundle.getString("idDescrip"));
    }

    private void llenarListItem() {
         op = myBundle.getInt("opcion");
        String datoItem;
        switch (op){
            case 1:


                for (int i=0; i < listAreain.size(); i++){
                    datoItem = listItem.get(1);
                    listItem.add(datoItem);
                }
                break;
            case 2:
                for (int i=0; i<2; i++){
                  datoItem = "Area de Diplomado N°"+(i+1);
                    listItem.add(datoItem);
                }
                break;
            case 3:
                helper.abrir();
                listAreain  = helper.allAreasInteres();
                helper.cerrar();

                for (int i=0; i<listAreain.size(); i++){
                    datoItem = listAreain.get(i).getNombre();
                    listItem.add(datoItem);
                }
                break;
            case 4:
                for (int i=0; i<2; i++){
                    datoItem = "Capacitador N°"+(i+1);
                    listItem.add(datoItem);
                }
                break;
        }
    }
}