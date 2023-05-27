package com.example.bookexchange.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookexchange.R;
import com.example.bookexchange.dao.DAONotificationPosts;
import com.example.bookexchange.models.NotificationRequest;
import com.example.bookexchange.models.ReceivedPostsNotification;
import com.example.bookexchange.models.ReceivedRequestsNotification;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationRequestAdapter extends RecyclerView.Adapter<NotificationRequestAdapter.NotificationViewHolder> {

    Context context;
    List<NotificationRequest> myNotificationArray;
    DAONotificationPosts daoNotificationPosts;
    DatabaseReference databaseReference;

    FirebaseAuth auth;
    FirebaseUser user;

    public NotificationRequestAdapter(Context context, List<NotificationRequest> myNotificationArray) {
        this.context = context;
        this.myNotificationArray = myNotificationArray;


    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notification_item, parent, false);
        NotificationRequestAdapter.NotificationViewHolder viewHolder = new NotificationRequestAdapter.NotificationViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationRequestAdapter.NotificationViewHolder holder, int position) {
        final NotificationRequest myNotiList = myNotificationArray.get(position);

        daoNotificationPosts = new DAONotificationPosts();

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

                            NotificationRequest notification = myNotificationArray.get(position);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference(NotificationRequest.class.getSimpleName());

                            databaseReference.child(user.getUid())
                                    .child(notification.notificationID)
                                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {


                                        }
                                    });

                            String states = "Accepted";
                            Date date = Calendar.getInstance().getTime();
                            String dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
                            ReceivedPostsNotification receivedPostsNotification = new ReceivedPostsNotification(myNotificationArray.get(position).userID, user.getUid(), user.getDisplayName(), myNotificationArray.get(position).bookName, states.trim(),dateFormat);
                            database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference(ReceivedRequestsNotification.class.getSimpleName());
                            databaseReference.child(myNotificationArray.get(position).userID).push().setValue(receivedPostsNotification);

                            myNotificationArray.remove(position);
                            notifyItemRemoved(position);


                            DatabaseReference databaseReference2 = database.getReference("Request"); // Replace "Post" with the actual node name where the posts are stored
                            databaseReference2.child(notification.getRequestID())
                                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

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
                            NotificationRequest notification = myNotificationArray.get(position);


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference(NotificationRequest.class.getSimpleName());


                            databaseReference.child(user.getUid())
                                    .child(notification.notificationID)
                                    .removeValue().addOnSuccessListener(command -> {



                                    });
                            Date date = Calendar.getInstance().getTime();
                            String dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
                            String states = "Canceled";

                            ReceivedPostsNotification receivedPostsNotification = new ReceivedPostsNotification(myNotificationArray.get(position).userID, user.getUid(), user.getDisplayName(), myNotificationArray.get(position).bookName, states.trim(),dateFormat);
                            FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                            databaseReference = database2.getReference(ReceivedPostsNotification.class.getSimpleName());
                            databaseReference.child(myNotificationArray.get(position).userID).push().setValue(receivedPostsNotification);

                            myNotificationArray.remove(position);
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
        return myNotificationArray.size();
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
