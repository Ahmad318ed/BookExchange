package com.example.bookexchange.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookexchange.R;
import com.example.bookexchange.models.ReceivedNotification;

import java.util.List;

public class ReceivedNotificationAdapter extends RecyclerView.Adapter<ReceivedNotificationAdapter.ReceivedNotificationViewHolder>{

    Context context;
    List<ReceivedNotification> myNotificationArray;




    public ReceivedNotificationAdapter(Context context, List<ReceivedNotification> myNotificationArray) {
        this.context = context;
        this.myNotificationArray = myNotificationArray;


    }
    @NonNull
    @Override
    public ReceivedNotificationAdapter.ReceivedNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notification_received_item, parent, false);
        ReceivedNotificationAdapter.ReceivedNotificationViewHolder viewHolder = new ReceivedNotificationAdapter.ReceivedNotificationViewHolder(view);

        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ReceivedNotificationAdapter.ReceivedNotificationViewHolder holder, int position) {
        final ReceivedNotification myNotiList = myNotificationArray.get(position);



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








    }

    @Override
    public int getItemCount() {
        return myNotificationArray.size();
    }

    public class ReceivedNotificationViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_name, tv_username,tv_data;
        TextView states_text;

        LinearLayout back_color_state;
        public ReceivedNotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_username = itemView.findViewById(R.id.tv_owner_user_name);
            tv_book_name = itemView.findViewById(R.id.book_name_received_notification);
            tv_data = itemView.findViewById(R.id.tv_date_state);

            states_text = itemView.findViewById(R.id.states_text);
            back_color_state = (LinearLayout) itemView.findViewById(R.id.state_color_back);


        }
    }
}
