package com.example.bookexchange.adapters;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookexchange.R;
import com.example.bookexchange.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    Context context;
    List<Post> myPostArray;

    SelectPostItemListener selectPostItemListener;

    public PostAdapter() {
    }

    public PostAdapter(Context context, List<Post> myPostArray,SelectPostItemListener selectPostItemListener) {

        this.context=context;
        this.myPostArray=myPostArray;
        this.selectPostItemListener=selectPostItemListener;
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



        holder.tv_book_name.setText(myPostList.getBookName());
        Glide.with(context).load(myPostList.getImg()).fitCenter().centerCrop().into(holder.img);
        holder.tv_book_college.setText(myPostList.getBookCollege());
        holder.tv_book_price.setText(myPostList.getBookPrice());
        holder.tv_seller_name.setText(myPostList.getBookSellerName());
        holder.tv_post_date.setText(myPostList.getPostDate());

        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPostItemListener.onItemViewClicked(myPostArray.get(position));

            }
        });

        holder.btn_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseAuth auth=FirebaseAuth.getInstance();
                FirebaseUser user=auth.getCurrentUser();
                selectPostItemListener.onItemTakeClicked(myPostArray.get(position),user);


            }
        });


    }

    @Override
    public int getItemCount() {

        return myPostArray.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {


        TextView tv_book_name,tv_book_college,tv_book_price,tv_seller_name,tv_post_date;
        ImageView img;
        AppCompatButton btn_take,btn_view;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_book_name=itemView.findViewById(R.id.book_name);
            img=itemView.findViewById(R.id.book_img);
            tv_book_college=itemView.findViewById(R.id.tv_book_college);
            tv_book_price=itemView.findViewById(R.id.tv_price);
            tv_seller_name=itemView.findViewById(R.id.book_seller);
            tv_post_date=itemView.findViewById(R.id.tv_date);
            btn_view=itemView.findViewById(R.id.btn_view);
            btn_take=itemView.findViewById(R.id.btn_take);

        }


    }


}
