package com.example.zunes;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zunes.Model.Post;

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

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        final Post post = postList.get(position);

        holder.songinfo.setText(post.getSongInfo());
        holder.username.setText(post.getUsername());
        Glide.with(holder.itemView.getContext()).load(post.getAlbumCover()).into(holder.albumCover);
        holder.description.setText(post.getDescription());
        holder.webView.loadData(post.getWebView(),"text/html", null);

        holder.webView.setInitialScale(1);
        holder.webView.setWebChromeClient(new WebChromeClient());
        holder.webView.getSettings().setAllowFileAccess(true);
        holder.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        holder.webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        holder.webView.setWebViewClient(new WebViewClient());
        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.getSettings().setLoadWithOverviewMode(true);
        holder.webView.getSettings().setUseWideViewPort(true);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;


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
        private WebView webView;
        //private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songinfo = itemView.findViewById(R.id.songInfo);
            username = itemView.findViewById(R.id.userName);
            albumCover = itemView.findViewById(R.id.albumCover);
            description = itemView.findViewById(R.id.description);
            webView = itemView.findViewById(R.id.postPlayer);
        }
    }
}
