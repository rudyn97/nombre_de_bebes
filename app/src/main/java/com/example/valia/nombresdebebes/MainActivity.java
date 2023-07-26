package com.example.valia.nombresdebebes;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DATOS mService;
    ConstraintLayout act;
    ImageView idImagenIdioma;

    String idioma;
    private Locale locale;
    private Configuration config = new Configuration();

    Button button4;

    @Override
    protected void onStart() {
        Intent in = new Intent(this, DATOS.class);
        bindService(in, mConnection, Context.BIND_AUTO_CREATE);
        super.onStart();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act = (ConstraintLayout) findViewById(R.id.activity_main);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.aparece);
        act.startAnimation(animation1);

        idImagenIdioma = (ImageView)findViewById(R.id.idImagenIdioma);
        button4 = (Button)findViewById(R.id.button4);

        readFilesIdioma(this);

        if(idioma.equals("sin")){
            if (Locale.getDefault().toString().contains("es")){
                idioma="espannol";
            }else {
                if (Locale.getDefault().toString().contains("en")){
                    idioma="ingles";
                }
            }
            return;
        }

        if (idioma.equals("espannol")){
            idImagenIdioma.setImageDrawable(getDrawable(R.drawable.i2));
            ponerIdioma();
        }else{
            if (idioma.equals("ingles")){
                idImagenIdioma.setImageDrawable(getDrawable(R.drawable.i3));
                ponerIdioma();
            }
        }

        getSupportActionBar().setTitle(getString(R.string.app_name));

    }

    public void ejecutar_datos_nombres(View view){
        Intent i= new Intent(this,Datos_Nombres.class);
        startActivity(i);
    }

    public void ejecutar_crear_nombre(View view){
        Intent i= new Intent(this,Crear_Nombre.class);
        startActivity(i);
    }
    public void ejecutar_elegir_nombre(View view){
        Intent i= new Intent(this,ElegirNombre.class);
        startActivity(i);
    }
    public void ejecutar_filtrar_nombre(View view){
        Intent i= new Intent(this,Filtrar_Nombres.class);
        startActivity(i);
    }

    public void ejecutar_favoritos(View view){
        Intent i= new Intent(this,Favoritos.class);
        startActivity(i);
    }

    public void ejecutar_info(View view){
        Intent i= new Intent(this,Informacion.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id=menuItem.getItemId();

        switch (id){

            case R.id.favoritos:{
                    ejecutar_favoritos(null);

                break;
            }
            case R.id.cuentos:{
                //PA LA APK DE NOMBRE DE BEBES CUENTOS
                abrirCuentos();
                break;
            }
        }
        return true;
    }

    public void cambiarIdioma(View view){

        switch (idioma){
            case "espannol":{
                idioma="ingles";
                cambiarIdiomaAqui("en");
                break;
            }
            case "ingles":{
                idioma="espannol";
                cambiarIdiomaAqui("es");
                break;
            }
        }
    }

    private void cambiarIdiomaAqui(String siglas){

        locale = new Locale(siglas);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        Intent refresh = new Intent(MainActivity.this, MainActivity.class);
        startActivity(refresh);
        finish();

        saveFilesIdioma(this,idioma);
    }

    private void ponerIdioma(){

        /*************IDIOMA AQUI Y EN OPCIONES***************/

        String [] nombreIdioma={"espannol","ingles"};
        String [] nombreBoton={"Datos de Nombres","Name Data"};
        String [] siglasIdioma={"es","en"};

        for (int i=0;i<nombreIdioma.length;i++) {
            if (idioma.equals(nombreIdioma[i]) && !button4.getText().equals(nombreBoton[i])) {
                //cambiarIdiomaAqui("es");
                locale = new Locale(siglasIdioma[i]);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.locale = locale;
                getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
                Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                startActivity(refresh);
                finish();

                saveFilesIdioma(this, idioma);
            }
        }
    }

    private void abrirCuentos(){
        String appName = "com.goldennumber.rudyn.nombresdebebescuentos";

        boolean isAppIntalled = available(appName);

        if (isAppIntalled){
            Intent intent = getPackageManager().getLaunchIntentForPackage(appName);
            if (intent!=null){
                startActivity(intent);
                finish();
            }
        }else {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
            bottomSheetDialog.show(getSupportFragmentManager(), "INFORMACION");
        }
    }

    //////////METODO SABER SI ESTA//////////////////
    private boolean available(String nombre){
        boolean available = true;
        try {
            getPackageManager().getPackageInfo(nombre,0);
        }catch (PackageManager.NameNotFoundException e){
            available = false;
        }
        return available;
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

    private void saveFilesIdioma(Context context,String idioma){
        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mieditor=datos.edit();
        mieditor.putString("Idioma", idioma);
        mieditor.apply();
    }



    private String readFilesIdioma(Context context){
        String idioma1="sin";
        SharedPreferences datos=PreferenceManager.getDefaultSharedPreferences(context);
        try{
            idioma1=datos.getString("Idioma","sin");
        }catch (Exception e){
            e.printStackTrace();
        }

        idioma=idioma1;
        return idioma1;
    }


    private void saveFile() {
        try{
            File file = new File(getCacheDir(),"IdiomaNombreDeBebes");
            FileOutputStream fos = new FileOutputStream(file);
            String g=""+idioma;
            fos.write(g.getBytes());
            fos.close();
        }catch (java.io.IOException e){
            e.printStackTrace();
        }
        onBackPressed();
    }

}
