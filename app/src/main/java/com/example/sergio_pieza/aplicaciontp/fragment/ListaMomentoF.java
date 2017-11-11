package com.example.sergio_pieza.aplicaciontp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.sergio_pieza.aplicaciontp.R;
import com.example.sergio_pieza.aplicaciontp.adapter.MomentoAdapter;
import com.example.sergio_pieza.aplicaciontp.fragment.dummy.DummyContent;
import com.example.sergio_pieza.aplicaciontp.fragment.dummy.DummyContent.DummyItem;
import com.example.sergio_pieza.aplicaciontp.sql.Momento;

import java.util.ArrayList;
import java.util.List;

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
        RecyclerView rv=(RecyclerView)view.findViewById(R.id.listaMomentos);
        rv.setHasFixedSize(true);
        List<Momento> momentos=Momento.ejemplo(10);
        MomentoAdapter mAdapter = new MomentoAdapter(this.getContext(),momentos);
        rv.setAdapter(mAdapter);
        // Set the adapter
        LinearLayoutManager llm=new LinearLayoutManager((getActivity()));
        rv.setLayoutManager(llm);
        return view;
    }






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

}
