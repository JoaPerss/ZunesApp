package com.example.zunes;

import static com.spotify.sdk.android.auth.AccountsQueryParameters.CLIENT_ID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NavController controller = Navigation.findNavController(this, R.id.navHostFragment);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigation,controller);

        auth = FirebaseAuth.getInstance();

        createAuthStateListener();

    }

    private void createAuthStateListener() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser == null) {
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.PhoneBuilder().build());

                    signInLauncher.launch(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build());

                }
                else {
                    Toast.makeText(getApplicationContext(), "Signed in as " + currentUser.getDisplayName(), Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        auth.removeAuthStateListener(authStateListener);
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        if (result.getResultCode() == RESULT_OK) {
            FirebaseUser currentUser = auth.getCurrentUser();
            assert currentUser != null;
            Toast.makeText(getApplicationContext(), "Signed in as " + currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Signed in cancelled", Toast.LENGTH_LONG).show();
        }
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