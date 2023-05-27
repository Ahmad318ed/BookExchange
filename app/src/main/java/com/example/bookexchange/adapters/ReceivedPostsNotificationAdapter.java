package com.example.bookexchange.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookexchange.R;
import com.example.bookexchange.models.ReceivedPostsNotification;
import com.example.bookexchange.models.ReceivedRequestsNotification;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ReceivedPostsNotificationAdapter extends RecyclerView.Adapter<ReceivedPostsNotificationAdapter.ReceivedNotificationViewHolder> {

    Context context;
    List<ReceivedPostsNotification> myNotificationArray;

    FirebaseAuth auth;
    FirebaseUser user;


    public ReceivedPostsNotificationAdapter(Context context, List<ReceivedPostsNotification> myNotificationArray) {
        this.context = context;
        this.myNotificationArray = myNotificationArray;


    }

    @NonNull
    @Override
    public ReceivedPostsNotificationAdapter.ReceivedNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notification_received_post_item, parent, false);
        ReceivedPostsNotificationAdapter.ReceivedNotificationViewHolder viewHolder = new ReceivedPostsNotificationAdapter.ReceivedNotificationViewHolder(view);

        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ReceivedPostsNotificationAdapter.ReceivedNotificationViewHolder holder, int position) {
        final ReceivedPostsNotification myNotiList = myNotificationArray.get(position);


        holder.tv_username.setText(myNotiList.getOwner_Username());
        holder.tv_book_name.setText(myNotiList.getBookName());
        holder.tv_data.setText(myNotiList.getDate());


        if (myNotiList.getStates().trim().equals("Canceled")) {

            holder.states_text.setText("Rejected");
            holder.back_color_state.setBackgroundResource(R.drawable.container_bg_state_red);

        }

        if (myNotiList.getStates().trim().equals("Accepted")) {

            holder.states_text.setText("Accepted");
            holder.back_color_state.setBackgroundResource(R.drawable.container_bg_state);


        }
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Are your sure you Want to Remove the Notification ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                auth = FirebaseAuth.getInstance();
                                user = auth.getCurrentUser();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference = database.getReference(ReceivedPostsNotification.class.getSimpleName());
                                String currentUserID = user.getUid();
                                myNotificationArray.remove(position);
                                notifyItemRemoved(position);
                                databaseReference.child(currentUserID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });

                alertDialog.create().show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return myNotificationArray.size();
    }

    public class ReceivedNotificationViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_name, tv_username, tv_data;
        TextView states_text;

        LinearLayout back_color_state;
        ConstraintLayout card;

        public ReceivedNotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_username = itemView.findViewById(R.id.tv_owner_user_name);
            tv_book_name = itemView.findViewById(R.id.book_name_received_notification);
            tv_data = itemView.findViewById(R.id.tv_date_state);
            card = itemView.findViewById(R.id.constraint_card_post);
            states_text = itemView.findViewById(R.id.states_text);
            back_color_state = (LinearLayout) itemView.findViewById(R.id.state_color_back);


        }
    }
}
