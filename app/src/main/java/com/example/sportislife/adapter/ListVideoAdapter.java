package com.example.sportislife.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportislife.R;
import com.example.sportislife.repository.model.Weight;
import com.example.sportislife.ui.video.VideoModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListVideoAdapter extends RecyclerView.Adapter<ListVideoAdapter.MyViewHolder> {

    Context context;
    ArrayList<VideoModel> videoModels;

    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public ListVideoAdapter(Context context, ArrayList<VideoModel> videoModels) {
        this.context = context;
        this.videoModels = videoModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_video_list, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VideoModel currentItem = videoModels.get(position);
        holder.textView1.setText(currentItem.getChannelTitle());
        holder.textView.setText(currentItem.getTitle());
        Glide.with(context).load(currentItem.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return videoModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView1;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView,onItemClickListener listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.row_title);
            textView1 = itemView.findViewById(R.id.row_discription);
            imageView = itemView.findViewById(R.id.row_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
