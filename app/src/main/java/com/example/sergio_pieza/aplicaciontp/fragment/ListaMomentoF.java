package com.example.sergio_pieza.aplicaciontp.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.Volley.VolleySingleton;
import com.example.sergio_pieza.aplicaciontp.activity.MomentoDetalleActivity;
import com.example.sergio_pieza.aplicaciontp.adapter.MomentoAdapter;
import com.example.sergio_pieza.aplicaciontp.fragment.dummy.DummyContent;
import com.example.sergio_pieza.aplicaciontp.fragment.dummy.DummyContent.DummyItem;
import com.example.sergio_pieza.aplicaciontp.helper.Api;
import com.example.sergio_pieza.aplicaciontp.helper.RecyclerViewClickListener;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;
import com.example.sergio_pieza.aplicaciontp.sql.Momento;
import com.example.sergio_pieza.aplicaciontp.sql.MomentoDao;
import com.example.sergio_pieza.aplicaciontp.sql.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link //OnListFragmentInteractionListener}
 * interface.
 */
public class ListaMomentoF extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    Usuario uActual = SharedPrefHelper.getInstance(getActivity()).getUser();
    ArrayList<Momento> momentos=new ArrayList<Momento>();
    RecyclerView rv;
    MomentoAdapter mAdapter;
    private RecyclerViewClickListener listenerRecycler;
    private OnMomentoSelectedListener listenerSelect;
    private final int permisoEscribir =2;
    public ListaMomentoF() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//indica q tiene menu

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE},permisoEscribir);//Method of Fragment

        } else {
            Log.e("DB", "PERMISSION GRANTED");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);//indica q tiene menu
        View view = inflater.inflate(R.layout.fragment_momento_list, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.listaMomentos);
        rv.setHasFixedSize(true);
         listenerRecycler = (vistaListener, position) -> {
            //Toast.makeText(getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
            Momento momentoElegido =momentos.get(position);
           listenerSelect.onItemSelected(momentoElegido);
        };
        mAdapter = new MomentoAdapter(this.getActivity(), momentos,listenerRecycler);
        rv.setAdapter(mAdapter);
        // Set the adapter
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        MomentoDao mDao = new MomentoDao(getActivity());
        int idU = uActual.getId();

        StringRequest postRequest = new StringRequest(Request.Method.POST, Api.URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            //convierta json a la respuesta
                            JSONObject obj = new JSONObject(response);
                            //si hay error muestra el mensaje
                            if (obj.getBoolean("error") == true) {
                                String mensaje = obj.getString("mensaje");
                                Log.d("mensaje", mensaje);

                            } else {
                                String mensaje = obj.getString("mensaje");
                                Log.d("mensaje", mensaje);
                                JSONArray jsonMomentos = (JSONArray)(obj.getJSONArray("momentos"));

                                for (int i =  jsonMomentos.length()-1; 0 <=i; i--) {
                                    JSONObject m = jsonMomentos.getJSONObject(i);
                                    int p1 = m.getInt("id_m");
                                    String p2 = m.getString("imagen");
                                    String p3 = m.getString("fecha");
                                    String p4 = m.getString("descripcion");
                                    Double p5 = m.getDouble("latitud");
                                    Double p6 = m.getDouble("longitud");
                                    int p7 = m.getInt("zona_id");
                                    int p8 = m.getInt("usuario_id");
                                    Momento nuevoMomento = new Momento(p1,p2,p3,p4,p5,p6,p7,p8);
                                    momentos.add(nuevoMomento);
                                    mDao.insertar(nuevoMomento);
                                }

                                mAdapter.notifyDataSetChanged();
                                Log.d("aca tendria que notificar","el adapdter");

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        Log.d("Response", response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String id = String.valueOf(uActual.getId());
                params.put("funcion", "momentosUsuario");
                params.put("usuario_id", id);

                return params;
            }


        };


        /**
         * This interface must be implemented by activities that contain this
         * fragment to allow an interaction in this fragment to be communicated
         * to the activity and potentially other fragments contained in that
         * activity.
         * <p/>
         * See the Android Training lesson <a href=
         * "http://developer.android.com/training/basics/fragments/communicating.html"
         * >Communicating with Other Fragments</a> for more information.
         */
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(postRequest);


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.clear();
        inflater.inflate(R.menu.home, menu);
        MenuItem buscarAction=menu.findItem(R.id.buscar);
        SearchView vistaBuscar=(SearchView) MenuItemCompat.getActionView(buscarAction);
        buscar(vistaBuscar);
        return;
    }

    private void buscar(SearchView vistaBuscar) {

        vistaBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int boton =item.getItemId();
        if (boton ==R.id.salir){
            cerrarSesion();
            return true;
        }
        if(boton==R.id.buscar){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cerrarSesion(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Queres cerrar sesión");
        alertDialogBuilder.setPositiveButton("si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        SharedPrefHelper.getInstance(getActivity()).logout();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public interface OnMomentoSelectedListener {
        public void onItemSelected(Momento m);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if (activity instanceof OnMomentoSelectedListener) {
            listenerSelect= (OnMomentoSelectedListener) activity;
        } else {
            throw new ClassCastException(
                    activity.toString()
                            + " hay que implementar onMomentoSelectedlistener");
        }
    }
}
