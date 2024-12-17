package com.mirea.kt.ribo.soberdriver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private List<Record> records;

    public RecordAdapter(List<Record> records) {
        this.records = records;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView date;
        TextView result;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView2);
            date = itemView.findViewById(R.id.date);
            result = itemView.findViewById(R.id.result);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Record record = records.get(position);
        DecimalFormat format = new DecimalFormat("#.###");
        double result = 0;
        if (record.getResult() >= 0) {
            result = record.getResult();
        }
        if (result < 0.05) {
            holder.itemView.findViewById(R.id.card).setBackgroundColor(holder.itemView.getResources().getColor(R.color.green_back));
            holder.image.setImageResource(R.drawable.good_result);
        } else if (result >= 0.05 && result < 0.3) {
            holder.itemView.findViewById(R.id.card).setBackgroundColor(holder.itemView.getResources().getColor(R.color.orange_back));
            holder.image.setImageResource(R.drawable.medium_result);
        } else {
            holder.itemView.findViewById(R.id.card).setBackgroundColor(holder.itemView.getResources().getColor(R.color.red_back));
            holder.image.setImageResource(R.drawable.bad_result);
        }
        holder.result.setText(String.valueOf(result));
        holder.date.setText(String.valueOf(record.getTime()));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}