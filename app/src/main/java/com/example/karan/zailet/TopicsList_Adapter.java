package com.example.karan.zailet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by karan on 8/6/17.
 */

public class TopicsList_Adapter extends RecyclerView.Adapter<TopicsList_Adapter.TopicHolder> {

    Topic topic;
    private Context mContext;
    private ArrayList<Topic> topicList;
    private SparseBooleanArray selectedItems;


    public class TopicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView coverImageView;
        public TextView titleTextView;

        @Override
        public void onClick(View v) {

            // Save the selected positions to the SparseBooleanArray
            if (selectedItems.get(getAdapterPosition(), false)) {
                selectedItems.delete(getAdapterPosition());
//                myBackground.setSelected(false);
            }
            else {
                selectedItems.put(getAdapterPosition(), true);
//                myBackground.setSelected(true);
            }
        }

        public TopicHolder(View itemView) {
            super(itemView);
            coverImageView = (ImageView) itemView.findViewById(R.id.coverImageView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);

        }
    }

    public TopicsList_Adapter(Context mContext, ArrayList<Topic> topicList) {
        this.mContext = mContext;
        this.topicList = topicList;


    }

    @Override
    public TopicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_topics, parent, false);
        return new TopicHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopicHolder holder, int position) {
        topic = topicList.get(position);
        holder.titleTextView.setText(topic.getInterest());

        // loading album cover using Glide library
        Picasso.with(mContext).load("http://zailet.com/"+topic.getCover()).into(holder.coverImageView);

        // Set the selected state of the row depending on the position
//        holder.myBackground.setSelected(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }


}
