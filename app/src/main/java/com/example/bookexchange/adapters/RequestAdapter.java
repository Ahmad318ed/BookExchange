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
        holder.tv_edition.setText(myRequestList.getBookEdition());
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


        TextView tv_name,tv_edition,tv_seller,tv_college,tv_price;
        ImageView img;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name=itemView.findViewById(R.id.textName_request);
            tv_edition=itemView.findViewById(R.id.textEdition_request);
            img=itemView.findViewById(R.id.img_book);

            tv_price=itemView.findViewById(R.id.textSellPrice_request);
            tv_college=itemView.findViewById(R.id.textCollege_request);
            tv_seller=itemView.findViewById(R.id.textUsername_request);


        }


    }


}
