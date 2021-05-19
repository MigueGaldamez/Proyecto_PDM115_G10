package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.proyecto_pdm_g10.AreaDiplomado;
import com.example.proyecto_pdm_g10.AreaInteres;
import com.example.proyecto_pdm_g10.Capacitador;
import com.example.proyecto_pdm_g10.Local;
import com.example.proyecto_pdm_g10.R;
import com.example.proyecto_pdm_g10.cz13016_crud.CapacitacionCrud;

import java.util.ArrayList;
import java.util.List;

public class cz13016ObtenerRegistroCapacitacion extends AppCompatActivity {

    //op es asignada a un parametro que se le envia de de la ctivity CZ13016Insertar... o CZ13016Actualizar..
    //Si op = 1; entonces esta activiy manda a la xml que renderize los locales
    //Si op = 2; entonces esta activiy manda a la xml que renderize las Areas de Diplomado
    //Si op = 3; entonces esta activiy manda a la xml que renderize las Areas de Inters
    //Si op = 4; entonces esta activiy manda a la xml que renderize los Capacitadores


    CapacitacionCrud helper;


    List<Local> listLocal;
    List<AreaInteres> listAreain;
    List<Capacitador> listCapacitador;
    List<AreaDiplomado> listAreaDip;

    List<String> listForeignKey = new ArrayList<>();


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

               // id, idUbicacion, idTipoUbicacion
                String foreign_key = listForeignKey.get(position);

                myBundleRetorno.putInt("op", op);
                myBundleRetorno.putString("idItem",listItem.get(position));
                myBundleRetorno.putString("foreignKey", foreign_key);

                int activity = myBundle.getInt("activity");
                if (activity == 1) {
                    Intent myIntent = new Intent(cz13016ObtenerRegistroCapacitacion.this, CZ13016InsertarCapacitacion.class);
                    myIntent.putExtras(myBundleRetorno);
                    startActivity(myIntent);
                }else {
                    Intent myIntent = new Intent(cz13016ObtenerRegistroCapacitacion.this, CZ13016ActualizarCapacitacionActivity.class);
                    myIntent.putExtras(myBundleRetorno);
                    startActivity(myIntent);
                }



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
                helper.abrir();
               listLocal = helper.allLoca();
                helper.cerrar();

                for (int i=0; i < listLocal.size(); i++){
                    datoItem = listLocal.get(i).getNombre();
                    listItem.add(datoItem);

                    String foreignkey = listLocal.get(i).getIdLocal()+listLocal.get(i).getIdUbicacion()+listLocal.get(i).getIdTipoUbicacion();
                    listForeignKey.add(foreignkey);

                }
                break;
            case 2:
                helper.abrir();
                listAreaDip = helper.allAreaDip();
                helper.cerrar();

                for (int i=0; i<listAreaDip.size(); i++){
                 //   listAreaDip.get(i).getIdDiplomado()
                  datoItem = listAreaDip.get(i).getNombre();
                    listItem.add(datoItem);

                    listForeignKey.add(listAreaDip.get(i).getIdAreaDiplomado());
                }
                break;
            case 3:
                helper.abrir();
                listAreain = helper.allAreasInteres();
                helper.cerrar();

                for (int i=0; i<listAreain.size(); i++){
                    datoItem = listAreain.get(i).getNombre();
                    listItem.add(datoItem);

                    listForeignKey.add(listAreain.get(i).getCodigo());
                }
                break;
            case 4:

                helper.abrir();
                listCapacitador = helper.allCapacitador();
                helper.cerrar();

                for (int i=0; i<listCapacitador.size(); i++){
                    datoItem = listCapacitador.get(i).getNombres()+" "+listCapacitador.get(i).getApellidos();
                    listItem.add(datoItem);

                    listForeignKey.add(listCapacitador.get(i).getIdCapacitador());
                }
                break;
        }
    }
}