package com.example.valia.nombresdebebes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Valia on 23/08/2021.
 */

public class PantallaInicio extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.pantalla_inicio);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /*ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(PantallaInicio.this, null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(new Intent(PantallaInicio.this, MainActivity.class), compat.toBundle());
                }*/

                Intent intent = new Intent(PantallaInicio.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },400);
    }

}
