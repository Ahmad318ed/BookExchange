package com.example.bookexchange.dao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bookexchange.models.Notification;
import com.example.bookexchange.models.Post;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DAONotification {

    DatabaseReference databaseReference;
    public static String pushKey;
    public static String bookSellerId;



    public DAONotification() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Notification.class.getSimpleName());

    }


    public Task<Void> add(com.example.bookexchange.models.Notification notification, Post post) {




        return databaseReference.child(post.getBookSellerId()).push().setValue(notification);


    }


//    HashMap<String,Object>hashMap=new HashMap<>();
//        hashMap.put("key","value");
//        hashMap.put("key","value");

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {

        return databaseReference.child(key).updateChildren(hashMap);

    }

    public Task<Void> remove(String useriD, String pushKey) {


        return databaseReference.child(useriD).child(pushKey).removeValue();
    }

    public Query get() {

        return databaseReference.orderByKey();
    }


}
