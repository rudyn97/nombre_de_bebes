package com.example.valia.nombresdebebes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Valia on 27/08/2021.
 */

public class ElegirNombre extends AppCompatActivity {
    private ViewPager viewPager;
    private AdaptadorVistaSlider adaptadorVistaSlider;
    private LinearLayout idLinearDots;
    private Button butonNext, butonBack;
    private TextView idMostrar;

    ArrayList<String> contenido = new ArrayList<>();
    ArrayList<String> titulo = new ArrayList<>();
    ArrayList<Integer> imagen = new ArrayList<>();

    TextView [] dots;
    ArrayList<Integer> muestra=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elegir_nombre);

        idMostrar=(TextView)findViewById(R.id.idMostrar);
        viewPager=(ViewPager) findViewById(R.id.idViewPager);
        butonBack=(Button) findViewById(R.id.idBotonBack);
        butonNext=(Button) findViewById(R.id.idBotonNext);
        idLinearDots=(LinearLayout) findViewById(R.id.idLinearLayoutDots);
        iniciarArrays();

        addDots(0);
        cargarViewPager();
    }

    public void botonSiguiente(View view){
        int next=getItem(+1);
        if (next<titulo.size()){
            viewPager.setCurrentItem(next);
        }else{
            finish();
        }
    }
    public void botonAnterior(View view){
        if (viewPager.getCurrentItem()==titulo.size() || viewPager.getCurrentItem()==0){
            finish();
        }else{
            int back=getItem(-1);
            viewPager.setCurrentItem(back);
        }
    }

    public void botonMostrar(View view){
        switch (viewPager.getCurrentItem()){
            case 0:{
                Intent i= new Intent(this,Filtrar_Nombres.class);
                startActivity(i);
                //Toast.makeText(this, "Mostrar", Toast.LENGTH_SHORT).show();
                break;
            }
            case 1:{
                Intent i= new Intent(this,Filtrar_Nombres.class);
                startActivity(i);
                //Toast.makeText(this, "Mostrar1", Toast.LENGTH_SHORT).show();
                break;
            }
            case 2:{
                Toast.makeText(this, "Mostrar2", Toast.LENGTH_SHORT).show();
                break;
            }
            case 3:{
                Toast.makeText(this, "Mostrar3", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }



    private int getItem(int i){
        return viewPager.getCurrentItem()+i;
    }

    private void iniciarArrays(){
        //EL titulo
        titulo.add(""+getString(R.string.n1));
        titulo.add(""+getString(R.string.n2));
        titulo.add(""+getString(R.string.n3));
        titulo.add(""+getString(R.string.n4));
        titulo.add(""+getString(R.string.n5));
        titulo.add(""+getString(R.string.n6));
        titulo.add(""+getString(R.string.n7));
        titulo.add(""+getString(R.string.n8));
        titulo.add(""+getString(R.string.n9));
        titulo.add(""+getString(R.string.n10));
        titulo.add(""+getString(R.string.n11));
        titulo.add(""+getString(R.string.n12));
        titulo.add(""+getString(R.string.n13));
        titulo.add(""+getString(R.string.n14));
        titulo.add(""+getString(R.string.n15));


        //La imagen
        imagen.add(R.drawable.inspiracion);
        imagen.add(R.drawable.lista);
        imagen.add(R.drawable.modas);
        imagen.add(R.drawable.voz_alta);
        imagen.add(R.drawable.tradicion);
        imagen.add(R.drawable.nombres_raros);
        imagen.add(R.drawable.apellidos);
        imagen.add(R.drawable.iniciales);
        imagen.add(R.drawable.nombre_hermano);
        imagen.add(R.drawable.opiniones_demas);
        imagen.add(R.drawable.dificil_pronunciar);
        imagen.add(R.drawable.legislacion);
        imagen.add(R.drawable.repasar_agenda);
        imagen.add(R.drawable.apodos);
        imagen.add(R.drawable.no_apurarse);

        //El contenido
        contenido.add(""+getString(R.string.c1));
        contenido.add(""+getString(R.string.c2));
        contenido.add(""+getString(R.string.c3));
        contenido.add(""+getString(R.string.c4));
        contenido.add(""+getString(R.string.c5));
        contenido.add(""+getString(R.string.c6));
        contenido.add(""+getString(R.string.c7));
        contenido.add(""+getString(R.string.c8));
        contenido.add(""+getString(R.string.c9));
        contenido.add(""+getString(R.string.c10));
        contenido.add(""+getString(R.string.c11));
        contenido.add(""+getString(R.string.c12));
        contenido.add(""+getString(R.string.c13));
        contenido.add(""+getString(R.string.c14));
        contenido.add(""+getString(R.string.c15));

        //Aqui pones los que llevan enlace
        //muestra.add(0);
        //muestra.add(1);
        //muestra.add(2);

        //Hacer para la legislacion de los paises. Para proximas versiones
        //muestra.add(11);
    }

    private void addDots(int currentPage){
        dots=new TextView[titulo.size()];

        idLinearDots.removeAllViews();

        if (muestra.contains(currentPage)){
            idMostrar.setVisibility(View.VISIBLE);
        }else {
            idMostrar.setVisibility(View.INVISIBLE);
        }

        for (int i=0;i<dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.LTGRAY);
            if(i==currentPage){
                dots[i].setTextColor(Color.argb(255,120,92,204));
                dots[i].setText("•");
            }

            idLinearDots.addView(dots[i]);
        }
    }

    private void cargarViewPager(){
        adaptadorVistaSlider= new AdaptadorVistaSlider(getSupportFragmentManager());
        for (int i=0;i<titulo.size();i++){
            adaptadorVistaSlider.addFragment(newInstance(titulo.get(i),contenido.get(i),imagen.get(i)));
        }
        viewPager.setAdapter(adaptadorVistaSlider);
        viewPager.addOnPageChangeListener(pagerListener);
    }

    private SliderFragment newInstance(String title, String content, int image){
        Bundle bundle= new Bundle();
        bundle.putString("titulo",title);
        bundle.putString("contenido",content);
        bundle.putInt("imagen",image);

        SliderFragment fragment=new SliderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    ViewPager.OnPageChangeListener pagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {


            addDots(position);
            if (position==titulo.size()-1){
                butonNext.setText(""+getString(R.string.salir));
                butonBack.setText(""+getString(R.string.atras));
            }else{
                butonNext.setText(""+getString(R.string.siguiente));
                butonBack.setText(""+getString(R.string.atras));
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
