package com.example.valia.nombresdebebes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Valia on 17/12/2019.
 */

public class Crear_Nombre extends AppCompatActivity {

    RadioButton rb;
    String inicial;
    boolean compuesto;

    EditText editText;

    Spinner spinner;
    CheckBox checkBox;
    ArrayList<String> cantL;
    int cantLetras=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_nombres);

        cantL=new ArrayList<>();
        cantL.add(""+getString(R.string.cualquier_cant));
        cantL.add("3");
        cantL.add("4");
        cantL.add("5");
        cantL.add("6");
        cantL.add("7");
        cantL.add("8");
        cantL.add("9");
        cantL.add("10");


        inicial = "";
        compuesto=false;


        editText=(EditText) findViewById(R.id.editText3);
        checkBox= (CheckBox)findViewById(R.id.checkBox2);
        spinner = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,cantL);
        spinner.setAdapter(adapter);
        rb = (RadioButton) findViewById(R.id.radioButton);
        rb.setSelected(true);

        getSupportActionBar().setTitle(getString(R.string.crear_nombre));

    }


    public void ejecutar_nombres_creados(View view){

        if (spinner.getSelectedItemPosition()!=0){
            cantLetras=spinner.getSelectedItemPosition();
            cantLetras=cantLetras+2;
        }

        String sexo;
        if (rb.isChecked()) {
            sexo=""+getString(R.string.femenino);
        }else {
            sexo=""+getString(R.string.masculino);
        }
        inicial=editText.getText().toString();
        compuesto=checkBox.isChecked();


        if (inicial.equals("")
                || inicial.contains("0") || inicial.contains("1") || inicial.contains("2")
                || inicial.contains("3") || inicial.contains("4") || inicial.contains("5")
                || inicial.contains("6") || inicial.contains("7") || inicial.contains("8")
                || inicial.contains("9") ){
            editText.setError(""+getString(R.string.escriba_alguna_letra));
        }else{
                Intent i=new Intent(this,NombresCreados.class);
                i.putExtra("sexo",sexo);
                i.putExtra("letraI",inicial);
                i.putExtra("cantL",cantLetras);
                i.putExtra("compuesto",compuesto);
                startActivity(i);


        }
    }

}
