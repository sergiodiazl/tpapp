package com.example.sergio_pieza.aplicaciontp.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v4.app.FragmentManager;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.activity.LoginActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sergio-pieza on 06/11/2017.
 */

public class HomeF extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);//indica q tiene menu
        FloatingActionButton subir;
        View view= inflater.inflate(R.layout.home_f, container, false);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.home_placeholder, new ListaMomentoF());
        ft.commit();
            String imagenUrl="http://www.islabit.com/wp-content/uploads/2017/09/Android.png";

        subir=(FloatingActionButton)view.findViewById(R.id.botonASubirMomento);
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSubirMomento();
            }
        });
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.home, menu);
        return;
    }
    public void aSubirMomento(){

        startActivity(new Intent(this.getActivity(),LoginActivity.class));
        return;
    }
}

