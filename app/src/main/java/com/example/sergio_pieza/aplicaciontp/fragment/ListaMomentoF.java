package com.example.sergio_pieza.aplicaciontp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.sergio_pieza.aplicaciontp.adapter.MomentoAdapter;
import com.example.sergio_pieza.aplicaciontp.fragment.dummy.DummyContent;
import com.example.sergio_pieza.aplicaciontp.fragment.dummy.DummyContent.DummyItem;
import com.example.sergio_pieza.aplicaciontp.helper.Api;
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

    public ListaMomentoF() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_momento_list, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.listaMomentos);
        rv.setHasFixedSize(true);
        mAdapter = new MomentoAdapter(this.getContext(), momentos);
        rv.setAdapter(mAdapter);
        // Set the adapter
        LinearLayoutManager llm = new LinearLayoutManager((getActivity()));
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

}
