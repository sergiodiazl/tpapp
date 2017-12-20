package com.example.sergio_pieza.aplicaciontp.activity;

/**
 * Created by sergio-pieza on 06/11/2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.adapter.MyPagerAdapter;
import com.example.sergio_pieza.aplicaciontp.fragment.HomeF;
import com.example.sergio_pieza.aplicaciontp.fragment.ListaMisMomentosF;
import com.example.sergio_pieza.aplicaciontp.fragment.ListaMomentoF;
import com.example.sergio_pieza.aplicaciontp.helper.RecyclerViewClickListener;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;
import com.example.sergio_pieza.aplicaciontp.sql.Momento;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements ListaMomentoF.OnMomentoSelectedListener ,ListaMisMomentosF.OnMomentoSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SharedPrefHelper.getInstance(this).cargarLocale();
        setContentView(R.layout.activity_home);
        //terminar la otra actividad

        MyPagerAdapter myPagerAdapter =
                new MyPagerAdapter(
                        getSupportFragmentManager(),this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);
        //asignar los iconos a las pestañas
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
         int[] iconos = {
                R.drawable.home,
                R.drawable.contactos,
                 R.drawable.imagen,
                R.drawable.perfil
        };
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(iconos[i]);
        }//aca termina la asigancion d iconos

    }





    @Override
    public void onItemSelected(Momento m) {
          Intent iDetalle=new Intent(this,MomentoDetalleActivity.class);
         iDetalle.putExtra("momento",m);
         startActivity(iDetalle);

    }
}

