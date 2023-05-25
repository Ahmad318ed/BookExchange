package com.example.bookexchange.ui.fragments;

import android.content.Intent;
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
import com.example.bookexchange.adapters.SelectPostItemListener;
import com.example.bookexchange.adapters.SelectRequestItemListener;
import com.example.bookexchange.dao.DAONotification;
import com.example.bookexchange.dao.DAOPost;
import com.example.bookexchange.dao.DAORequest;
import com.example.bookexchange.models.Post;
import com.example.bookexchange.models.Request;
import com.example.bookexchange.ui.activites.HomeActivity;
import com.example.bookexchange.ui.activites.ViewPostActivity;
import com.example.bookexchange.ui.activites.ViewRequestActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RequestsFragment extends Fragment implements SelectRequestItemListener, Serializable {

    RecyclerView recyclerView;
    public static RequestAdapter myadapter;
    FirebaseAuth auth;
    FirebaseUser user;
    String currentUserID;
    DAORequest daoRequest;
    DAONotification daoNotification;
    DatabaseReference databaseReference;
    public static List<Request> requestList = new ArrayList<>();
    private boolean fabShouldBeHidden = false; // Flag to keep track of FAB visibility


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_requests, container, false);
        fabShouldBeHidden = false;



        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        daoRequest = new DAORequest();
        daoNotification = new DAONotification();
        requestList.clear();


        recyclerView = view.findViewById(R.id.rv_request);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        currentUserID = user.getUid();

        daoRequest.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postsnap : snapshot.getChildren()) {
                    String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                    Request request = postsnap.getValue(Request.class);



                    if (!(request.getBookSellerId().equals(currentUserID))) {



                        requestList.add(request);



                    }



                }
                myadapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        myadapter = new RequestAdapter(requireContext(), requestList, (SelectRequestItemListener) this);
        recyclerView.setAdapter(myadapter);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
//                        .findLastVisibleItemPosition();
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && !(lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)) {
//                    HomeActivity.fab.show();
//                }
//                super.onScrollStateChanged(recyclerView, newState);
//
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                // Get the last visible item position
//                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
//                        .findLastVisibleItemPosition();
//
//                // Check if the last visible item is the last item in the RecyclerView
//                if (lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
//                    // Hide the FAB and set flag to true
//                    HomeActivity.fab.hide();
//                    fabShouldBeHidden = true;
//                } else {
//                    // Set flag to false and show the FAB
//                    fabShouldBeHidden = false;
//                    HomeActivity.fab.show();
//                }
//
//
//            }
//        });


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


    @Override
    public void onItemViewClicked(Request request) {
        Intent intent = new Intent(getContext(), ViewRequestActivity.class);
        intent.putExtra("request", request);
        startActivity(intent);
    }

    @Override
    public void onItemGiveClicked(Request request, FirebaseUser currentUser) {

    }

    @Override
    public void onItemDeleteClicked(Request request, FirebaseUser currentUser) {

    }
}