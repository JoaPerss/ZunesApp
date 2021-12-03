package com.example.zunes.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int i) {
            return new Song[i];
        }
    };

    private String songName;
    private String albumCover;
    private String webView;


    public Song(String songName, String albumCover, String webView) {
        this.songName = songName;
        this.albumCover = albumCover;
        this.webView = webView;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    public String getWebView() {
        return webView;
    }

    public void setWebView(String webView) {
        this.webView = webView;
    }

    public Song(Parcel in) {
        this.songName = in.readString();
        this.albumCover = in.readString();
        this.webView = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.songName);
        parcel.writeString(this.albumCover);
        parcel.writeString(this.webView);
    }
}

