package com.example.bookexchange.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookexchange.R;
import com.example.bookexchange.models.Request;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    Context context;
    List<Request> myRequestArray;

    public RequestAdapter(Context context, List<Request> myRequestArray) {

        this.context=context;
        this.myRequestArray=myRequestArray;
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

        holder.tv_name.setText(myRequestList.getBookName());
        holder.img.setImageResource(myRequestList.getImg());
        holder.tv_college.setText(myRequestList.getBookCollege());
        holder.tv_price.setText(myRequestList.getBookPrice());
        holder.tv_seller.setText(myRequestList.getBookSeller());





    }

    @Override
    public int getItemCount() {

        return myRequestArray.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {


        TextView tv_name,tv_college,tv_price,tv_seller;
        ImageView img;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name=itemView.findViewById(R.id.book_name_request);
            img=itemView.findViewById(R.id.book_img_request);
            tv_college=itemView.findViewById(R.id.tv_book_college_request);
            tv_price=itemView.findViewById(R.id.tv_price_request_request);
            tv_seller=itemView.findViewById(R.id.book_seller_request);

        }


    }


}
