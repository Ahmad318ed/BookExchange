package com.example.bookexchange.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookexchange.R;
import com.example.bookexchange.adapters.NotificationRequestAdapter;
import com.example.bookexchange.dao.DAONotificationRequest;
import com.example.bookexchange.models.NotificationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestNotificationFragment extends Fragment {

    View inflate;
    RecyclerView recyclerView;
    NotificationRequestAdapter myadapter;
    public static List<NotificationRequest> notificationList = new ArrayList<>();
    public static ArrayList<String> pushKeysList = new ArrayList<>();
    DatabaseReference databaseReference;
    DAONotificationRequest daoNotification;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_request_notification, container, false);
        return inflate;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        notificationList.clear();

        daoNotification = new DAONotificationRequest();
        recyclerView = view.findViewById(R.id.notificationRecyclerView_request);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(NotificationRequest.class.getSimpleName());


        String currentUserID = user.getUid();
        daoNotification.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    notificationList.clear();

                    for (DataSnapshot idSnapshot : snapshot.getChildren()) {
                        if (idSnapshot.getKey().equals(currentUserID)) {
                            for (DataSnapshot pushSnapshot : idSnapshot.getChildren()) {
                                String pushKey = pushSnapshot.getKey(); // Get the dynamically generated push key


                                // Access the child node data using the push key
                                NotificationRequest notification = pushSnapshot.getValue(NotificationRequest.class);
                                if (!(notification == null))
                                    notification.notificationID = pushKey;


                                notificationList.add(notification);
                                myadapter.notifyDataSetChanged();

                                // Do something with the retrieved data
                            }
                            break; // Exit the loop once the specific ID is found
                        }
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myadapter = new NotificationRequestAdapter(requireContext(), notificationList);
        recyclerView.setAdapter(myadapter);

    }
}