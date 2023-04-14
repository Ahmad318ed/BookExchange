package com.example.bookexchange.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookexchange.R;
import com.example.bookexchange.adapters.RequestAdapter;
import com.example.bookexchange.models.Request;
import com.example.bookexchange.ui.activites.HomeActivity;

import java.util.ArrayList;
import java.util.List;


public class RequestsFragment extends Fragment {

    RecyclerView recyclerView;
    RequestAdapter myadapter;
    List<Request> myList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_requests, container, false);

        Request Request1 = new Request("Data Structure", "Ysra Khaled",R.drawable.data_structure,"كلية تيكنلوجيا المعلومات","1","3");
        Request Request2 = new Request(" علوم عسكرية ", "موسى العوضي",R.drawable.alom,"كلية الاداب","0.5","5");
        Request Request3 = new Request("Algorithms", "Mazen Kamel",R.drawable.algorithms,"كلية تيكنلوجيا المعلومات","Free","12");


        myList.add(Request1);
        myList.add(Request2);
        myList.add(Request3);

        recyclerView = view.findViewById(R.id.rv_request);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        myadapter = new RequestAdapter(requireContext(), myList);
        recyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    HomeActivity.fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 1 || dy < 1 && HomeActivity.fab.isShown()) {
                    HomeActivity.fab.hide();
                }


            }
        });

        return view;
    }
}