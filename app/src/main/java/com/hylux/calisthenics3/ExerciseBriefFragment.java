package com.hylux.calisthenics3;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExerciseBriefFragment extends Fragment {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private Exercise exercise;

    private ArrayList<Uri> imagePaths;

    public ExerciseBriefFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.exercise = DatabaseInterface.exerciseList.get(getArguments().getString("EXTRA_ID"));
            Log.d("EXERCISE", exercise.toString());
            Log.d("EXERCISE_LIST", DatabaseInterface.exerciseList.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exercise_brief, container, false);

        imagePaths = new ArrayList<>();

        viewPager = rootView.findViewById(R.id.gallery);
        pagerAdapter = new GalleryPagerAdapter();
        viewPager.setAdapter(pagerAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        if (exercise.getPathsToImages() != null || exercise.getPathsToImages().size() != 0) {
            for (String pathRef : exercise.getPathsToImages()) {
                StorageReference file = storageReference.child(pathRef);
                file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("STORAGE_REF", "SUCCESS");
                        imagePaths.add(uri);
                        Log.d("STORAGE_REF", uri.toString());
                        pagerAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        return rootView;
    }

    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imagePaths.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            Picasso.get().load(imagePaths.get(position)).into(imageView);

            container.addView(imageView);
            return imageView;
        }
    }

    public static ExerciseBriefFragment newInstance(String id) {
        ExerciseBriefFragment fragment = new ExerciseBriefFragment();

        Bundle args = new Bundle();
        args.putString("EXTRA_ID", id);
        fragment.setArguments(args);

        return fragment;
    }
}
