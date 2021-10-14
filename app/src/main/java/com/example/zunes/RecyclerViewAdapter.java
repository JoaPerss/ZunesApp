package com.example.zunes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Post> postList;

    RecyclerViewAdapter(List<Post> postList){
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        final Post post = postList.get(position);

        holder.songinfo.setText(post.getSongInfo());
        holder.username.setText(post.getUsername());
        Glide.with(holder.itemView.getContext()).load(post.getAlbumCover()).into(holder.albumCover);
        holder.description.setText(post.getDescription());

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView songinfo;
        private TextView username;
        private ImageView albumCover;
        private TextView description;
        //private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songinfo = itemView.findViewById(R.id.songInfo);
            username = itemView.findViewById(R.id.userName);
            albumCover = itemView.findViewById(R.id.albumCover);
            description = itemView.findViewById(R.id.description);
//            cardView = itemView.findViewById(R.id.carView);
        }
    }
}
