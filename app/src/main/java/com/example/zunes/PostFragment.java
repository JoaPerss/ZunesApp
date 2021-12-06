package com.example.zunes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.zunes.Model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.Objects;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Result;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PostFragment extends Fragment {
    public PostFragment() {
        // Required empty public constructor
    }
    FloatingActionButton postFab;
    EditText editTextSongName;
    EditText editTextDescription;

    private FirebaseAuth auth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /// Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar();
        userInput(view);
    }

    private void userInput(@NonNull View view) {
        postFab = view.findViewById(R.id.postFab);
        editTextSongName = view.findViewById(R.id.searchForSong);
        editTextDescription = view.findViewById(R.id.editTextDescription);


        postFab.setOnClickListener(view1 -> {
            String songName = editTextSongName.getText().toString();
            spotifySearch(songName);

        });
    }

        private void spotifySearch(String songName) {
            SpotifyService spotify = getSpotifyService();

            spotify.searchTracks(songName, new Callback<TracksPager>() {
            @Override
            public void success(TracksPager tracksPager, Response response) {
                Track item = tracksPager.tracks.items.get(0);

                String fullSong = item.artists.get(0).name + " - " + item.name;
                String albumCover = item.album.images.get(0).url;
                String webViewId = item.id;
                String webView = "<iframe \n" + "src=\"https://open.spotify.com/embed/track/\n"+webViewId+"?theme=0\" width=\"100%\" height=\"80\" frameBorder=\"0\" allowfullscreen=\"\" allow=\"autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture\"></iframe>";

                response.toString();
                Log.d("Fant den", response.getReason());
                Log.d("FULL TRACK", fullSong);

                String description = editTextDescription.getText().toString();

                //Simulate username by converting to lowercase and remove spacing between names
                auth = FirebaseAuth.getInstance();
                String displayName = auth.getCurrentUser().getDisplayName();
                String username = "@" + displayName.toLowerCase().replace(" ", "");

                Post newPost = new Post(fullSong, username, albumCover, description, webView);

                HomeFragment homeFragment = new HomeFragment();
                homeFragment.addPostToPostList(newPost);

                Snackbar.make(getView(),"Post succsessfully posted, check your feed!", Snackbar.LENGTH_LONG).show();

            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(getView(), "An error occured while searching", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private SpotifyService getSpotifyService() {
        SpotifySplash spotifySplash = new SpotifySplash();
        String authToken = spotifySplash.getAuthToken();
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(authToken);
        return api.getService();
    }

    private void toolbar() {
        Toolbar toolbar;
        toolbar = requireView().findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Zunes");
    }
}