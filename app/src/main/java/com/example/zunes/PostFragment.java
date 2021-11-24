package com.example.zunes;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PostFragment extends Fragment {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar();

        SpotifyApi api = new SpotifyApi();

// Most (but not all) of the Spotify Web API endpoints require authorisation.
// If you know you'll only use the ones that don't require authorisation you can skip this step
        api.setAccessToken("BQAC0HfeiWIS-4FItdLJcEk3b5I-WSnrwkVbYpyxqaNomSWxqR2QYh_o_D3P2HakGL4rc6RF4j0ioMT5eQWa_-gGqkNO9JOGldFVty88ZD1PG6weXwDpA5nzcnX6qKcPDPqhSE-kAsk");
        //api.setAccessToken();

        SpotifyService spotify = api.getService();

        spotify.getAlbum("2dIGnmEIy1WZIcZCFSj6i8", new Callback<Album>() {

            @Override
            public void success(Album album, Response response) {
                Log.d("Album success", album.name);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Album failure", error.toString());
            }
        });


/*        SpotifyApi api = new SpotifyApi();
        api.setAccessToken("BQAC0HfeiWIS-4FItdLJcEk3b5I-WSnrwkVbYpyxqaNomSWxqR2QYh_o_D3P2HakGL4rc6RF4j0ioMT5eQWa_-gGqkNO9JOGldFVty88ZD1PG6weXwDpA5nzcnX6qKcPDPqhSE-kAsk");

        SpotifyService spotifyService = api.getService();

        spotifyService.getAlbum("2dIGnmEIy1WZIcZCFSj6i8");*/



    }
    private void toolbar() {
        Toolbar toolbar;
        toolbar = requireView().findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Zunes");
    }
}