package com.example.sergio_pieza.aplicaciontp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.sergio_pieza.aplicaciontp.adapter.MomentoAdapter;
import com.example.sergio_pieza.aplicaciontp.adapter.UsuarioAdapter;
import com.example.sergio_pieza.aplicaciontp.fragment.dummy.DummyContent;
import com.example.sergio_pieza.aplicaciontp.fragment.dummy.DummyContent.DummyItem;
import com.example.sergio_pieza.aplicaciontp.helper.Api;
import com.example.sergio_pieza.aplicaciontp.helper.SesionHelper;
import com.example.sergio_pieza.aplicaciontp.helper.SharedPrefHelper;
import com.example.sergio_pieza.aplicaciontp.sql.ContactoDao;
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
public class ListaContactoF extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    Usuario uActual = SharedPrefHelper.getInstance(getActivity()).getUser();

    ArrayList<Usuario> cu=new ArrayList<Usuario>();
    RecyclerView rv;
    UsuarioAdapter mAdapter;
    Context mContext;
    public ListaContactoF() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacto_list, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.listaContactos);
        rv.setHasFixedSize(true);
        int idU = uActual.getId();
        mContext=this.getContext();
        ContactoDao cDao=new ContactoDao(mContext);
        cu=cDao.contactosUsuario(idU);
        setHasOptionsMenu(true);
        mAdapter = new UsuarioAdapter(mContext, cu);
        rv.setAdapter(mAdapter);
        // Set the adapter
        LinearLayoutManager llm = new LinearLayoutManager((getActivity()));
        rv.setLayoutManager(llm);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.clear();
        inflater.inflate(R.menu.contactos, menu);
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int boton =item.getItemId();
        if (boton ==R.id.salir){
            SesionHelper.cerrarSesion(mContext);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
