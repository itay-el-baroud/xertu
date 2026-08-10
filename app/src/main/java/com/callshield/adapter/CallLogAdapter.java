package com.callshield.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.callshield.R;
import com.callshield.data.CallLogEntry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.ViewHolder> {
    private List<CallLogEntry> list;

    public CallLogAdapter(List<CallLogEntry> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_log, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CallLogEntry item = list.get(position);
        holder.phoneText.setText(item.phoneNumber);
        holder.typeText.setText(item.type);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        holder.timeText.setText(sdf.format(new Date(item.attemptTime)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView phoneText, typeText, timeText;
        ViewHolder(View itemView) {
            super(itemView);
            phoneText = itemView.findViewById(R.id.phoneText);
            typeText = itemView.findViewById(R.id.typeText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }
                                              }
