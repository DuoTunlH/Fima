package com.example.fima.common;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fima.R;
import com.example.fima.models.UserExpense;

public class ExpenseViewHolder extends RecyclerView.ViewHolder{

    Context context;
    TextView name;
    TextView amount;
    UserExpense expense;

    public void setExpense(UserExpense expense) {
        this.expense = expense;
    }

    public ExpenseViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        name = itemView.findViewById(R.id.name);
        amount = itemView.findViewById(R.id.amount);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("movie", movie);
//                context.startActivity(intent);
            }
        });
    }

}
