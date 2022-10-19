package com.example.btgkapptonghop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemRecycleAdapter extends RecyclerView.Adapter<ItemRecycleAdapter.ViewHolder> {

    ArrayList<Item> dataitems;
    Context context;

    public ItemRecycleAdapter(ArrayList<Item> dataitems, Context context) {
        this.dataitems = dataitems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.hoTen.setText(dataitems.get(position).getHoTen());
        holder.gioiThieu.setText(dataitems.get(position).getGioiThieu());
//        holder.logo.setImageResource(dataitems.get(position).getLogo());
        if (dataitems.get(position).getLogo() == 0)
        {
            holder.logo.setImageURI(dataitems.get(position).getLinkimg());
        }
        else {
            holder.logo.setImageResource(dataitems.get(position).getLogo());
        }
    }

    @Override
    public int getItemCount() {
        return dataitems.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        TextView hoTen,gioiThieu ;
        ImageFilterView logo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logoava);
            hoTen = itemView.findViewById(R.id.textView2);
            gioiThieu = itemView.findViewById(R.id.textView3);
        }
    }
}
