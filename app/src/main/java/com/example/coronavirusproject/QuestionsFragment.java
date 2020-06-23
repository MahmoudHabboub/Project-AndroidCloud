package com.example.coronavirusproject;

import android.icu.text.Edits;
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

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class QuestionsFragment extends Fragment {

    FirebaseFirestore db;
    ArrayList<Questions> questions;
    RecyclerView questions_recyclerview;
    adapter_questions_recyclerview adapterQuestionsRecyclerview;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        questions_recyclerview=getActivity().findViewById(R.id.questions_recyclerview);
        questions=new ArrayList<Questions>();

        adapterQuestionsRecyclerview=new adapter_questions_recyclerview(questions,R.layout.item_questions,getActivity().getApplicationContext());
        questions_recyclerview.setAdapter(adapterQuestionsRecyclerview);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        questions_recyclerview.setLayoutManager(linearLayoutManager);


        db.collection("questions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Iterator<QueryDocumentSnapshot> iterator=queryDocumentSnapshots.iterator();
                        while (iterator.hasNext()){
                            Questions q=iterator.next().toObject(Questions.class);
                            questions.add(q);
                            adapterQuestionsRecyclerview.notifyDataSetChanged();
                        }
                    }
                });
    }

    public QuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questions, container, false);
    }
}
