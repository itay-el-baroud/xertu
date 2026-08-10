package com.callshield.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.callshield.R;
import com.callshield.data.BlockedNumber;
import java.util.List;

public class BlockedNumberAdapter extends RecyclerView.Adapter<BlockedNumberAdapter.ViewHolder> {

    private List<BlockedNumber> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDelete(BlockedNumber number);
    }

    public BlockedNumberAdapter(List<BlockedNumber> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blocked_number, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BlockedNumber item = list.get(position);
        holder.phoneText.setText(item.phoneNumber);
        holder.categoryText.setText(item.category);
        holder.countText.setText("Attempts: " + item.attemptsCount);
        holder.deleteText.setOnClickListener(view -> {
            if (listener!= null) listener.onDelete(item);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView phoneText, categoryText, countText, deleteText;
        ViewHolder(View itemView) {
            super(itemView);
            phoneText = itemView.findViewById(R.id.phoneText);
            categoryText = itemView.findViewById(R.id.categoryText);
            countText = itemView.findViewById(R.id.countText);
            deleteText = itemView.findViewById(R.id.deleteText);
        }
    }
}
