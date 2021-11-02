package com.example.zunes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zunes.Model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private List<Post> postList;
    private List<String> postIdList;

    private static final String TAG = HomeFragment.class.getSimpleName();

    private FirebaseFirestore firestoreDb;
    private CollectionReference postCollectionReference;
    private ListenerRegistration fireStoreListenerReg;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postList = new ArrayList<>();
        postIdList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerViewAdapter = new RecyclerViewAdapter(postList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);

        firestoreDb = FirebaseFirestore.getInstance();
        postCollectionReference = firestoreDb.collection("posts");
        //thread.start();

        toolbar();
        fabAction();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (fireStoreListenerReg != null)
            fireStoreListenerReg.remove();
    }

    @Override
    public void onResume() {
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
        fireStoreListenerReg = postCollectionReference.addSnapshotListener((value, error) -> {
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
        });
    }

    private void fabAction() {
        final FloatingActionButton fab = getView().findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            //Navigation.findNavController(view).navigate(R.id.);
            Intent intent = new Intent(getActivity().getApplicationContext(),SecondActivity.class);
            startActivity(intent);
        });
    }
    private void toolbar() {
        Toolbar toolbar;
        toolbar = getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Zunes");
    }

}