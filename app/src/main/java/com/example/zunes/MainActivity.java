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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zunes.Model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NavController controller = Navigation.findNavController(this, R.id.navHostFragment);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigation,controller);

    }
/*


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
*/

}