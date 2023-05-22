package com.example.bookexchange.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookexchange.R;
//import com.example.bookexchange.adapters.NotificationAdapter;
import com.example.bookexchange.adapters.NotificationAdapter;
import com.example.bookexchange.adapters.PostAdapter;
import com.example.bookexchange.dao.DAONotification;
import com.example.bookexchange.models.Notification;
import com.example.bookexchange.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    View inflate;

    RecyclerView recyclerView;
    NotificationAdapter myadapter;
    public static List<Notification> notificationList = new ArrayList<>();
    public static ArrayList<String> pushKeysList = new ArrayList<>();

    DatabaseReference databaseReference;

    DAONotification daoNotification;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_notification, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        notificationList.clear();

        daoNotification = new DAONotification();
        recyclerView = view.findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Notification.class.getSimpleName());


        String currentUserID = user.getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    notificationList.clear();

                    for (DataSnapshot idSnapshot : snapshot.getChildren()) {
                        if (idSnapshot.getKey().equals(currentUserID)) {
                            for (DataSnapshot pushSnapshot : idSnapshot.getChildren()) {
                                String pushKey = pushSnapshot.getKey(); // Get the dynamically generated push key


                                // Access the child node data using the push key
                                Notification notification = pushSnapshot.getValue(Notification.class);
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


//                for (DataSnapshot idSnapshot : snapshot.getChildren()) {
//                    String id = idSnapshot.getKey(); // Get the ID (child key) under the "Notification" node
//
//                    for (DataSnapshot pushSnapshot : idSnapshot.getChildren()) {
//                        String pushKey = pushSnapshot.getKey(); // Get the dynamically generated push key
//
//                        // Access the child node data using the push key
//                        Notification notification = pushSnapshot.getValue(Notification.class);
//
//
//
//                        notificationList.add(notification);
//                        myadapter.notifyDataSetChanged();
//
//
//                    }
//
//
//                }.

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myadapter = new NotificationAdapter(requireContext(), notificationList);
        recyclerView.setAdapter(myadapter);


    }


    @Override
    public void onStart() {
        super.onStart();
    }
}