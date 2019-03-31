package com.hylux.calisthenics3;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

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
            new GetImageBitmapTask(imageView).execute(imagePaths.get(position).toString());


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

    static class GetImageBitmapTask extends AsyncTask<String, Void, Bitmap> {

        Bitmap bm = null;
        ImageView imageView;

        public GetImageBitmapTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL aURL = new URL(urls[0]);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "Error getting bitmap", e);
            }
            return bm;
        }

        protected void onPostExecute(Bitmap bm) {
            imageView.setImageBitmap(bm);
        }
    }
}
