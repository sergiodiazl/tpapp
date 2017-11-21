package com.example.sergio_pieza.aplicaciontp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MomentoDetalle.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MomentoDetalle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MomentoDetalle extends Fragment implements OnMapReadyCallback{

Momento  momento;
GoogleMap gmap;
MapView mapa;


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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista =inflater.inflate(R.layout.fragment_momento_detalle, container, false);
        TextView usuario =(TextView)vista.findViewById(R.id.usuarioMomentoDetalle);
        TextView descripcion =(TextView)vista.findViewById(R.id.descripcionMomentoDetalle);
        ImageView imagen =(ImageView)vista.findViewById(R.id.imagenMomentoDetalle);
        TextView latitud =(TextView)vista.findViewById(R.id.latitudMomentoDetalle);
        TextView longitud =(TextView)vista.findViewById(R.id.longitudMomentoDetalle);
        String latString=String.valueOf(momento.getLatitud());
        String lonString=String.valueOf(momento.getLongitud());
        String uString=String.valueOf(momento.getUsuario_Id());
        usuario.setText(uString);
        latitud.setText(latString);
        longitud.setText(lonString);
        descripcion.setText(momento.getDescripcion());
        Glide.with(getActivity()).load(momento.getImagen()).placeholder(R.drawable.imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter()
                .override(500,500).into(imagen);
        return vista;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapa=(MapView)view.findViewById(R.id.mapaMomentoDetalle);
        if(mapa !=null){
            mapa.onCreate(null);
            mapa.onResume();
            mapa.getMapAsync(this);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        LatLng lugar=new LatLng(momento.getLatitud(),momento.getLongitud());
        gmap=googleMap;
        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(
                new MarkerOptions().position(lugar)
        );
        CameraPosition camara=CameraPosition.builder().target(lugar).zoom(16).bearing(0).tilt(20).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camara));

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
