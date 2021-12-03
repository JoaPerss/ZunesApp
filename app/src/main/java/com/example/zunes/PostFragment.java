package com.example.zunes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.zunes.Model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Objects;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Result;
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

    public String songInfoSpotify;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /// Inflate the layout for this fragment
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
            String description = editTextDescription.getText().toString();

            String result = spotifySearch(songName);

            MainActivity mainActivity = new MainActivity();
            String username = "John Doe";//mainActivity.getUsernameOfCurrentUser();

            Post newPost = new Post(result, "@" + username, "", description, "");

            HomeFragment homeFragment = new HomeFragment();
            homeFragment.addPostToPostList(newPost);

        });
    }

        private String spotifySearch(String songName) {
            SpotifyService spotify = getSpotifyService();

            final String[] searchResult = new String[1];


            spotify.searchTracks(songName, new Callback<TracksPager>() {
            @Override
            public void success(TracksPager tracksPager, Response response) {
                String fullSong = tracksPager.tracks.items.get(0).artists.get(0).name + " - " + tracksPager.tracks.items.get(0).name;

                response.toString();

                Log.d("Fant den", response.getReason());

                //setSongInfoSpotify(tracksPager.tracks.items.get(0).artists.get(0).name + " - " + tracksPager.tracks.items.get(0).name);

                //setSongInfoSpotify(fullSong);
                searchResult[0] = fullSong;
                setSongInfoSpotify(searchResult[0]);

                Log.d("FULL TRACK", songInfoSpotify);

                Log.d("søkeresultat", Arrays.stream(searchResult).findAny().get());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Fant ikke", error.getMessage());
            }
        });
            return songInfoSpotify;
    }

    private SpotifyService getSpotifyService() {
        SpotifySplash spotifySplash = new SpotifySplash();
        String authToken = spotifySplash.getAuthToken();
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(authToken);
        return api.getService();
    }


    public String getSongInfoSpotify() {
        return songInfoSpotify;
    }

    public void setSongInfoSpotify(String songInfoSpotify) {
        this.songInfoSpotify = songInfoSpotify;
    }

    private void toolbar() {
        Toolbar toolbar;
        toolbar = requireView().findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Zunes");
    }
}