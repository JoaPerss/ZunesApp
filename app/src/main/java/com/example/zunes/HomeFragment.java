package com.example.zunes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import java.util.Objects;


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
        fireStoreListenerReg = postCollectionReference.addSnapshotListener((value, error) -> {
            if (error != null){
                Log.w(TAG, "Listen failed.", error);
                return;
            }
            assert value != null;
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

    public void addPostToPostList(Post post){
        firestoreDb = FirebaseFirestore.getInstance();
        postCollectionReference = firestoreDb.collection("posts");

        postCollectionReference.add(post);
    }

    private void fabAction() {
        final FloatingActionButton fab = requireView().findViewById(R.id.postFab);
        fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_postFragment));
    }
    private void toolbar() {
        Toolbar toolbar = requireView().findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Zunes");
    }

}