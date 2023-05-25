package com.example.bookexchange.adapters;

import com.example.bookexchange.models.Post;
import com.example.bookexchange.models.Request;
import com.google.firebase.auth.FirebaseUser;

public interface SelectRequestItemListener {


    void onItemViewClicked(Request request);

    void onItemGiveClicked(Request request, FirebaseUser currentUser);
    void onItemDeleteClicked(Request request, FirebaseUser currentUser);




}
