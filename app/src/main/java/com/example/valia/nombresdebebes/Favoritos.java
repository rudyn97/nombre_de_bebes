package com.example.valia.nombresdebebes;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Valia on 17/12/2019.
 */

public class Favoritos extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    RecyclerView recyclerView;
    AdaptadorFavoritos adaptadorFavoritos;
    ArrayList<Persona> listaP;
    DATOS mService;
    LinearLayout idLinearNoHay;

    boolean ejecutado = false;

    ImageView idImagenBebe;
    AnimationDrawable fondoAnimacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritos);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerFav);
        listaP = new ArrayList<>();
        idLinearNoHay = (LinearLayout)findViewById(R.id.idLinearNoHay);

        Intent i = new Intent(this, DATOS.class);
        this.bindService(i, mConnection, Context.BIND_AUTO_CREATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!ejecutado){
                    cargar();
                    ejecutado=true;

                }
            }
        },100);

        idImagenBebe = (ImageView) findViewById(R.id.idImagenBebe);
        idImagenBebe.setBackgroundResource(R.drawable.animacion_bebe);
        fondoAnimacion = (AnimationDrawable) idImagenBebe.getBackground();

        getSupportActionBar().setTitle(getString(R.string.favoritos));

        //DbD.cargarFavoritos(this);
        //listaP=DbD.getPersonas();

        //guardarLista(DbD.cargarFavoritos(this));//)getFavPersonas());
    }

    @Override
    protected void onStart() {
        super.onStart();
        fondoAnimacion.start();
    }

    //Adaptar las personas sign a personas, para mostrarlos en favorito
    private ArrayList<Persona> adaptarSignFav(ArrayList<PersonaSign> personaSigns){
        ArrayList<Persona> retornar = new ArrayList<>();

        for (int i=0;i<personaSigns.size();i++){
            Persona p=new Persona(personaSigns.get(i).getNombre(),ucFirst(personaSigns.get(i).getSexo()),"Vacio",personaSigns.get(i).isFavorito());
            retornar.add(p);
        }
        return retornar;
    }

    //Llevar la palabra masculino a Masculino, y asi con femenino
    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) return str;
        else return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void guardarLista(ArrayList<Persona> listaDfavoritos ,ArrayList<PersonaSign> listaDfavoritosSign){

        ArrayList<Persona> adaptado = new ArrayList<>();
        adaptado=adaptarSignFav(listaDfavoritosSign);

        //**ADAPTAR LOS FAV SIGN PARA FAV Y SE LE PASAN LOS DOS Y AQUI SE USA EL METODO DE ADAPTARLO Y LOS JUNTARIAS*****/
        //JUNTARLO EN LISTAP Y PASARSELO DONDE ESTE LISTADFAVORITOS
        listaP=listaDfavoritos;

        //Guardar en la lista de mostrar en favorito la de favorito sign ya adaptado a fav
        for (int i=0;i<adaptado.size();i++){
            listaP.add(adaptado.get(i));
        }

        if (!listaP.isEmpty()){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adaptadorFavoritos= new AdaptadorFavoritos(listaP);
            recyclerView.setAdapter(adaptadorFavoritos);

            //Este ultimo lo deberia poder quitar
            //poner selectLista(listaDfavoritos);
            selectLista(listaP);
        }else{
            mostrarNoHay();
        }
        
    }

    /*private ArrayList<Persona> getFavPersonas(){

        ArrayList<Persona> favPersonas=new ArrayList<>();
        for (int i=0;i<listaP.size();i++){
            if (listaP.get(i).isFavorito()){
                favPersonas.add(listaP.get(i));
            }
        }
        return favPersonas;
    }*/

    @Override
    public void onItemSelected(final AdapterView<?> adapterView, View view, int i, long l) {
        if (!ejecutado){
            cargar();
            ejecutado=true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void cargar(){

        //****CARGO TAMBIEN FAV SIGN Y SE LO PASO COMO SEGUNDO METODO A GUARDAR LIST******/
        mService.cargarFavoritos(this);
        mService.cargarFavoritosSign(this);
        guardarLista(mService.cargarFavoritos(this),mService.cargarFavoritosSign(this));

    }

    public void selectLista(final ArrayList<Persona> listaDpersonas){

        //EN LISTA DE PERSONAS ESTA FAVORITOS Y FAVORITOS SIGN

        mService.cargarFavoritos(Favoritos.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(Favoritos.this));
        adaptadorFavoritos= new AdaptadorFavoritos(listaDpersonas);
        adaptadorFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //SI ES FAVORITO PARA QUE LA LISTA SEPA QUE CAMBIO A FALSO
                    listaDpersonas.get(recyclerView.getChildAdapterPosition(view)).setFavorito(false);

                    //PARA QUE CAMBIE EN LA BASE DE DATOS
                    for (int i=0;i<mService.getPersonas().size();i++){
                        if (listaDpersonas.get(
                                recyclerView.getChildAdapterPosition(view)).getNombre()==mService.getPersonas().get(i).getNombre()){

                            mService.getPersonas().get(i).setFavorito(false);
                            break;
                        }
                    }
                    //***********************************
                    //AQUI HAGO LO QUE ARRIBA CON PERSONAS SIGN SOLO LO DE BASE DE DATOS
                    for (int i=0;i<mService.getPersonasSign().size();i++){
                        if (listaDpersonas.get(
                            recyclerView.getChildAdapterPosition(view)).getNombre()==mService.getPersonasSign().get(i).getNombre()){

                            mService.getPersonasSign().get(i).setFavorito(false);
                            break;
                        }
                    }

                    //*********************************************/

                    //******VER SI ESTE ANUNCIO PASA CREO Q NO****************/
                    //REVISAR QUE CUANDO LO BORRA SE QUEDA PRENDIO Y SE DESMARCA EL OTRO
                    //PONER UN TOAST CON EL QUE ESTA BORRANDO
                    listaDpersonas.remove(recyclerView.getChildAdapterPosition(view));

                    //******GUARDO TAMBIEN FAV SIGN**************************/
                    mService.guardarFavoritos(Favoritos.this);
                    mService.guardarFavoritosSign(Favoritos.this);
                    adaptadorFavoritos.notifyDataSetChanged();


                if (listaDpersonas.size()==0){
                    mostrarNoHay();
                }
            }


        });

        recyclerView.setAdapter(adaptadorFavoritos);

    }

    private void mostrarNoHay(){
        //AQUI PONERLE QUE HAYA UN PANEL POR DETRAS PARA SI NO HAY FAVORITOS SE MUESTRE
        //Toast.makeText(this, "No hay Favoritos", Toast.LENGTH_SHORT).show();
        recyclerView.setVisibility(View.INVISIBLE);
        idLinearNoHay.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        super.onSupportNavigateUp();
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DATOS.LocalBinder binder = (DATOS.LocalBinder) service;
            mService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };


}
