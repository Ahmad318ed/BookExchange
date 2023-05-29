package com.example.bookexchange.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bookexchange.R;
import com.example.bookexchange.adapters.MyRequestAdapter;
import com.example.bookexchange.adapters.SelectRequestItemListener;
import com.example.bookexchange.dao.DAORequest;
import com.example.bookexchange.models.Request;
import com.example.bookexchange.ui.activites.ViewRequestActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyRequestFragment extends Fragment implements SelectRequestItemListener{

    View view;
    DAORequest daoRequest;
    FirebaseAuth auth;
    FirebaseUser user;
    String currentUserID;
    public static List<Request> myRequestList = new ArrayList<>();
    DatabaseReference databaseReference;
    public static MyRequestAdapter myadapter;
    RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.from(getContext()).inflate(R.layout.fragment_my_request, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        daoRequest = new DAORequest();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        myRequestList.clear();
        recyclerView = view.findViewById(R.id.rv_post_myRequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        currentUserID = user.getUid();

        daoRequest.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postsnap : snapshot.getChildren()) {
                    String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                    Request request = postsnap.getValue(Request.class);

                    if (request.getBookSellerId().equals(currentUserID)) {

                        if (!(request == null))
                            request.setRequestID(pushKey);

                        myRequestList.add(request);
                    }




                }
                myadapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myadapter = new MyRequestAdapter(requireContext(), myRequestList,(SelectRequestItemListener) this);
        recyclerView.setAdapter(myadapter);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_myRequest);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDataAndUpdateUI();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void fetchDataAndUpdateUI() {
        myRequestList.clear();
        daoRequest.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postsnap : snapshot.getChildren()) {
                    String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                    Request request = postsnap.getValue(Request.class);

                    if (request.getBookSellerId().equals(currentUserID)) {

                        if (!(request == null))
                            request.setRequestID(pushKey);

                        myRequestList.add(request);
                    }




                }
                myadapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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