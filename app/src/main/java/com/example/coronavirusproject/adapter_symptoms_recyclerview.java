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

public class adapter_symptoms_recyclerview extends RecyclerView.Adapter<symptoms_viewholder> {
    ArrayList<String> Url_symptoms_img;
    int item_symptoms;
    Context applicationContext;

    public adapter_symptoms_recyclerview(ArrayList<String> Url_symptoms_img,int item_symptoms,Context applicationContext){
        this.Url_symptoms_img=Url_symptoms_img;
        this.item_symptoms=item_symptoms;
        this.applicationContext=applicationContext;
    }
    public symptoms_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(item_symptoms,parent,false);
        symptoms_viewholder symptomsViewholder =new symptoms_viewholder(v);
        return symptomsViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull symptoms_viewholder holder, int position) {
        String url=Url_symptoms_img.get(position);
        Picasso.get().load(url).into( holder.symptoms_img);
    }

    @Override
    public int getItemCount() {
        return Url_symptoms_img.size();
    }
}

class symptoms_viewholder extends RecyclerView.ViewHolder {
    ImageView symptoms_img;
    public symptoms_viewholder(@NonNull View itemView) {
        super(itemView);
        symptoms_img=itemView.findViewById(R.id.symptoms_contagion_img);
    }
}