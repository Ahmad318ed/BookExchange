package com.example.bookexchange.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookexchange.R;
import com.example.bookexchange.dao.DAORequest;
import com.example.bookexchange.models.Request;
import com.example.bookexchange.ui.fragments.MyRequestFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MyRequestAdapter extends RecyclerView.Adapter<MyRequestAdapter.MyPostViewHolder> {

    Context context;
    List<Request> myRequestArray;
    DAORequest daoRequest;


    SelectRequestItemListener selectRequestItemListener;

    public MyRequestAdapter() {
    }

    public MyRequestAdapter(Context context, List<Request> myRequestArray, SelectRequestItemListener selectRequestItemListener) {

        this.context = context;
        this.myRequestArray = myRequestArray;
        this.selectRequestItemListener = selectRequestItemListener;
    }


    public void setfilteredlist(List<Request> filteredList) {

        this.myRequestArray = filteredList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.myrequest_item, parent, false);
        MyPostViewHolder viewHolder = new MyPostViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostViewHolder holder, int position) {

        final Request myRequestList = myRequestArray.get(position);


        daoRequest = new DAORequest();
        holder.tv_book_name.setText(myRequestList.getBookName());
        Glide.with(context).load(myRequestList.getImg()).fitCenter().centerCrop().into(holder.img);
        holder.tv_book_college.setText(myRequestList.getBookCollege());
        holder.tv_book_price.setText(myRequestList.getBookPrice());
        holder.tv_seller_name.setText(myRequestList.getBookSellerName());
        holder.tv_post_date.setText(myRequestList.getRequestDate());

        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectRequestItemListener.onItemViewClicked(myRequestArray.get(position));

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
                alertDialog2.setTitle("Confirm");
                alertDialog2.setMessage("Do you Want to Accept ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                daoRequest.remove(myRequestList.getRequestID()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(context, "The Post has been deleted", Toast.LENGTH_SHORT).show();

                                    }
                                });


                                myRequestArray.remove(position);
                                notifyItemRemoved(position);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });

                alertDialog2.create().show();







            }
        });


    }

    @Override
    public int getItemCount() {

        return myRequestArray.size();
    }

    public class MyPostViewHolder extends RecyclerView.ViewHolder {


        TextView tv_book_name, tv_book_college, tv_book_price, tv_seller_name, tv_post_date;
        ImageView img;
        AppCompatButton btn_delete, btn_view;


        public MyPostViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_book_name = itemView.findViewById(R.id.book_name_myRequest);
            img = itemView.findViewById(R.id.book_img_myRequest);
            tv_book_college = itemView.findViewById(R.id.tv_book_college_myRequest);
            tv_book_price = itemView.findViewById(R.id.tv_price_myRequest);
            tv_seller_name = itemView.findViewById(R.id.book_seller_myRequest);
            tv_post_date = itemView.findViewById(R.id.tv_date_myRequest);
            btn_view = itemView.findViewById(R.id.btn_view_myRequest);
            btn_delete = itemView.findViewById(R.id.btn_delete_myRequest);

        }


    }


}
