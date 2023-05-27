package com.example.bookexchange.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookexchange.R;
import com.example.bookexchange.dao.DAONotificationPosts;
import com.example.bookexchange.dao.DAOPost;
import com.example.bookexchange.models.NotificationPost;
import com.example.bookexchange.models.Post;
import com.example.bookexchange.models.ReceivedPostsNotification;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationPostsAdapter extends RecyclerView.Adapter<NotificationPostsAdapter.NotificationViewHolder> {

    Context context;
    List<NotificationPost> myNotificationArrayPost;
    DAONotificationPosts daoNotificationPosts;
    DatabaseReference databaseReference;
    Post post;
    Post post2;
    DAOPost daoPost;

    FirebaseAuth auth;
    FirebaseUser user;

    public NotificationPostsAdapter(Context context, List<NotificationPost> myNotificationArrayPost) {
        this.context = context;
        this.myNotificationArrayPost = myNotificationArrayPost;


    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notification_item, parent, false);
        NotificationPostsAdapter.NotificationViewHolder viewHolder = new NotificationPostsAdapter.NotificationViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationPostsAdapter.NotificationViewHolder holder, int position) {
        final NotificationPost myNotiList = myNotificationArrayPost.get(position);

        daoNotificationPosts = new DAONotificationPosts();
        daoPost = new DAOPost();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        holder.tv_username.setText(myNotiList.getUserName());
        holder.tv_book_name.setText(myNotiList.getBookName());
        holder.tv_notification_Date.setText(myNotiList.getNotificationDate());

        holder.btn_accept.setOnClickListener(v -> {

            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
            alertDialog2.setTitle("Confirm");
            alertDialog2.setMessage("Do you Want to Accept ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            NotificationPost notificationPost = myNotificationArrayPost.get(position);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference(NotificationPost.class.getSimpleName());

                            databaseReference.child(user.getUid())
                                    .child(notificationPost.notificationID)
                                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {


                                        }
                                    });

                            String states = "Accepted";
                            Date date = Calendar.getInstance().getTime();
                            String dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
                            ReceivedPostsNotification receivedPostsNotification = new ReceivedPostsNotification(myNotificationArrayPost.get(position).userID, user.getUid(), user.getDisplayName(), myNotificationArrayPost.get(position).bookName, states.trim(), dateFormat);
                            database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference(ReceivedPostsNotification.class.getSimpleName());
                            databaseReference.child(myNotificationArrayPost.get(position).userID).push().setValue(receivedPostsNotification);

                            myNotificationArrayPost.remove(position);
                            notifyItemRemoved(position);

                           DatabaseReference databaseReference2 = database.getReference("Post"); // Replace "Post" with the actual node name where the posts are stored
                            databaseReference2.child(notificationPost.getPostID())
                                    .removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // Post removed successfully
                                        }
                                    });



                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });


            alertDialog2.create().show();

            notifyDataSetChanged();

        });

        holder.btn_cancel.setOnClickListener(v -> {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Confirm");
            alertDialog.setMessage("Are your sure you Want to Cancel the Request ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NotificationPost notificationPost = myNotificationArrayPost.get(position);


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference(NotificationPost.class.getSimpleName());


                            databaseReference.child(user.getUid())
                                    .child(notificationPost.notificationID)
                                    .removeValue().addOnSuccessListener(command -> {


                                    });
                            Date date = Calendar.getInstance().getTime();
                            String dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
                            String states = "Canceled";

                            ReceivedPostsNotification receivedPostsNotification = new ReceivedPostsNotification(myNotificationArrayPost.get(position).userID, user.getUid(), user.getDisplayName(), myNotificationArrayPost.get(position).bookName, states.trim(), dateFormat);
                            FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                            databaseReference = database2.getReference(ReceivedPostsNotification.class.getSimpleName());
                            databaseReference.child(myNotificationArrayPost.get(position).userID).push().setValue(receivedPostsNotification);

                            myNotificationArrayPost.remove(position);
                            notifyItemRemoved(position);


                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });

            alertDialog.create().show();


        });


    }


    @Override
    public int getItemCount() {
        return myNotificationArrayPost.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_name, tv_username, tv_notification_Date;
        Button btn_cancel, btn_accept;


        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);


            tv_username = itemView.findViewById(R.id.tv_user_name);
            tv_notification_Date = itemView.findViewById(R.id.tv_date);
            tv_book_name = itemView.findViewById(R.id.book_name_notification);

            btn_accept = itemView.findViewById(R.id.btn_accept);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);


        }
    }
}
