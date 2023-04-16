package com.example.bookexchange.dao;

import com.example.bookexchange.models.Post;
import com.example.bookexchange.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOPost {

    DatabaseReference databaseReference;

    public DAOPost() {

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(Post.class.getSimpleName());

    }


    public Task<Void>add(Post post){

        return databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(post);

    }


//    HashMap<String,Object>hashMap=new HashMap<>();
//        hashMap.put("key","value");
//        hashMap.put("key","value");

    public Task<Void>update(String key, HashMap<String,Object>hashMap){

        return databaseReference.child(key).updateChildren(hashMap);

    }

    public Task<Void>remove(String key){


        return databaseReference.child(key).removeValue();
    }
    public Query get(){

        return databaseReference.orderByKey();
    }

}
