package com.example.bookexchange.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.bookexchange.adapters.PostAdapter;
import com.example.bookexchange.R;
import com.example.bookexchange.adapters.SelectPostItemListener;
import com.example.bookexchange.dao.DAONotificationPosts;
import com.example.bookexchange.dao.DAOPost;
import com.example.bookexchange.models.NotificationPost;
import com.example.bookexchange.models.Post;
import com.example.bookexchange.models.ReceivedPostsNotification;
import com.example.bookexchange.ui.activites.HomeActivity;
import com.example.bookexchange.ui.activites.LoginActivity;
import com.example.bookexchange.ui.activites.SignUpActivity;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class PostsFragment extends Fragment implements SelectPostItemListener, Serializable {


    RecyclerView recyclerView;
    public static PostAdapter myadapter;
    FirebaseAuth auth;
    FirebaseUser user;

    String currentUserID;
    String Collage;
    public static List<Post> postList = new ArrayList<>();

    DAOPost daoPost;
    DAONotificationPosts daoNotificationPosts;
    DatabaseReference databaseReference;
    private boolean fabShouldBeHidden = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_posts, container, false);


        fabShouldBeHidden = false;


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        daoPost = new DAOPost();
        daoNotificationPosts = new DAONotificationPosts();

        if (user != null) {
            // User is logged in
            // Perform necessary actions
            postList.clear();
            recyclerView = view.findViewById(R.id.rv_post);
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


            daoPost.get().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot postsnap : snapshot.getChildren()) {
                        String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                        Post post = postsnap.getValue(Post.class);


                        if (!(post.getBookSellerId().equals(currentUserID))) {

                            if (post.getBookCollege().equals(Collage)) {
                                post.setPostID(pushKey);
                                postList.add(post);

                            }


                        }


                    }
                    myadapter.notifyDataSetChanged();

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Collections.sort(postList, Collections.reverseOrder());
            myadapter = new PostAdapter(requireContext(), postList, this);
            recyclerView.setAdapter(myadapter);

            SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe);
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
//            }
//        });
        } else {
            // User is not logged in
            // Redirect to login screen or show a login prompt

            fabShouldBeHidden = false;


            auth = FirebaseAuth.getInstance();
            daoPost = new DAOPost();
            daoNotificationPosts = new DAONotificationPosts();
            postList.clear();


            recyclerView = view.findViewById(R.id.rv_post);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);


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


            daoPost.get().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot postsnap : snapshot.getChildren()) {
                        String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                        Post post = postsnap.getValue(Post.class);


                        if (post.getBookCollege().equals(Collage)) {
                            post.setPostID(pushKey);
                            postList.add(post);

                        }


                    }
                    myadapter.notifyDataSetChanged();

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Collections.sort(postList, Collections.reverseOrder());
            myadapter = new PostAdapter(requireContext(), postList, this);
            recyclerView.setAdapter(myadapter);

            SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe);
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
//            }
//        });
        }


        return view;
    }

    private void fetchDataAndUpdateUI() {

        if (user != null) {
            // User is logged in
            // Perform necessary actions

            postList.clear();

            daoPost.get().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot postsnap : snapshot.getChildren()) {
                        String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                        Post post = postsnap.getValue(Post.class);


                        if (!(post.getBookSellerId().equals(currentUserID))) {

                            if (post.getBookCollege().equals(Collage)) {
                                post.setPostID(pushKey);
                                postList.add(post);

                            }


                        }


                    }
                    myadapter.notifyDataSetChanged();

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            // User is not logged in
            // Redirect to login screen or show a login prompt

            postList.clear();

            daoPost.get().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot postsnap : snapshot.getChildren()) {
                        String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                        Post post = postsnap.getValue(Post.class);


                        if (post.getBookCollege().equals(Collage)) {
                            post.setPostID(pushKey);
                            postList.add(post);

                        }


                    }
                    myadapter.notifyDataSetChanged();

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


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
//        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//
//
//                getActivity().finish();
//
//            }
//        });
//        // Show or hide the FAB based on the flag
//        if (fabShouldBeHidden) {
//            HomeActivity.fab.hide();
//        } else {
//            HomeActivity.fab.show();
//        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onItemViewClicked(Post post) {



            Intent intent = new Intent(getContext(), ViewPostActivity.class);
            intent.putExtra("post", post);
            startActivity(intent);



    }


    @Override
    public void onItemTakeClicked(Post post, FirebaseUser currentUser) {
        if (user != null) {

            // User is logged in
            // Perform necessary actions

            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(requireContext());
            alertDialog2.setTitle("Confirm");
            alertDialog2.setMessage("Are You Sure ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Date date = Calendar.getInstance().getTime();
                            String dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
                            auth = FirebaseAuth.getInstance();
                            user = auth.getCurrentUser();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference(NotificationPost.class.getSimpleName());
                            String currentUserID = user.getUid();


                            if (!(post.getBookSellerId().equals(currentUser.getUid()))) {


                                NotificationPost notificationPost2 = new NotificationPost(currentUser.getUid(), currentUser.getDisplayName(), post.getBookName(), dateFormat);
                                notificationPost2.setPostID(post.getPostID());


                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        boolean notificationExists = false;


                                        for (DataSnapshot idSnapshot : snapshot.getChildren()) {
                                            if (idSnapshot.getKey().equals(post.getBookSellerId())) {
                                                for (DataSnapshot pushSnapshot : idSnapshot.getChildren()) {
                                                    NotificationPost notificationPost = pushSnapshot.getValue(NotificationPost.class);


                                                    if (notificationPost.getUserName().equals(user.getDisplayName()) && notificationPost.getBookName().equals(post.getBookName())) {
                                                        // NotificationPost already exists
                                                        notificationExists = true;
                                                        Toast.makeText(requireContext(), "You have already sent this Notification before !", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    }
                                                }
                                                break; // Exit the loop once the specific ID is found
                                            }
                                        }

                                        if (!notificationExists) {
                                            daoNotificationPosts.add(notificationPost2, post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(requireContext(), "Notification has been sent to the user", Toast.LENGTH_SHORT).show();
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
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });


            alertDialog2.create().show();




        } else {
            // User is not logged in
            // Redirect to login screen or show a login prompt
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_login_signup, null);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialogView.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    startActivity(intent);

                }
            });

            dialogView.findViewById(R.id.signUp_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(requireContext(), SignUpActivity.class);
                    startActivity(intent);

                }
            });


            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            dialog.show();


        }


    }

    @Override
    public void onItemDeleteClicked(Post post, FirebaseUser currentUser, int position) {

    }


}