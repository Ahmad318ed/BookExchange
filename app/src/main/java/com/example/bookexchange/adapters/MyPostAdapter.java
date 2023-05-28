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
import com.example.bookexchange.dao.DAOPost;
import com.example.bookexchange.models.Post;
import com.example.bookexchange.ui.fragments.MyPostsFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder> {

    Context context;
    List<Post> myPostArray;
    DAOPost daoPost;


    SelectPostItemListener selectPostItemListener;

    public MyPostAdapter() {
    }

    public MyPostAdapter(Context context, List<Post> myPostArray, SelectPostItemListener selectPostItemListener) {

        this.context = context;
        this.myPostArray = myPostArray;
        this.selectPostItemListener = selectPostItemListener;
    }


    public void setfilteredlist(List<Post> filteredList) {

        this.myPostArray = filteredList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.mypost_item, parent, false);
        MyPostViewHolder viewHolder = new MyPostViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostViewHolder holder, int position) {

        final Post myPostList = myPostArray.get(position);


        daoPost = new DAOPost();
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

                                daoPost.remove(myPostList.getPostID()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(context, "The Post has been deleted", Toast.LENGTH_SHORT).show();

                                    }
                                });

                                myPostArray.remove(position);
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

        return myPostArray.size();
    }

    public class MyPostViewHolder extends RecyclerView.ViewHolder {


        TextView tv_book_name, tv_book_college, tv_book_price, tv_seller_name, tv_post_date;
        ImageView img;
        AppCompatButton btn_delete, btn_view;


        public MyPostViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_book_name = itemView.findViewById(R.id.book_name_myPosts);
            img = itemView.findViewById(R.id.book_img_myPosts);
            tv_book_college = itemView.findViewById(R.id.tv_book_college_myPosts);
            tv_book_price = itemView.findViewById(R.id.tv_price_myPosts);
            tv_seller_name = itemView.findViewById(R.id.book_seller_myPosts);
            tv_post_date = itemView.findViewById(R.id.tv_date_myPosts);
            btn_view = itemView.findViewById(R.id.btn_view_myPosts);
            btn_delete = itemView.findViewById(R.id.btn_delete_myPosts);

        }


    }


}
