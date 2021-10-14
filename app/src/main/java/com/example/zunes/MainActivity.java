package com.example.zunes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postList = new ArrayList<>();
        //prepareMockPosts();
        thread.start();
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerViewAdapter = new RecyclerViewAdapter(postList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);

    }


//https://i.scdn.co/image/ab67616d0000b273fcc1ef4b803dd2a5acecf42d

    Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        try {
                Post post = new Post("Passion - PinkPantheress", "@joaperss", "https://i.scdn.co/image/ab67616d0000b273fcc1ef4b803dd2a5acecf42d", "Denne går på repeat", "d");
                postList.add(post);
                post = new Post("family ties - baby keem", "@joaperss", "https://i.scdn.co/image/ab67616d0000b273fcc1ef4b803dd2a5acecf42d", "diditagainanddiditagain", "w");
                postList.add(post);
                post = new Post("Bislett Stadion - Oslo Ess", "@joaperss", "https://i.scdn.co/image/ab67616d0000b273fcc1ef4b803dd2a5acecf42d", "Klassiker", "w");
                postList.add(post);
                post = new Post("Kids - MGMT", "@joaperss", "https://i.scdn.co/image/ab67616d0000b273fcc1ef4b803dd2a5acecf42d", "Taler for seg selv", "w");
                postList.add(post);
                post = new Post("Moon - Kanye West", "@joaperss", "https://i.scdn.co/image/ab67616d0000b273fcc1ef4b803dd2a5acecf42d", "Don Toliver leverer", "w");
                postList.add(post);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
});

}