package com.example.sergio_pieza.aplicaciontp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.sql.Momento;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MomentoDetalle.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MomentoDetalle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MomentoDetalle extends Fragment {

Momento  momento;



    public MomentoDetalle() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MomentoDetalle newInstance(Momento momento) {
        MomentoDetalle fragment = new MomentoDetalle();
        Bundle args = new Bundle();
        args.putSerializable("momento",momento);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        momento=(Momento)getArguments().getSerializable("momento");
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista =inflater.inflate(R.layout.fragment_momento_detalle, container, false);
        TextView usuario =(TextView)vista.findViewById(R.id.usuarioMomentoDetalle);
        TextView descripcion =(TextView)vista.findViewById(R.id.descripcionMomentoDetalle);
        ImageView imagen =(ImageView)vista.findViewById(R.id.imagenMomentoDetalle);

        usuario.setText(String.valueOf(momento.getUsuario_Id()));
        descripcion.setText(momento.getDescripcion());
        Glide.with(getActivity()).load(momento.getImagen()).placeholder(R.drawable.imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter()
                .override(500,500).into(imagen);
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
