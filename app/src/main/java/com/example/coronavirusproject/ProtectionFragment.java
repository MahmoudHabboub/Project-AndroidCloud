package com.example.coronavirusproject;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ProtectionFragment extends Fragment {


    static int protection_img_count=6;
    static String Name_video="video1.mp4";
    RecyclerView protection_recyclerview;
    FirebaseStorage firebaseStorage;
    StorageReference storageRef;
    ArrayList<String> Url_protection_img;
    VideoView videoView;
    Uri videoUri;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("ttt","onActivityCreated");
        Url_protection_img=new ArrayList<>();

        firebaseStorage = FirebaseStorage.getInstance("gs://coronavirusproject-daf25.appspot.com");
        storageRef = firebaseStorage.getReference();

        protection_recyclerview=getActivity().findViewById(R.id.protection_recyclerview);


        videoView=getActivity().findViewById(R.id.videoView);

        storageRef.child("/videos/"+Name_video).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String path = uri.toString();
                videoUri=Uri.parse(path);
                videoView.setVideoURI(videoUri);
                videoView.requestFocus();
                MediaController mediaController=new MediaController(getContext());
                videoView.setMediaController(mediaController);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("ddd",exception.getMessage());
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=protection_img_count;i++){
                    final int j=i;
                    storageRef.child("/images/protection/protection"+i+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String path = uri.toString();
//                    Log.d("ddd",path);
                            Url_protection_img.add(path);
                            if(j==protection_img_count){
                                adapter_protection_recyclerview adapterProtectionRecyclerview=new adapter_protection_recyclerview(Url_protection_img,R.layout.item_protection,getActivity().getApplicationContext());
                                protection_recyclerview.setAdapter(adapterProtectionRecyclerview);
                                GridLayoutManager gridLayoutManager =new GridLayoutManager(getActivity().getApplicationContext(),2);
                                protection_recyclerview.setLayoutManager(gridLayoutManager);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("ddd",exception.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    public ProtectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_protection, container, false);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            Log.d("ttt","setMenuVisibility");
        }else {

        }
    }
}
