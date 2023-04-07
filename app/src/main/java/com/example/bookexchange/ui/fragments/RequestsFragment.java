package com.example.bookexchange.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookexchange.R;
import com.example.bookexchange.adapters.RequestAdapter;
import com.example.bookexchange.models.Request;

import java.util.ArrayList;
import java.util.List;


public class RequestsFragment extends Fragment {

    RecyclerView recyclerView;
    RequestAdapter myadapter;
    List<Request> myList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_requests, container, false);

        Request Request = new Request("Rich dad poor dad", "1997",R.drawable.rich,"dd","dd","dd");
        Request Request2 = new Request(" علوم عسكرية ", "2015",R.drawable.alom,"dd","dd","dd");
        Request Request3 = new Request("ENERGY MANUAL STUDENT PRICE", "2022",R.drawable.en,"dd","dd","");
        Request Request4 = new Request("12 Rules for Life An Antidote to Chaos", "2023",R.drawable.rul,"","","");
        Request Request5 = new Request("The Every day Hero Manifesto", "2005",R.drawable.the,"","","");


        myList.add(Request4);
        myList.add(Request5);
        myList.add(Request2);
        myList.add(Request3);
        myList.add(Request);

        recyclerView = view.findViewById(R.id.rv_request);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        myadapter = new RequestAdapter(requireContext(), myList);
        recyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();


        return view;
    }
}