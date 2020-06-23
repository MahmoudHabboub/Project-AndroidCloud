package com.example.coronavirusproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter_top_country_recyclerview extends RecyclerView.Adapter<top_country_viewholder> {
    ArrayList<Country> topCountry;
    int item_top_country;
    Context applicationContext;

    public adapter_top_country_recyclerview(ArrayList<Country> topCountry, int item_top_country, Context applicationContext) {
        this.topCountry=topCountry;
        this.item_top_country=item_top_country;
        this.applicationContext=applicationContext;
    }

    @NonNull
    @Override
    public top_country_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(item_top_country,parent,false);
        top_country_viewholder country_viewholder=new top_country_viewholder(v);
        return country_viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull top_country_viewholder holder, int position) {
        Country country=topCountry.get(position);
        holder.name_country.setText(country.getName());
        holder.injury_cases_country.setText(country.getCases());
        holder.death_cases_country.setText(country.getDeaths());
        holder.recovery_cases_country.setText(country.getRecovered());

    }

    @Override
    public int getItemCount() {
        return topCountry.size();
    }
}

class top_country_viewholder extends RecyclerView.ViewHolder {
    TextView name_country;
    TextView injury_cases_country;
    TextView death_cases_country;
    TextView recovery_cases_country;

    public top_country_viewholder(@NonNull View itemView) {
        super(itemView);
        name_country=itemView.findViewById(R.id.name_country);
        injury_cases_country=itemView.findViewById(R.id.injury_cases_country);
        death_cases_country=itemView.findViewById(R.id.death_cases_country);
        recovery_cases_country=itemView.findViewById(R.id.recovery_cases_country);

    }
}
