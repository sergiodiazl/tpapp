package com.example.sergio_pieza.aplicaciontp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.fragment.ContactosF;
import com.example.sergio_pieza.aplicaciontp.fragment.HomeF;
import com.example.sergio_pieza.aplicaciontp.fragment.ListaMisMomentosF;
import com.example.sergio_pieza.aplicaciontp.fragment.PerfilF;

/**
 * Created by sergio-pieza on 06/11/2017.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
private Context mContext;
    public MyPagerAdapter(FragmentManager fm ,Context context) {
        super(fm);
        mContext=context;
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
                fragment = new ListaMisMomentosF();

                break;
            case 3:
                fragment = new PerfilF();
                break;
            default:
                fragment = new HomeF();//cambiar a homef()
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String titulo;
        switch (position) {
            case 0:

                titulo =mContext.getResources().getString(R.string.home);
                return titulo;
            case 1:
                titulo =mContext.getResources().getString(R.string.contactos);
                return titulo;
            case 02:
                titulo =mContext.getResources().getString(R.string.miPerfil);
                return titulo;
            case 03:
                titulo =mContext.getResources().getString(R.string.perfil);
                return titulo;
        }
        return null;
    }


}
