package com.example.valia.nombresdebebes;

import android.animation.Animator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by rudyng on 1/26/2022.
 */

public class Datos_Nombres1 extends AppCompatActivity {

    String nombre_seleccionado="";
    ArrayList<PersonaSign> personas;
    DATOS mService1;
    boolean ejecutado = false;

    PersonaSign personaSeleccionada;

    ImageView idImageSexo;
    TextView idTextNombre;
    TextView idTextViewOrigen;
    TextView idTextViewSignificado;
    ImageView idImageFavOno;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_nombres_1);

        Bundle datos = getIntent().getExtras();
        nombre_seleccionado=datos.getString("NOMBRE_SELECCIONADO");

        //Toast.makeText(this, ""+nombre_seleccionado, Toast.LENGTH_SHORT).show();

        Intent in = new Intent(this, DATOS.class);
        this.bindService(in, mConnection1, Context.BIND_AUTO_CREATE);

        personas=new ArrayList<>();

        idImageSexo = (ImageView)findViewById(R.id.idImageSexo);
        idTextNombre = (TextView)findViewById(R.id.idTextNombre);
        idTextViewOrigen = (TextView)findViewById(R.id.idTextViewOrigen);
        idTextViewSignificado = (TextView)findViewById(R.id.idTextViewSignificado);
        idImageFavOno = (ImageView)findViewById(R.id.idImageFavOno);

        //Carga personas de la base de datos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!ejecutado){
                    cargar();
                    ejecutado=true;
                }
            }
        },100);

        getSupportActionBar().setTitle(getString(R.string.seleccionado));

    }


    public void cargar(){
        personas=mService1.getPersonasSign();

        mService1.cargarFavoritosSign(this);
        //Lo que vaya a iniciar con la lista va desde aqui
        personaSeleccionada=buscarPersona(nombre_seleccionado);
        if (personaSeleccionada==null){
            return;
        }
        //Toast.makeText(this, ""+personaSeleccionada.getNombre(), Toast.LENGTH_SHORT).show();
        //Aqui va el metodo para mostrar todos los datos de acuerdo a como lo haya puesto
        mostrarDatos(personaSeleccionada);

    }


    private void mostrarDatos(PersonaSign personaSelect){

        idImageSexo.setImageResource(personaSelect.getIdImagenSexoColor());
        idTextNombre.setText(personaSelect.getNombre());
        idTextViewOrigen.setText(personaSelect.getOrigen());
        idTextViewSignificado.setText(personaSelect.getSignificado());
        if (personaSelect.isFavorito()){
            idImageFavOno.setImageResource(R.drawable.corazon_activo);
        }else{
            idImageFavOno.setImageResource(R.drawable.corazon_desactivado);
        }

        animacion();

    }

    private void animacion(){
        animation = AnimationUtils.loadAnimation(this,R.anim.zoom_in_on);
        idImageFavOno.startAnimation(animation);

    }

    private PersonaSign buscarPersona(String nombre){
        PersonaSign devolver=null;
        for (int i=0;i<personas.size();i++){
            if (nombre.equals(personas.get(i).getNombre())){
                devolver =personas.get(i);
            }
        }
        return devolver;
    }

    private ServiceConnection mConnection1 = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DATOS.LocalBinder binder = (DATOS.LocalBinder) service;
            mService1 = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

}
