package com.example.zunes;

import static com.spotify.sdk.android.auth.AuthorizationResponse.Type.TOKEN;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class SpotifySplash extends AppCompatActivity {

    private static final String CLIENT_ID = "0e41e2294a1b41379a57d514fc3a1768";
    private static final String REDIRECT_URI = "com.zunes://callback";
    private static final int REQUEST_CODE = 1337;
    private static final String SCOPES = "user-read-recently-played,user-library-modify,user-read-email,user-read-private";
    public static String authToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_spotify_splash);

        authenticateSpotify();

    }
    private void authenticateSpotify(){
        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(CLIENT_ID, TOKEN,REDIRECT_URI);
        builder.setScopes(new String[]{SCOPES});
        AuthorizationRequest request = builder.build();
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    SharedPreferences.Editor editor = getSharedPreferences("SPOTIFY", 0).edit();
                    editor.putString("token", response.getAccessToken());
                    Log.d("STARTING", "GOT AUTH TOKEN");
                    Log.d("TOKEN; ", response.getAccessToken());
                    Toast.makeText(getApplicationContext(),"Spotify Sign in succsessful", Toast.LENGTH_SHORT).show();
                    editor.apply();

                    authToken = response.getAccessToken();
                    setAuthToken(authToken);

                    startMainActivity();
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Log.d("Error", "error");
                    Toast.makeText(getApplicationContext(),"Spotify Sign in unsuccsessful", Toast.LENGTH_SHORT).show();
                    break;

                // Most likely auth flow was cancelled
                default:
                    Log.d("Default", "Auth flow cancelled");
                    Toast.makeText(getApplicationContext(), "Auth flow cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setAuthToken(String authToken) {
        SpotifySplash.authToken = authToken;
    }


    public String getAuthToken() {
        return authToken;
    }

    private void startMainActivity() {
        Intent intent = new Intent(SpotifySplash.this, MainActivity.class);
        startActivity(intent);
    }

}