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
import com.example.bookexchange.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    Context context;
    List<Post> myPostArray;

    public PostAdapter() {
    }

    public PostAdapter(Context context, List<Post> myPostArray) {

        this.context=context;
        this.myPostArray=myPostArray;
    }

    public void setfilteredlist(List<Post> filteredList){

        this.myPostArray=filteredList;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.post_item,parent,false);
        PostViewHolder viewHolder = new PostViewHolder(view);

        return viewHolder;
    }

    private static final String TAG = "PostAdapter";
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        final Post myPostList = myPostArray.get(position);
        Log.i(TAG, "onBindViewHolder: "+myPostList);

        holder.tv_name.setText(myPostList.getBookName());
        holder.tv_edition.setText(myPostList.getBookEdition());
        holder.img.setImageResource(myPostList.getImg());
        holder.tv_college.setText(myPostList.getBookCollege());
        holder.tv_price.setText(myPostList.getBookPrice());
        holder.tv_seller.setText(myPostList.getBookSeller());



    }

    @Override
    public int getItemCount() {

        return myPostArray.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {


        TextView tv_name,tv_edition,tv_college,tv_price,tv_seller;
        ImageView img;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name=itemView.findViewById(R.id.book_name_post);
            tv_edition=itemView.findViewById(R.id.textEdition_post);
            img=itemView.findViewById(R.id.img_book_post);
            tv_college=itemView.findViewById(R.id.textCollege_post);
            tv_price=itemView.findViewById(R.id.textSellPrice_post);
            tv_seller=itemView.findViewById(R.id.textUsername_post);



        }


    }


}
