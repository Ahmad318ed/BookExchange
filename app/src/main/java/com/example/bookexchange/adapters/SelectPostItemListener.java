package com.example.bookexchange.adapters;

import android.view.Menu;

import com.example.bookexchange.models.Post;
import com.google.firebase.auth.FirebaseUser;

public interface SelectPostItemListener {


    boolean onCreateOptionsMenu(Menu menu);

    void onItemViewClicked(Post post);

    void onItemTakeClicked(Post post, FirebaseUser currentUser);
    void onItemDeleteClicked(Post post, FirebaseUser currentUser,int position);




}
