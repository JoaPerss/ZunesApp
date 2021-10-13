package com.example.zunes;
import com.example.zunes.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postList = new ArrayList<>();
        prepareMockPosts();
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerViewAdapter = new RecyclerViewAdapter(postList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);

    }
    private void prepareMockPosts(){
        Post post = new Post("Passion - PinkPantheress", "@joaperss", converter("d"), "Denne går på repeat", "d");
        postList.add(post);
        post = new Post("family ties - baby keem", "@joaperss", "albumCover", "diditagainanddiditagain", "w");
        postList.add(post);
        post = new Post("Bislett Stadion - Oslo Ess", "@joaperss", "albumCover", "Klassiker", "w");
        postList.add(post);
        post = new Post("Kids - MGMT", "@joaperss", "albumCover", "Taler for seg selv", "w");
        postList.add(post);
        post = new Post("Moon - Kanye West", "@joaperss", "albumCover", "Don Toliver leverer", "w");
        postList.add(post);
    }
    private String converter(String img){

        return "";
    }
}