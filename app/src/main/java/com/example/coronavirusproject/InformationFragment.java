package com.example.coronavirusproject;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class InformationFragment extends Fragment {
    static int symptoms_img_count=4;
    static int contagion_img_count=4;
    RecyclerView symptoms_recyclerview;
    RecyclerView contagion_recyclerview;
    FirebaseStorage firebaseStorage;
    StorageReference storageRef;
    ArrayList<String> Url_symptoms_img;
    ArrayList<String>Url_contagion_img;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        symptoms_recyclerview=getActivity().findViewById(R.id.symptoms_recyclerview);
        contagion_recyclerview=getActivity().findViewById(R.id.contagion_recyclerview);
        Url_symptoms_img=new ArrayList<>();
        Url_contagion_img=new ArrayList<>();

        firebaseStorage = FirebaseStorage.getInstance("gs://coronavirusproject-daf25.appspot.com");
        storageRef = firebaseStorage.getReference();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=symptoms_img_count;i++){
                    final int j=i;
                    storageRef.child("/images/symptoms/symptoms"+i+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String path = uri.toString();
//                    Log.d("ddd",path);
                            Url_symptoms_img.add(path);
                            if(j==symptoms_img_count){
                                adapter_symptoms_recyclerview adapterSymptomsRecyclerview=new adapter_symptoms_recyclerview(Url_symptoms_img,R.layout.item_symptoms,getActivity().getApplicationContext());
                                symptoms_recyclerview.setAdapter(adapterSymptomsRecyclerview);
                                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity().getApplicationContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                linearLayoutManager.setReverseLayout(false);
                                symptoms_recyclerview.setLayoutManager(linearLayoutManager);
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=contagion_img_count;i++){
                    final int j=i;
                    storageRef.child("/images/contagion/contagion"+i+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String path = uri.toString();
//                    Log.d("ddd",path);
                            Url_contagion_img.add(path);
                            if(j==contagion_img_count){
                                adapter_contagion_recyclerview adapterContagionRecyclerview=new adapter_contagion_recyclerview(Url_contagion_img,R.layout.item_symptoms,getActivity().getApplicationContext());
                                contagion_recyclerview.setAdapter(adapterContagionRecyclerview);
                                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL, true);
                                contagion_recyclerview.setLayoutManager(linearLayoutManager);
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

    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information, container, false);
    }
}
