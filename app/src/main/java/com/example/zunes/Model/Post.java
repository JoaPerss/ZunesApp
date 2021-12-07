package com.example.zunes.Model;

import com.google.firebase.firestore.Exclude;

public class Post {
    private String pId;
    private String songInfo;
    private String username;
    private String albumCover;
    private String description;
    private String webView;

    public Post(String songInfo, String username, String albumCover, String description, String webView) {
        this.songInfo = songInfo;
        this.username = username;
        this.albumCover = albumCover;
        this.description = description;
        this.webView = webView;
    }

    public Post() {
    }

    @Exclude
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
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
