package com.example.bookexchange.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookexchange.R;
import com.example.bookexchange.models.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    Context context;
    List<Request> myRequestArray;

    SelectRequestItemListener selectRequestItemListener;


    public RequestAdapter(Context context, List<Request> myRequestArray,SelectRequestItemListener selectRequestItemListener) {

        this.context=context;
        this.myRequestArray=myRequestArray;
        this.selectRequestItemListener=selectRequestItemListener;

    }

    public void setFilteredList(List<Request> filteredList){

        this.myRequestArray=filteredList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.request_item,parent,false);
        RequestViewHolder viewHolder = new RequestViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

        final Request myRequestList = myRequestArray.get(position);

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

        holder.btn_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseAuth auth=FirebaseAuth.getInstance();
                FirebaseUser user=auth.getCurrentUser();
                selectRequestItemListener.onItemGiveClicked(myRequestArray.get(position),user);


            }
        });


    }

    @Override
    public int getItemCount() {

        return myRequestArray.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {


        TextView tv_book_name,tv_book_college,tv_book_price,tv_seller_name,tv_post_date;
        ImageView img;
        AppCompatButton btn_give,btn_view;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_book_name=itemView.findViewById(R.id.book_name_request);
            img=itemView.findViewById(R.id.book_img_request);
            tv_book_college=itemView.findViewById(R.id.tv_book_college_request);
            tv_book_price=itemView.findViewById(R.id.tv_price_request);
            tv_seller_name=itemView.findViewById(R.id.book_seller_request);
            tv_post_date=itemView.findViewById(R.id.tv_date_request);
            btn_view=itemView.findViewById(R.id.btn_view_request);
            btn_give=itemView.findViewById(R.id.btn_give_request);

        }


    }


}
