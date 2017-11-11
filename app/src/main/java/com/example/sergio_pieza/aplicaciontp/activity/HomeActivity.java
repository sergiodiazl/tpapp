package com.example.sergio_pieza.aplicaciontp.activity;

/**
 * Created by sergio-pieza on 06/11/2017.
 */
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.adapter.MyPagerAdapter;

import android.support.v4.app.FragmentTransaction;
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MyPagerAdapter myPagerAdapter =
                new MyPagerAdapter(
                        getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);
        //asignar los iconos a las pestañas
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
         int[] iconos = {
                R.drawable.home,
                R.drawable.contactos,
                R.drawable.perfil
        };
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(iconos[i]);
        }//aca termina la asigancion d iconos

    }
}

