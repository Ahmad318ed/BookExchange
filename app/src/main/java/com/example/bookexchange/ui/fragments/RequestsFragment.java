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
    public static RequestAdapter myadapter;
    public static List<Request> myList = new ArrayList<>();
    private boolean fabShouldBeHidden = false; // Flag to keep track of FAB visibility


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_requests, container, false);
        fabShouldBeHidden = false;

        myList.clear();

        Request Request1 = new Request("Data Structure", "Ysra Khaled", R.drawable.data_structure, "كلية تيكنلوجيا المعلومات", "1");
        Request Request2 = new Request(" علوم عسكرية ", "موسى العوضي", R.drawable.alom, "كلية الاداب", "0.5");
        Request Request3 = new Request("Algorithms", "Mazen Kamel", R.drawable.algorithms, "كلية تيكنلوجيا المعلومات", "Free");


        myList.add(Request1);
        myList.add(Request2);
        myList.add(Request3);
        myList.add(Request1);
        myList.add(Request2);


        recyclerView = view.findViewById(R.id.rv_request);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        myadapter = new RequestAdapter(requireContext(), myList);
        recyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !(lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)) {
                    HomeActivity.fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);

            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                // Get the last visible item position
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastVisibleItemPosition();

                // Check if the last visible item is the last item in the RecyclerView
                if (lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                    // Hide the FAB and set flag to true
                    HomeActivity.fab.hide();
                    fabShouldBeHidden = true;
                } else {
                    // Set flag to false and show the FAB
                    fabShouldBeHidden = false;
                    HomeActivity.fab.show();
                }


            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Show or hide the FAB based on the flag
        if (fabShouldBeHidden) {
            HomeActivity.fab.hide();
        } else {
            HomeActivity.fab.show();
        }
    }


}