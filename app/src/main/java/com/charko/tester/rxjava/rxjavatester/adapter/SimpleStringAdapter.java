package com.charko.tester.rxjava.rxjavatester.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SimpleStringAdapter extends RecyclerView.Adapter<SimpleStringAdapter.ViewHolder> {
    private final Context mContext;
    private final List<String> mStrings = new ArrayList<>();

    public SimpleStringAdapter(Context context) {
        mContext = context;
    }

    public void setStrings(List<String> newStrings) {
        mStrings.clear();
        mStrings.addAll(newStrings);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView colorTextView;

        public ViewHolder(View view) {
            super(view);
            colorTextView = (TextView) view.findViewById(android.R.id.text1);
        }
    }

    @NonNull
    @Override
    public SimpleStringAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleStringAdapter.ViewHolder holder, int position) {
        holder.colorTextView.setText(mStrings.get(position));
        holder.itemView.setOnClickListener(view -> {
            Toast.makeText(mContext, mStrings.get(position), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }
}
