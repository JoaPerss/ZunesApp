package com.example.zunes;

import android.graphics.Bitmap;

public class Post {
    private String songInfo;
    private String username;
    private Bitmap albumCover;
    private String description;
    private String webView;

    public Post(String songInfo, String username, Bitmap albumCover, String description, String webView) {
        this.songInfo = songInfo;
        this.username = username;
        this.albumCover = albumCover;
        this.description = description;
        this.webView = webView;
    }



    public String getSongInfo() {
        return songInfo;
    }

    public void setSongInfo(String songInfo) {
        this.songInfo = songInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(Bitmap albumCover) {
        this.albumCover = albumCover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebView() {
        return webView;
    }

    public void setWebView(String webView) {
        this.webView = webView;
    }
}
