package com.example.sergio_pieza.aplicaciontp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sergio_pieza.aplicaciontp.R;

/**
 * Created by sergio-pieza on 06/11/2017.
 */

public class HomeF extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_f, container, false);
    }
}
