package com.example.proyecto_pdm_g10.cz13016_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyecto_pdm_g10.AreaDiplomado;
import com.example.proyecto_pdm_g10.ControlBDProyecto;
import com.example.proyecto_pdm_g10.Dia;
import com.example.proyecto_pdm_g10.R;
import com.example.proyecto_pdm_g10.cz13016_crud.CapacitacionCrud;
import com.example.proyecto_pdm_g10.cz13016_crud.HorarioCrud;
import com.example.proyecto_pdm_g10.cz13016_entities.Capacitacion;
import com.example.proyecto_pdm_g10.cz13016_entities.Horario;

import java.util.ArrayList;
import java.util.List;

public class CZ13016ActualizarHorarioActivity extends AppCompatActivity {

    EditText idHorario;
    EditText idHoraIni;
    EditText idHoraFin;
    EditText idCapacitacion;

    Spinner idDiaSpinner;

    Context thisContex;
    SharedPreferences.Editor editor;

    Bundle myBundleRetornado;
    Bundle myBundle;

    List<Dia> listDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cz13016_actualizar_horario);

        myBundleRetornado = this.getIntent().getExtras();


        thisContex = this;
        SharedPreferences sharprefs = getSharedPreferences("thisInstanceHorarioActualizar", thisContex.MODE_PRIVATE);


        iniEdititText();
        cargarSpinner();
        getEditTex();

    }

    private void cargarSpinner() {

        List<String> opciones = new ArrayList<>();

        HorarioCrud helper = new HorarioCrud(this);

        helper.abrir();
        listDia = helper.allLDia();
        helper.cerrar();

        for (int i=0; i<listDia.size(); i++){
            opciones.add(listDia.get(i).getNomDia()+"     "+listDia.get(i).getFecha());
        }

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, opciones);
        idDiaSpinner.setAdapter(adapterSpinner);

    }

    private void iniEdititText() {
        idHorario = findViewById(R.id.idHorario);
        idHoraIni = findViewById(R.id.idHoraIni);
        idHoraFin = findViewById(R.id.idHoraFin);
        idCapacitacion = findViewById(R.id.idCapacitacion);
        idDiaSpinner = findViewById(R.id.idDiaSpinner);
    }

    public void obtenerCapacitacion(View view) {
        if (!idCapacitacion.getText().toString().isEmpty()){
            setTearPara();
            Intent myIntent = new Intent(this, CZ13016ObtenerRegistroHorarioActivity.class);
            myIntent.putExtra("activity",2);
            startActivity(myIntent);
        }

    }

    private void setTearPara() {
        SharedPreferences sharprefs = getPreferences(thisContex.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharprefs.edit();

        editor.putString("idHrio",idHorario.getText().toString());
        editor.putString("hInicio",idHoraIni.getText().toString());
        editor.putString("hFin",idHoraFin.getText().toString());
        editor.putString("idCap",idCapacitacion.getText().toString());
        editor.putInt("idDia",idDiaSpinner.getSelectedItemPosition());
        editor.commit();

    }

    private void getEditTex() {

        SharedPreferences sharprefs = getPreferences(thisContex.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharprefs.edit();

        if (myBundleRetornado != null){


            editor.putString("idCap",myBundleRetornado.getString("idItem"));
            editor.putInt("capKey",myBundleRetornado.getInt("foreignKey"));
            editor.commit();
            restaurarActivity();

            // Toast.makeText(this, String.valueOf( myBundleRetornado.getInt("foreignKey")), Toast.LENGTH_SHORT).show();

        }

    }

    private void restaurarActivity(){

        SharedPreferences sharprefs = getPreferences(thisContex.MODE_PRIVATE);


        idHorario.setText(sharprefs.getString("idHrio",""));

        idHoraIni.setText(sharprefs.getString("hInicio",""));


        idHoraFin.setText(sharprefs.getString("hFin",""));

        idCapacitacion.setText(sharprefs.getString("idCap",""));

        int position = sharprefs.getInt("idDia",0);
        idDiaSpinner.setSelection(position);
        // Toast.makeText(this, listDia.get(position).getIdDia()+"  "+listDia.get(position).getNomDia()+"  "+listDia.get(position).getFecha(), Toast.LENGTH_SHORT).show();

    }




    public void actualizarHorario(View view) {

        SharedPreferences sharprefs = getPreferences(thisContex.MODE_PRIVATE);

        HorarioCrud helper = new HorarioCrud(this);



        String regInsertados = "none";
        Horario horario = new Horario();

        if (!(idHorario.getText().toString().isEmpty() || idHoraIni.getText().toString().isEmpty() || idHoraFin.getText().toString().isEmpty() || idCapacitacion.getText().toString().isEmpty())){

            horario.setIdHorario(idHorario.getText().toString());
            horario.setHoraInicio(idHoraIni.getText().toString());
            horario.setHoraFin(idHoraFin.getText().toString());

            horario.setIdCapacitacion(sharprefs.getInt("capKey",0));

            int pos = idDiaSpinner.getSelectedItemPosition();
            horario.setIdDia(listDia.get(pos).getIdDia());

            helper.abrir();
            regInsertados = helper.actualizarHorario(horario);
            helper.cerrar();
           // Toast.makeText(this, pos, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();


        // Toast.makeText(this, listDia.get(pos).getIdDia(), Toast.LENGTH_SHORT).show();

    }

    public void limpiarText(View view) {
    }

    @Override
    public void onBackPressed() {

        SharedPreferences sharprefs = getPreferences(thisContex.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharprefs.edit();

        super.onBackPressed();
        if (editor != null){
            editor.remove("thisInstanceHorarioActualizar").commit();
        }
        Intent myIntent = new Intent(this,CZ13016MenuHorarioActivity.class);
        startActivity(myIntent);
    }

    public void obtenerHorario(View view) {


        SharedPreferences sharprefs = getPreferences(thisContex.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharprefs.edit();

       if (!idHorario.getText().toString().isEmpty() && !idHorario.getText().toString().equals("0")){

           HorarioCrud helper = new HorarioCrud(this);
           helper.abrir();
           Horario horario = helper.extraerHorario(idHorario.getText().toString());
           helper.cerrar();


           ControlBDProyecto helperControl = new ControlBDProyecto(this);
           CapacitacionCrud helperCapacitacion = new CapacitacionCrud(this);



//&&   &&

           if (horario != null ){

               helperCapacitacion.abrir();
               Capacitacion capa = helperCapacitacion.extraerCapacitacion(horario.getIdCapacitacion());
               helperCapacitacion.cerrar();

               idHoraIni.setText(horario.getHoraFin());

               idHoraFin.setText(horario.getHoraFin());
               if (capa != null){

                   helperControl.abrir();
                   AreaDiplomado areaDip = helperControl.consultarAreaDiplomado(capa.getIdAreaDip());
                   helperControl.cerrar();

                   if (areaDip != null)
                    idCapacitacion.setText(String.valueOf(areaDip.getNombre()));
               }

               helper.abrir();
               Integer position = helper.extraerHorarioPosition(horario.getIdDia());
               helper.cerrar();
               if (position != null){
                   idDiaSpinner.setSelection(position);
               }



               //  int position = sharprefs.getInt("idDia",0);
               //

                   idHoraIni.setText(horario.getHoraInicio());
                   idHoraFin.setText(horario.getHoraFin());

                 //  idCapacitacion.setText(areaDip.getNombre());
                  // idDiaSpinner.setSelection(position);


               editor.putInt("capKey",horario.getIdCapacitacion());
               editor.commit();
           }else
           {
               vaciarEditText();
               Toast.makeText(this, "ID "+idHorario.getText().toString()+" Sin registro", Toast.LENGTH_SHORT).show();
           }

        }else
           Toast.makeText(this, "Ingrese un valor", Toast.LENGTH_SHORT).show();

    }

    private void vaciarEditText() {
        idHoraIni.setText("");
        idHoraFin.setText("");
        idCapacitacion.setText("");
       // idDia.setText("");
    }
}