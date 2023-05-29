package com.example.bookexchange.ui.fragments;

import static com.example.bookexchange.ui.fragments.PostsFragment.postList;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookexchange.R;
import com.example.bookexchange.adapters.MyPostAdapter;
import com.example.bookexchange.adapters.SelectPostItemListener;
import com.example.bookexchange.dao.DAOPost;
import com.example.bookexchange.models.Post;
import com.example.bookexchange.ui.activites.ViewPostActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPostsFragment extends Fragment implements SelectPostItemListener {

    View view;
    DAOPost daoPost;
    FirebaseAuth auth;
    FirebaseUser user;
    String currentUserID;
    public static List<Post> myPostList = new ArrayList<>();
    DatabaseReference databaseReference;
    public static MyPostAdapter myadapter;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.from(getContext()).inflate(R.layout.fragment_my_posts, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        daoPost = new DAOPost();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        myPostList.clear();
        recyclerView = view.findViewById(R.id.rv_post_myPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        currentUserID = user.getUid();

        daoPost.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postsnap : snapshot.getChildren()) {
                    String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                    Post post = postsnap.getValue(Post.class);

                    if (post.getBookSellerId().equals(currentUserID)) {

                        if (!(post == null)) {
                            post.setBookSellerName(user.getDisplayName());
                            post.setPostID(pushKey);
                            myPostList.add(post);
                        }
                    }


                }
                myadapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myadapter = new MyPostAdapter(requireContext(), myPostList, this);
        recyclerView.setAdapter(myadapter);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_myPosts);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDataAndUpdateUI();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void fetchDataAndUpdateUI() {
        myPostList.clear();

        daoPost.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postsnap : snapshot.getChildren()) {
                    String pushKey = postsnap.getKey(); // Get the dynamically generated push key

                    Post post = postsnap.getValue(Post.class);

                    if (post.getBookSellerId().equals(currentUserID)) {

                        if (!(post == null)) {
                            post.setBookSellerName(user.getDisplayName());
                            post.setPostID(pushKey);
                            myPostList.add(post);
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


    private void filtertext(String text) {

        List<Post> filteredList = new ArrayList<>();

        for (Post post : postList) {

            if (post.getBookName().toLowerCase().contains(text.toLowerCase())) {

                filteredList.add(post);

            }

            if (filteredList.isEmpty()) {


            } else {

                myadapter.setfilteredlist(filteredList);

            }

        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Here..");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtertext(newText);

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {








        return true;
    }

    @Override
    public void onItemViewClicked(Post post) {

        Intent intent = new Intent(getContext(), ViewPostActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);


    }

    @Override
    public void onItemTakeClicked(Post post, FirebaseUser currentUser) {

    }

    @Override
    public void onItemDeleteClicked(Post post, FirebaseUser currentUser, int position) {


    }
}