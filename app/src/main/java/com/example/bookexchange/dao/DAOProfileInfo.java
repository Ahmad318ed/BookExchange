package com.example.bookexchange.dao;

import com.example.bookexchange.models.Post;
import com.example.bookexchange.models.Profile_info;
import com.example.bookexchange.ui.activites.Profile;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOProfileInfo {
    DatabaseReference databaseReference;



    public DAOProfileInfo() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(Profile_info.class.getSimpleName());
    }



    public Task<Void> add(Profile_info profile){

        return databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(profile);
//         databaseReference.push().setValue(profile);

    }

    public Task<Void>update(String key, HashMap<String,Object> hashMap){

        return databaseReference.child(key).updateChildren(hashMap);

    }

    public Task<Void>remove(String key){


        return databaseReference.child(key).removeValue();
    }
    public Query get(){

        return databaseReference.orderByKey();
    }
}
