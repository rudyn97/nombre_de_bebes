package com.example.valia.nombresdebebes;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Valia on 23/12/2019.
 */

public class Filtrar_Nombres extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtrar_nombres);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        getSupportActionBar().setTitle(getString(R.string.filtrar_nombres));

    }


    //AQUI PARA EL SCROLL
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Toast.makeText(Filtrar_Nombres.this, ""+position, Toast.LENGTH_SHORT).show();
            if (position==0){
                return Fragment1.newInstance(position + 1);
            }else{
                if (position==1){
                    return Fragment2.newInstance(position + 2);
                }else{
                    return Fragment3.newInstance(position);
                }
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //Toast.makeText(Filtrar_Nombres.this, "Hey"+position, Toast.LENGTH_SHORT).show();
            switch (position) {
                case 0:
                    return ""+getString(R.string.todo);
                case 1:
                    //Toast.makeText(Filtrar_Nombres.this, "Hola"+position, Toast.LENGTH_SHORT).show();
                    return ""+getString(R.string.paises);
                case 2:
                    //Toast.makeText(Filtrar_Nombres.this, "Hola"+position, Toast.LENGTH_SHORT).show();
                    return ""+getString(R.string.origen);
            }
            return null;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}

