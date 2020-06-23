package com.example.coronavirusproject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ListUsersFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference parentReference;
    DatabaseReference usersReference;
    RecyclerView recyclerView;
    AdapterListUsers adapterListUsers;
    Toolbar toolbar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView=getActivity().findViewById(R.id.reyclerview_user_list);
        auth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        parentReference = firebaseDatabase.getReference();
        usersReference = parentReference.child("users");
        toolbar=getActivity().findViewById(R.id.toolbar_mes);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        FirebaseUser user=auth.getCurrentUser();
        String CuruserName=user.getEmail();
        String CuruserUid= user.getUid();

        @SuppressLint("StaticFieldLeak") AsyncTask<String,User, ArrayList<User>> t=new AsyncTask<String, User, ArrayList<User>>() {
            ArrayList<User>userArrayList=new ArrayList<>();
            @Override
            protected ArrayList<User> doInBackground(final String... strings) {
                usersReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User e = snapshot.getValue(User.class);
                            if(!e.getUid().equalsIgnoreCase(strings[0])){
                                publishProgress(e);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return userArrayList;
            }

            @Override
            protected void onProgressUpdate(User... values) {
                super.onProgressUpdate(values);
                userArrayList.add(values[0]);
                adapterListUsers.notifyDataSetChanged();

            }

            @Override
            protected void onPostExecute(ArrayList<User> users) {
                super.onPostExecute(users);
                if(users !=null){
                    adapterListUsers=new AdapterListUsers(users,getActivity().getApplicationContext(),R.layout.item);
                    recyclerView.setAdapter(adapterListUsers);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }
            }
        };
        t.execute(CuruserUid);
    }

    public ListUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_users, container, false);
    }
}
