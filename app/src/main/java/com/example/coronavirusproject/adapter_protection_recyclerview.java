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

public class adapter_protection_recyclerview  extends RecyclerView.Adapter<protection_viewholder> {
    ArrayList<String> Url_protection_img;
    int item_protection;
    Context applicationContext;

    public adapter_protection_recyclerview(ArrayList<String> Url_protection_img,int item_protection,Context applicationContext){
        this.Url_protection_img=Url_protection_img;
        this.item_protection=item_protection;
        this.applicationContext=applicationContext;
    }

    @NonNull
    @Override
    public protection_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(item_protection,parent,false);
        protection_viewholder protectionViewholder =new protection_viewholder(v);
        return protectionViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull protection_viewholder holder, int position) {
        String url=Url_protection_img.get(position);
        Picasso.get().load(url).into(holder.protection_img);
    }

    @Override
    public int getItemCount() {
        return Url_protection_img.size();
    }
}

class protection_viewholder extends RecyclerView.ViewHolder {
    ImageView protection_img;
    public protection_viewholder(@NonNull View itemView) {
        super(itemView);
        protection_img=itemView.findViewById(R.id.protection_img);
    }
}