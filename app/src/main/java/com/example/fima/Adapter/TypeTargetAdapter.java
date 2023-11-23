package com.example.fima.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fima.R;
import com.example.fima.models.ListTypeTarget;

import java.util.ArrayList;

public class TypeTargetAdapter extends RecyclerView.Adapter<TypeTargetAdapter.viewholder> {
    ArrayList<ListTypeTarget> items;
    Context context;

    public TypeTargetAdapter(ArrayList<ListTypeTarget> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public TypeTargetAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_type, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeTargetAdapter.viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        int drawableResourceId = holder.itemView.getResources()
                .getIdentifier(items.get(position).getImgpath(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView img;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            img = itemView.findViewById(R.id.img_type);
        }
    }
}
