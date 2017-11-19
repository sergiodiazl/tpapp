package com.example.sergio_pieza.aplicaciontp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.fragment.MomentoDetalle;
import com.example.sergio_pieza.aplicaciontp.sql.Momento;

public class MomentoDetalleActivity extends AppCompatActivity {
    MomentoDetalle momentoDetalle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momento_detalle);

        Momento momento =(Momento)getIntent().getSerializableExtra("momento");
        if(savedInstanceState==null){
            momentoDetalle =MomentoDetalle.newInstance(momento);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contenedor_momento_detalle,momentoDetalle);
            ft.commit();
        }
    }

}
