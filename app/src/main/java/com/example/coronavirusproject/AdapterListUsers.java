package com.example.coronavirusproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterListUsers extends RecyclerView.Adapter<ListUsers_VH> {
    ArrayList<User> UserArrayList;
    Context context;
    int id;
    public AdapterListUsers(ArrayList<User> UserArrayList,Context context,int id){
        this.UserArrayList=UserArrayList;
        this.context=context;
        this.id=id;
    }

    @NonNull
    @Override
    public ListUsers_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(id,parent,false);
        ListUsers_VH listUsers_vh=new ListUsers_VH(view);
        return listUsers_vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListUsers_VH holder, int position) {
        final User u= UserArrayList.get(position);
        holder.Address.setText(u.getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context,ListMessagesActivity.class);
                i.putExtra("User", u);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return UserArrayList.size();
    }
}

class ListUsers_VH extends RecyclerView.ViewHolder {
    TextView Address;
    public ListUsers_VH(@NonNull View itemView) {
        super(itemView);
        Address=itemView.findViewById(R.id.address);
    }
}
