package com.example.bookexchange.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookexchange.R;
import com.example.bookexchange.models.Slides;

import java.util.List;

public class MyPagerAdapter extends RecyclerView.Adapter<MyPagerAdapter.ViewHolder> {

    private List<Slides> slides;
    private Context context;

    public MyPagerAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_slides, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(slides.get(position).getImg());
        holder.title.setText(slides.get(position).getTitle());
        holder.desc.setText(slides.get(position).getDesc());


    }

    @Override
    public int getItemCount() {
        return slides.size();
    }

    public void setSlides(List<Slides> slides) {
        this.slides = slides;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);


        }


    }
}