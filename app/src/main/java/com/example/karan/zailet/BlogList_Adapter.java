package com.example.karan.zailet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Karan on 08-06-2017.
 */

public class BlogList_Adapter extends RecyclerView.Adapter<BlogList_Adapter.BlogHolder> {

    private Context mContext;
    private ArrayList<Blog> blogList;


    public class BlogHolder extends RecyclerView.ViewHolder{
        public ImageView coverImageView;
        public TextView titleTextView;
        public TextView descriptionTextView;

        public BlogHolder(View itemView) {
            super(itemView);
            coverImageView = (ImageView) itemView.findViewById(R.id.image);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);

        }
    }

    public BlogList_Adapter(Context mContext, ArrayList<Blog> blogList) {
        this.mContext = mContext;
        this.blogList = blogList;


    }

    @Override
    public BlogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_blog, parent, false);
        return new BlogHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BlogHolder holder, int position) {
        Blog blog = blogList.get(position);
        holder.titleTextView.setText(blog.getTitle());

        holder.descriptionTextView.setText(blog.getDescription());

        // loading album cover using Glide library
        Picasso.with(mContext).load("http://zailet.com/"+blog.getThumbnail()).into(holder.coverImageView);

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
