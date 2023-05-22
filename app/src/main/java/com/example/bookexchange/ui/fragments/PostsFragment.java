package com.example.bookexchange.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookexchange.adapters.PostAdapter;
import com.example.bookexchange.R;
import com.example.bookexchange.adapters.SelectPostItemListener;
import com.example.bookexchange.dao.DAONotification;
import com.example.bookexchange.dao.DAOPost;
import com.example.bookexchange.models.Notification;
import com.example.bookexchange.models.Post;
import com.example.bookexchange.ui.activites.HomeActivity;
import com.example.bookexchange.ui.activites.ViewPostActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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


public class PostsFragment extends Fragment implements SelectPostItemListener, Serializable {


    RecyclerView recyclerView;
    public static PostAdapter myadapter;
    FirebaseAuth auth;
    FirebaseUser user;

    String currentUserID;
    public static List<Post> postList = new ArrayList<>();

    DAOPost daoPost;
    DAONotification daoNotification;
    DatabaseReference databaseReference;
    private boolean fabShouldBeHidden = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_posts, container, false);

        fabShouldBeHidden = false;
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().detach(PostsFragment.this).attach(PostsFragment.this).commit();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        daoPost = new DAOPost();
        daoNotification = new DAONotification();
        postList.clear();


        recyclerView = view.findViewById(R.id.rv_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        currentUserID = user.getUid();

        daoPost.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postsnap : snapshot.getChildren()) {
                    String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                    Post post = postsnap.getValue(Post.class);



                    if (!(post.getBookSellerId().equals(currentUserID))) {



                        postList.add(post);



                    }



                }
                myadapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myadapter = new PostAdapter(requireContext(), postList, this);
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
//            }
//        });




        return view;
    }






    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });


    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {


                getActivity().finish();

            }
        });
        // Show or hide the FAB based on the flag
        if (fabShouldBeHidden) {
            HomeActivity.fab.hide();
        } else {
            HomeActivity.fab.show();
        }


    }


    @Override
    public void onItemViewClicked(Post post) {

        Intent intent = new Intent(getContext(), ViewPostActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);


    }


    @Override
    public void onItemTakeClicked(Post post, FirebaseUser currentUser) {

        Date date = Calendar.getInstance().getTime();
        String dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Notification.class.getSimpleName());
        String currentUserID = user.getUid();


        if (!(post.getBookSellerId().equals(currentUser.getUid()))) {


            Notification notification2 = new Notification(currentUser.getUid(), currentUser.getDisplayName(), post.getBookName(), dateFormat);


            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean notificationExists = false;


                    for (DataSnapshot idSnapshot : snapshot.getChildren()) {
                        if (idSnapshot.getKey().equals(post.getBookSellerId())) {
                            for (DataSnapshot pushSnapshot : idSnapshot.getChildren()) {
                                Notification notification = pushSnapshot.getValue(Notification.class);


                                if (notification.getUserName().equals(user.getDisplayName()) && notification.getBookName().equals(post.getBookName())) {
                                    // Notification already exists
                                    notificationExists = true;
                                    Toast.makeText(requireContext(), "You have already sent this notification before!", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            break; // Exit the loop once the specific ID is found
                        }
                    }

                    if (!notificationExists) {
                        daoNotification.add(notification2, post).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(requireContext(), "Notification added to DB", Toast.LENGTH_SHORT).show();
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
    public void onItemDeleteClicked(Post post, FirebaseUser currentUser) {

    }


}