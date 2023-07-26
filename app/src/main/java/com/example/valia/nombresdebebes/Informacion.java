package com.example.valia.nombresdebebes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Valia on 04/09/2021.
 */

public class Informacion extends AppCompatActivity{

    TextView idTextViewVersion;
    LinearLayout idLinearInfo1;
    LinearLayout idLinearInfo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion);


        idLinearInfo1=(LinearLayout)findViewById(R.id.idLinearInfo1);
        idLinearInfo2=(LinearLayout)findViewById(R.id.idLinearInfo2);
        idTextViewVersion=(TextView)findViewById(R.id.idTextViewVersion);
        idTextViewVersion.setText(getResources().getString(R.string.version)+" "+BuildConfig.VERSION_NAME);

        clickLargo();
        clickLargo1();

        getSupportActionBar().setTitle(getString(R.string.informacion));

    }

    private void clickLargo(){

        final boolean[] largo = {false};

        idLinearInfo2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", "rudyn@nauta.cu");
                clipboardManager.setPrimaryClip(clip);

                largo[0] =true;
                return true;
            }
        });

        if (!largo[0]){
            idLinearInfo2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ejecutar_enviar_correo();

                }
            });
        }

    }

    /*****************************CORREO****************************/
    public void ejecutar_enviar_correo(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:rudyn@nauta.cu"));
        intent.putExtra(Intent.EXTRA_EMAIL, "rudyn@nauta.cu");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));

        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

    }
    /***********************************************************/

    /******************************ENVIAR APKLIS APK SINO PAGINA********************************************/
    /******************************LONG PRESS ENVIA PAGINA********************************************/
    private void clickLargo1(){

        final boolean[] largo = {false};

        idLinearInfo1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //DEJA APRETADO MANDA PA NAVEGADOR

                String url = "https://www.apklis.cu/application/com.example.valia.nombresdebebes";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

                largo[0] =true;
                return true;
            }
        });

    }


    //ESTO VA EN EL ONCLICK DEL BOTON
    public void ejecutar_enviar_pagina(View view){

        String appName = "cu.uci.android.apklis";

        boolean isAppIntalled = appInstalledOrNot(appName);

        if (isAppIntalled){
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Intent.ACTION_SEND));
            i.setData(Uri.parse("https://www.apklis.cu/application/com.example.valia.nombresdebebes"));
            i.setPackage(appName);
            startActivity(i);
        }else{
            //No tiene apklis mandarlo pal navegador
            String url = "https://www.apklis.cu/application/com.example.valia.nombresdebebes";
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
    }

    private boolean appInstalledOrNot(String uri){
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            return true;
        }catch (PackageManager.NameNotFoundException e){

        }return false;

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.info_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id=menuItem.getItemId();

        switch (id){
            case R.id.otras:{
                Intent i = new Intent(this, OtrasApks.class);
                startActivity(i);
                break;
            }
            case android.R.id.home:{
                onSupportNavigateUp();
                break;
            }
        }
        return true;
    }

}
