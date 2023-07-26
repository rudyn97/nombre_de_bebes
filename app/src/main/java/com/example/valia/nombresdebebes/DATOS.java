package com.example.valia.nombresdebebes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Valia on 22/08/2021.
 */

public class DATOS extends Service {

    ArrayList<Persona> personas = new ArrayList<>();

    ArrayList<PersonaSign> personas_sign = new ArrayList<>();

    private IBinder mBinder = new LocalBinder();
    SQLiteHelper DB;
    SQLiteHelperSignificado DBSi;

    public DATOS() {

    }

    public DATOS(Context context) {
        ListaPersonas(context);
        ListaPersonasSign(context);
    }

    //AQUI SE VA COGIENDO DE LA BASE DE DATOS Y SE VA AÑADIENDO
    public void ListaPersonas(Context context){

        if (!personas.isEmpty()){
            return;
        }

        ArrayList<Persona> pers;

        //PRIMERO CARGO LAS PERSONAS Y LUEGO LOS FAVORITOS PA Q ACTUALICE LA LISTA

        //Toast.makeText(this, "Carga BD", Toast.LENGTH_SHORT).show();
        DB =new SQLiteHelper(context);
        DB.open();
        pers = DB.getPersonas();
        DB.close();

        setPersonas(pers);
    }

    //AQUI SE VA COGIENDO DE LA BASE DE DATOS Y SE VA AÑADIENDO
    public void ListaPersonasSign(Context context){

        if (!personas_sign.isEmpty()){
            return;
        }

        ArrayList<PersonaSign> pers_sign;

        //PRIMERO CARGO LAS PERSONAS Y LUEGO LOS FAVORITOS PA Q ACTUALICE LA LISTA

        //Toast.makeText(this, "Carga BD", Toast.LENGTH_SHORT).show();
        DBSi =new SQLiteHelperSignificado(context);
        DBSi.open();
        pers_sign = DBSi.getPersonas();
        DBSi.close();

        setPersonasSign(pers_sign);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //Toast.makeText(this, "Bind", Toast.LENGTH_SHORT).show();
        ListaPersonas(this);
        ListaPersonasSign(this);

        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //AQUI CARGO DE LA BASE DE DATOS SI personas ESTA VACIO

        //Toast.makeText(this, "Start Command", Toast.LENGTH_SHORT).show();
        ListaPersonas(this);
        ListaPersonasSign(this);
        /*Persona p1=new Persona("aaa","Masculino","Uruguay",false);
        Persona p2=new Persona("bbb","Masculino","Italia",false);

        personas.add(p1);
        personas.add(p2);*/

        return START_STICKY;
    }


    public class LocalBinder extends Binder {

        DATOS getService() {
            // Return this instance of LocalService so clients can call public methods
            return DATOS.this;

        }
    }

    /*public void onStart(ArrayList<Persona> personas) {
        this.personas= personas;
    }*/

    public ArrayList<Persona> getPersonas() {
        return personas;
    }
    public ArrayList<PersonaSign> getPersonasSign() {
        return personas_sign;
    }


    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }
    public void setPersonasSign(ArrayList<PersonaSign> personas) {
        this.personas_sign = personas;
    }


    public void guardarFavoritos(Context context){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor= sharedPref.edit();
        int tamaño=0;
        int posiciones=0;

        for (int i=0;i<personas.size();i++){
            if (personas.get(i).isFavorito()){
                posiciones=i;
                editor.putInt("pos"+tamaño,posiciones);
                tamaño++;
            }
        }
        editor.putInt("tamaño",tamaño);
        editor.apply();
    }
    //CARGA LOS FAVORITOS LOS CAMBIA EN LA LISTA DE PERSONAS Y DESPUES LE PASA LA LISTA
    //DEVUELVE ARRAY LIST DE PERSONAS
    public ArrayList<Persona> cargarFavoritos(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        ArrayList<Persona> pe = new ArrayList<>();
        int tamaño=0;
        tamaño=sharedPref.getInt("tamaño",tamaño);

        int pos=0;

        for (int i=0;i<tamaño;i++){
            pos=sharedPref.getInt("pos"+i,pos);

            personas.get(pos).setFavorito(true);
            pe.add(personas.get(pos));

        }
        return pe;
    }

    public void guardarFavoritosSign(Context context){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor= sharedPref.edit();
        int tamaño=0;
        int posiciones=0;

        for (int i=0;i<personas_sign.size();i++){
            if (personas_sign.get(i).isFavorito()){
                posiciones=i;
                editor.putInt("poss"+tamaño,posiciones);
                tamaño++;
            }
        }
        editor.putInt("tamañoo",tamaño);
        editor.apply();
    }
    //CARGA LOS FAVORITOS LOS CAMBIA EN LA LISTA DE PERSONAS Y DESPUES LE PASA LA LISTA
    //DEVUELVE ARRAY LIST DE PERSONAS
    public ArrayList<PersonaSign> cargarFavoritosSign(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        ArrayList<PersonaSign> pe = new ArrayList<>();
        int tamaño=0;
        tamaño=sharedPref.getInt("tamañoo",tamaño);

        int pos=0;

        for (int i=0;i<tamaño;i++){
            pos=sharedPref.getInt("poss"+i,pos);

            personas_sign.get(pos).setFavorito(true);
            pe.add(personas_sign.get(pos));

        }
        return pe;
    }

}
