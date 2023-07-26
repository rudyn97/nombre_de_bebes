package com.example.valia.nombresdebebes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Valia on 27/08/2021.
 */

public class OtrasApks extends AppCompatActivity {
    private ViewPager viewPager;
    private AdaptadorVistaSliderMas adaptadorVistaSliderMas;
    private LinearLayout idLinearDots;
    private ImageView butonNext, butonBack;

    ArrayList<Integer> imagen = new ArrayList<>();
    ArrayList<String> direccApklis = new ArrayList<>();
    ArrayList<String> direccUptod = new ArrayList<>();

    TextView [] dots;
    ArrayList<Integer> muestra=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otras_apks);

        viewPager=(ViewPager) findViewById(R.id.idViewPager);
        butonBack=(ImageView) findViewById(R.id.idBotonBack);
        butonNext=(ImageView) findViewById(R.id.idBotonNext);
        idLinearDots=(LinearLayout) findViewById(R.id.idLinearLayoutDots);
        iniciarArrays();

        //addDots(0);
        cargarViewPager();
    }

    public void botonSiguiente(View view){
        int next=getItem(+1);
        if (next<imagen.size()){
            viewPager.setCurrentItem(next);
        }else{
            //finish();
        }
    }
    public void botonAnterior(View view){
        if (viewPager.getCurrentItem()==imagen.size() || viewPager.getCurrentItem()==0){
            //finish();
        }else{
            int back=getItem(-1);
            viewPager.setCurrentItem(back);
        }
    }


    private int getItem(int i){
        return viewPager.getCurrentItem()+i;
    }

    private void iniciarArrays(){
        //AQUI AL AGREGAR COSAS SE AGREGAN EN EN EL VISUAL

        //La imagen
        imagen.add(R.drawable.domino);
        imagen.add(R.drawable.onlycontactwhatsapp);

        direccApklis.add("https://www.apklis.cu/application/com.example.rudyn.anotar_domino");
        direccApklis.add("https://www.apklis.cu/application/com.example.valia.nowhatsapp");

        direccUptod.add("https://anotar-domino.uptodown.com/android");
        direccUptod.add("");

    }

    /*private void addDots(int currentPage){
        dots=new TextView[imagen.size()];

        idLinearDots.removeAllViews();


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
    }*/

    private void cargarViewPager(){
        adaptadorVistaSliderMas = new AdaptadorVistaSliderMas(getSupportFragmentManager());
        for (int i=0;i<imagen.size();i++){
            adaptadorVistaSliderMas.addFragment(newInstance(imagen.get(i),direccApklis.get(i),direccUptod.get(i)));
        }
        viewPager.setAdapter(adaptadorVistaSliderMas);
        viewPager.addOnPageChangeListener(pagerListener);
    }

    private SliderFragmentMas newInstance(int image, String direccionApklis, String direccionUptodown){
        Bundle bundle= new Bundle();
        bundle.putInt("imagen",image);
        bundle.putString("apklis",direccionApklis);
        bundle.putString("uptodown",direccionUptodown);

        SliderFragmentMas fragment=new SliderFragmentMas();
        fragment.setArguments(bundle);
        return fragment;
    }

    ViewPager.OnPageChangeListener pagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {


            //addDots(position);
            if (position==imagen.size()-1){
                butonNext.setVisibility(View.INVISIBLE);
                butonBack.setVisibility(View.VISIBLE);
            }else{
                if (position==0){
                    butonBack.setVisibility(View.INVISIBLE);
                }else{
                    butonBack.setVisibility(View.VISIBLE);
                }
                butonNext.setVisibility(View.VISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
