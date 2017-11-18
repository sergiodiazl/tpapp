package com.example.sergio_pieza.aplicaciontp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sergio_pieza.aplicaciontp.fragment.ContactosF;
import com.example.sergio_pieza.aplicaciontp.fragment.HomeF;
import com.example.sergio_pieza.aplicaciontp.fragment.ListaMomentoF;
import com.example.sergio_pieza.aplicaciontp.fragment.PerfilF;

/**
 * Created by sergio-pieza on 06/11/2017.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        switch(i) {
            case 0:
                fragment = new HomeF();//cambiar a homef()
                break;
            case 1:
                fragment = new ContactosF();
                break;
            case 2:
                fragment = new PerfilF();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Contacto";
            case 02:
                return "Perfil";
        }
        return null;
    }


}
