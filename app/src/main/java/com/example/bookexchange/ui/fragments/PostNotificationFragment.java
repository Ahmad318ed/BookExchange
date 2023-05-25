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
import com.example.bookexchange.adapters.NotificationPostsAdapter;
import com.example.bookexchange.dao.DAONotificationPosts;
import com.example.bookexchange.models.NotificationPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PostNotificationFragment extends Fragment {

    View inflate;
    RecyclerView recyclerView;
    NotificationPostsAdapter myadapter;
    public static List<NotificationPost> notificationPostList = new ArrayList<>();
    public static ArrayList<String> pushKeysList = new ArrayList<>();
    DatabaseReference databaseReference;
    DAONotificationPosts daoNotificationPosts;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_post_notification, container, false);
        return inflate;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        notificationPostList.clear();

        daoNotificationPosts = new DAONotificationPosts();
        recyclerView = view.findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(NotificationPost.class.getSimpleName());


        String currentUserID = user.getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    notificationPostList.clear();

                    for (DataSnapshot idSnapshot : snapshot.getChildren()) {
                        if (idSnapshot.getKey().equals(currentUserID)) {
                            for (DataSnapshot pushSnapshot : idSnapshot.getChildren()) {
                                String pushKey = pushSnapshot.getKey(); // Get the dynamically generated push key


                                // Access the child node data using the push key
                                NotificationPost notificationPost = pushSnapshot.getValue(NotificationPost.class);
                                if (!(notificationPost == null))
                                    notificationPost.notificationID = pushKey;


                                notificationPostList.add(notificationPost);
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


        myadapter = new NotificationPostsAdapter(requireContext(), notificationPostList);
        recyclerView.setAdapter(myadapter);

    }
}