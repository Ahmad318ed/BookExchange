package com.example.bookexchange.adapters;

import com.example.bookexchange.models.Post;
import com.google.firebase.auth.FirebaseUser;

public interface SelectPostItemListener {


    void onItemViewClicked(Post post);

    void onItemTakeClicked(Post post, FirebaseUser currentUser);
    void onItemDeleteClicked(Post post, FirebaseUser currentUser);




}
