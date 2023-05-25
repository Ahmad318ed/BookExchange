package com.example.bookexchange.dao;

import com.example.bookexchange.models.NotificationRequest;
import com.example.bookexchange.models.Request;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAONotificationRequest {

    DatabaseReference databaseReference;
    public static String pushKey;
    public static String bookSellerId;



    public DAONotificationRequest() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(NotificationRequest.class.getSimpleName());

    }


    public Task<Void> add(NotificationRequest notification, Request request) {




        return databaseReference.child(request.getBookSellerId()).push().setValue(notification);


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
