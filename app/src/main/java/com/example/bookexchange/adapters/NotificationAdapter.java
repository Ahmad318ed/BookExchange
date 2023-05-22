package com.example.bookexchange.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookexchange.R;
import com.example.bookexchange.dao.DAONotification;
import com.example.bookexchange.models.Notification;
import com.example.bookexchange.models.ReceivedNotification;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    Context context;
    List<Notification> myNotificationArray;
    DAONotification daoNotification;
    DatabaseReference databaseReference;

    FirebaseAuth auth;
    FirebaseUser user;

    public NotificationAdapter(Context context, List<Notification> myNotificationArray) {
        this.context = context;
        this.myNotificationArray = myNotificationArray;


    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notification_item, parent, false);
        NotificationAdapter.NotificationViewHolder viewHolder = new NotificationAdapter.NotificationViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        final Notification myNotiList = myNotificationArray.get(position);

        daoNotification = new DAONotification();

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

                            Notification notification = myNotificationArray.get(position);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference(Notification.class.getSimpleName());

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
                            ReceivedNotification receivedNotification = new ReceivedNotification(myNotificationArray.get(position).userID, user.getUid(), user.getDisplayName(), myNotificationArray.get(position).bookName, states.trim(),dateFormat);
                            database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference(ReceivedNotification.class.getSimpleName());
                            databaseReference.child(myNotificationArray.get(position).userID).push().setValue(receivedNotification);



                            NotificationAdapter.this.notifyItemRemoved(position);
                            NotificationAdapter.this.notifyItemRangeChanged(0,getItemCount()-position);





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
                            Notification notification = myNotificationArray.get(position);


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            databaseReference = database.getReference(Notification.class.getSimpleName());


                            databaseReference.child(user.getUid())
                                    .child(notification.notificationID)
                                    .removeValue().addOnSuccessListener(command -> {



                                    });
                            Date date = Calendar.getInstance().getTime();
                            String dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
                            String states = "Canceled";

                            ReceivedNotification receivedNotification = new ReceivedNotification(myNotificationArray.get(position).userID, user.getUid(), user.getDisplayName(), myNotificationArray.get(position).bookName, states.trim(),dateFormat);
                            FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                            databaseReference = database2.getReference(ReceivedNotification.class.getSimpleName());
                            databaseReference.child(myNotificationArray.get(position).userID).push().setValue(receivedNotification);

                            NotificationAdapter.this.notifyItemRemoved(position);
                            NotificationAdapter.this.notifyItemRangeChanged(position,getItemCount()-position);


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
