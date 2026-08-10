package com.callshield.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.callshield.R;
import com.callshield.adapter.CallLogAdapter;
import com.callshield.data.AppDatabase;
import com.callshield.data.CallLogEntry;
import java.util.List;

public class LogsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logs, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerLogs);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        AppDatabase db = AppDatabase.getInstance(requireContext());
        List<CallLogEntry> logs = db.logDao().getAll();
        recyclerView.setAdapter(new CallLogAdapter(logs));
        return v;
    }
}
