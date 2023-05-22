package com.example.bookexchange.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookexchange.adapters.PostAdapter;
import com.example.bookexchange.R;
import com.example.bookexchange.adapters.SelectPostItemListener;
import com.example.bookexchange.dao.DAOPost;
import com.example.bookexchange.models.Post;
import com.example.bookexchange.ui.activites.HomeActivity;
import com.example.bookexchange.ui.activites.ViewPostActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PostsFragment extends Fragment implements SelectPostItemListener, Serializable {


    RecyclerView recyclerView;
    public static PostAdapter myadapter;
    public static List<Post> postList = new ArrayList<>();
    DAOPost daoPost;
    SwipeRefreshLayout swipeRefreshLayout;


    private boolean fabShouldBeHidden = false; // Flag to keep track of FAB visibility


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_posts, container, false);
        fabShouldBeHidden = false;
        daoPost = new DAOPost();
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().detach(PostsFragment.this).attach(PostsFragment.this).commit();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        postList.clear();


        recyclerView = view.findViewById(R.id.rv_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        daoPost.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postsnap : snapshot.getChildren()) {

                    Post post = postsnap.getValue(Post.class);


                    postList.add(post);
                    myadapter = new PostAdapter(requireContext(), postList, new SelectPostItemListener() {
                        @Override
                        public void onItemClicked(Post post) {


                            Intent intent = new Intent(getContext(), ViewPostActivity.class);
                            intent.putExtra("post", post);
                            startActivity(intent);


                        }
                    });
                    recyclerView.setAdapter(myadapter);
                    myadapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

            }
        });

        return view;
    }




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
    public void onItemClicked(Post post) {

    }
}