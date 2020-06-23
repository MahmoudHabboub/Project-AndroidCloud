package com.example.coronavirusproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter_questions_recyclerview extends RecyclerView.Adapter<questions_viewholder> {
    ArrayList<Questions> questions;
    int item_questions;
    Context applicationContext;

    public adapter_questions_recyclerview(ArrayList<Questions> questions,int item_questions,Context applicationContext){
        this.questions=questions;
        this.item_questions=item_questions;
        this.applicationContext=applicationContext;
    }
    @NonNull
    @Override
    public questions_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(item_questions,parent,false);
        questions_viewholder questionsViewholder =new questions_viewholder(v);
        return questionsViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull questions_viewholder holder, int position) {
        int p=position+1;
        if(p==1){
//            setMargins(holder.itemView,0,820,0,0);
        }else{

        }
        Questions question=questions.get(position);
        holder.count_question.setText(p+"");
        holder.question.setText(question.getQuestion());
        holder.answer.setText(question.getAnswer());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}


class questions_viewholder extends RecyclerView.ViewHolder {
    TextView question;
    TextView answer;
    TextView count_question;
    public questions_viewholder(@NonNull View itemView) {
        super(itemView);
        question=itemView.findViewById(R.id.question);
        answer=itemView.findViewById(R.id.answer);
        count_question=itemView.findViewById(R.id.count_question);
    }
}