package com.example.zunes;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zunes.Model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private List<Post> postList;
    private List<String> postIdList;

    private static final String TAG = MainActivity.class.getSimpleName();

    private FirebaseFirestore firestoreDb;
    private CollectionReference postCollectionReference;
    private ListenerRegistration fireStoreListenerReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postList = new ArrayList<>();
        postIdList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerViewAdapter = new RecyclerViewAdapter(postList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);

        firestoreDb = FirebaseFirestore.getInstance();
        postCollectionReference = firestoreDb.collection("posts");
        //thread.start();

        toolbar();
        fabAction();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (fireStoreListenerReg != null)
            fireStoreListenerReg.remove();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createFireStoreReadListener();
    }

    private void createFireStoreReadListener() {

/*
        postCollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Post post = documentSnapshot.toObject(Post.class);
                        post.setpId(documentSnapshot.getId());
                        postList.add(post);
                        postIdList.add(post.getpId());
                    }
                    recyclerViewAdapter.notifyDataSetChanged();
                }else{
                    Log.d(TAG, "Error getting documents: " + task.getException());
                }

            }
        });
*/

        fireStoreListenerReg = postCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                for (DocumentChange documentChange : value.getDocumentChanges()){
                    QueryDocumentSnapshot documentSnapshot = documentChange.getDocument();
                    Post post = documentSnapshot.toObject(Post.class);
                    post.setpId(documentSnapshot.getId());
                    int pos = postIdList.indexOf(post.getpId());

                    switch (documentChange.getType()){
                        case ADDED:
                            postList.add(post);
                            postIdList.add(post.getpId());
                            recyclerViewAdapter.notifyItemInserted(postList.size()-1);
                            break;
                        case REMOVED:
                            postList.remove(pos);
                            postIdList.remove(pos);
                            recyclerViewAdapter.notifyItemRemoved(pos);
                        case MODIFIED:
                            postList.set(pos, post);
                            recyclerViewAdapter.notifyItemChanged(pos);
                            break;
                    }
                }
            }
        });


    }

    private void fabAction() {
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toolbar() {
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Zunes");
    }

    Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        try {
                Post post = new Post("Passion - PinkPantheress", "@joaperss", "https://i.scdn.co/image/ab67616d0000b273fcc1ef4b803dd2a5acecf42d", "Denne går på repeat", "<iframe src=\"https://open.spotify.com/embed/track/6ZJqCviTotiIujl1rfcL53?theme=0\" width=\"100%\" height=\"80\" frameBorder=\"0\" allowfullscreen=\"\" allow=\"autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture\"></iframe>");
                postList.add(post);
                post = new Post("family ties - baby keem", "@joaperss", "https://i.scdn.co/image/ab67616d0000b273163028c67a1f4a7c0e83a715", "diditagainanddiditagain", "<iframe src=\"https://open.spotify.com/embed/track/7Bpx2vsWfQFBACRz4h3IqH?theme=0\" width=\"100%\" height=\"80\" frameBorder=\"0\" allowfullscreen=\"\" allow=\"autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture\"></iframe>");
                postList.add(post);
                post = new Post("Bislett Stadion - Oslo Ess", "@joaperss", "https://i.scdn.co/image/ab67616d0000b273e2a9957f99b8e907902491a2", "Klassiker", "<iframe src=\"https://open.spotify.com/embed/track/30wOsGGJ89E6LJkvVJKpSm\" width=\"100%\" height=\"80\" frameBorder=\"0\" allowfullscreen=\"\" allow=\"autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture\"></iframe>");
                postList.add(post);
                post = new Post("Kids - MGMT", "@joaperss", "https://i.scdn.co/image/ab67616d0000b2738b32b139981e79f2ebe005eb", "Taler for seg selv", "<iframe src=\"https://open.spotify.com/embed/track/1jJci4qxiYcOHhQR247rEU?theme=0\" width=\"100%\" height=\"80\" frameBorder=\"0\" allowfullscreen=\"\" allow=\"autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture\"></iframe>");
                postList.add(post);
                post = new Post("Freaks - Surf Curse", "@joaperss", "https://i.scdn.co/image/ab67616d0000b2739efda673310de265a2c1cf1f", "vibes", "<iframe src=\"https://open.spotify.com/embed/track/7EkWXAI1wn8Ii883ecd9xr?theme=0\" width=\"100%\" height=\"80\" frameBorder=\"0\" allowfullscreen=\"\" allow=\"autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture\"></iframe>");
                postList.add(post);

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Post post : postList){
            postCollectionReference.add(post);
        }
    }
});

}