package com.example.coronavirusproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_contagion_recyclerview extends RecyclerView.Adapter<contagion_viewholder> {
    ArrayList<String> Url_contagion_img;
    int item_contagion;
    Context applicationContext;

    public adapter_contagion_recyclerview(ArrayList<String> Url_contagion_img,int item_contagion,Context applicationContext){
        this.Url_contagion_img=Url_contagion_img;
        this.item_contagion=item_contagion;
        this.applicationContext=applicationContext;
    }

    @NonNull
    @Override
    public contagion_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(item_contagion,parent,false);
        contagion_viewholder contagionViewholder =new contagion_viewholder(v);
        return contagionViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull contagion_viewholder holder, int position) {
        String url=Url_contagion_img.get(position);
        Picasso.get().load(url).into(holder.contagion_img);
    }

    @Override
    public int getItemCount() {
        return Url_contagion_img.size();
    }
}

class contagion_viewholder extends RecyclerView.ViewHolder {
    ImageView contagion_img;
    public contagion_viewholder(@NonNull View itemView) {
        super(itemView);
        contagion_img=itemView.findViewById(R.id.symptoms_contagion_img);
    }
}