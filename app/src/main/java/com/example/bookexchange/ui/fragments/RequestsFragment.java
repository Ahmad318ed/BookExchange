package com.example.bookexchange.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookexchange.R;
import com.example.bookexchange.adapters.RequestAdapter;
import com.example.bookexchange.adapters.SelectRequestItemListener;
import com.example.bookexchange.dao.DAONotificationRequest;
import com.example.bookexchange.dao.DAORequest;
import com.example.bookexchange.models.NotificationRequest;
import com.example.bookexchange.models.Request;
import com.example.bookexchange.ui.activites.HomeActivity;
import com.example.bookexchange.ui.activites.ViewRequestActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RequestsFragment extends Fragment implements SelectRequestItemListener, Serializable {

    RecyclerView recyclerView;
    public static RequestAdapter myadapter;
    FirebaseAuth auth;
    FirebaseUser user;
    String currentUserID;
    DAORequest daoRequest;
    String Collage;

    DAONotificationRequest daoNotificationrequest;
    DatabaseReference databaseReference;
    View view;
    public static List<Request> requestList = new ArrayList<>();
    private boolean fabShouldBeHidden = false; // Flag to keep track of FAB visibility

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.from(getContext()).inflate(R.layout.fragment_requests, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabShouldBeHidden = false;


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        daoRequest = new DAORequest();
        daoNotificationrequest = new DAONotificationRequest();
        requestList.clear();


        recyclerView = view.findViewById(R.id.rv_request);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        currentUserID = user.getUid();

        String collage = getArguments().getString("Collages");//String text
        switch (collage) {
            case "it":
                Collage = "Information  technology  collage";
                break;
            case "Arts_and_Sciences":
                Collage = "College  of  Arts  and  Sciences";
                break;
            case "Dawah":
                Collage = "College  of  Da'wah  and  Fundamentals  of  Religion";
                break;
            case "Sheikh_Noah":
                Collage = "Sheikh  Noah  College  of  Sharia  and  Law";
                break;
            case "Educational_Sciences":
                Collage = "Faculty  of  Educational  Sciences";
                break;
            case "Islamic_Architecture":
                Collage = "College  of  Arts  and  Islamic  Architecture";
                break;
            case "Money_and_Business":
                Collage = "College  Money  and  Business";
                break;
            case "Maliki_Hanafi_Shafii":
                Collage = "Faculty  of  Al  Hanafi Maliki Shafi'i Jurisprudence";
                break;
            case "Graduate_Studies":
                Collage = "College  Graduate  Studies";
                break;


        }

        daoRequest.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postsnap : snapshot.getChildren()) {
                    String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                    Request request = postsnap.getValue(Request.class);


                    if (!(request.getBookSellerId().equals(currentUserID))) {

                        if (request.getBookCollege().equals(Collage)) {

                            request.setRequestID(pushKey);
                            requestList.add(request);
                        }


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

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_requests);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDataAndUpdateUI();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

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


    }

    private void fetchDataAndUpdateUI() {
        requestList.clear();

        daoRequest.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postsnap : snapshot.getChildren()) {
                    String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                    Request request = postsnap.getValue(Request.class);


                    if (!(request.getBookSellerId().equals(currentUserID))) {

                        if (request.getBookCollege().equals(Collage)) {

                            request.setRequestID(pushKey);
                            requestList.add(request);
                        }


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

        Date date = Calendar.getInstance().getTime();
        String dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(NotificationRequest.class.getSimpleName());
        String currentUserID = user.getUid();


        if (!(request.getBookSellerId().equals(currentUser.getUid()))) {


            NotificationRequest notification2 = new NotificationRequest(currentUser.getUid(), currentUser.getDisplayName(), request.getBookName(), dateFormat);
            notification2.setRequestID(request.getRequestID());

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean notificationExists = false;


                    for (DataSnapshot idSnapshot : snapshot.getChildren()) {
                        if (idSnapshot.getKey().equals(request.getBookSellerId())) {
                            for (DataSnapshot pushSnapshot : idSnapshot.getChildren()) {
                                NotificationRequest notification = pushSnapshot.getValue(NotificationRequest.class);


                                if (notification.getUserName().equals(user.getDisplayName()) && notification.getBookName().equals(request.getBookName())) {
                                    // NotificationPost already exists
                                    notificationExists = true;
                                    Toast.makeText(requireContext(), "You have already sent this notification before!", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            break; // Exit the loop once the specific ID is found
                        }
                    }

                    if (!notificationExists) {
                        daoNotificationrequest.add(notification2, request).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(requireContext(), "NotificationPost added to DB", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle onCancelled if needed
                }
            });


        } else {
            Toast.makeText(requireContext(), "You Can not Press Take to your Own Book !", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onItemDeleteClicked(Request request, FirebaseUser currentUser) {

    }
}