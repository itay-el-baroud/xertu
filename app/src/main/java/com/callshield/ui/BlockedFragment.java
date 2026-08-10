package com.callshield.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.callshield.R;
import com.callshield.adapter.BlockedNumberAdapter;
import com.callshield.data.AppDatabase;
import com.callshield.data.BlockedNumber;
import com.callshield.service.UnblockWorker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BlockedFragment extends Fragment {
    private RecyclerView recyclerView;
    private BlockedNumberAdapter adapter;
    private EditText searchEdit;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blocked, container, false);
        recyclerView = v.findViewById(R.id.recyclerBlocked);
        searchEdit = v.findViewById(R.id.searchEdit);
        FloatingActionButton fab = v.findViewById(R.id.fabAdd);
        db = AppDatabase.getInstance(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadList("");

        fab.setOnClickListener(view -> showAddDialog());

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { loadList(s.toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });
        return v;
    }

    private void loadList(String query) {
        List<BlockedNumber> list;
        if (query.isEmpty()) list = db.blockedDao().getAll();
        else list = db.blockedDao().search(query);
        adapter = new BlockedNumberAdapter(list, number -> {
            db.blockedDao().delete(number);
            loadList(searchEdit.getText().toString());
        });
        recyclerView.setAdapter(adapter);
    }

    private void showAddDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_number, null);
        EditText phoneInput = dialogView.findViewById(R.id.phoneInput);
        Spinner categorySpinner = dialogView.findViewById(R.id.categorySpinner);
        Spinner durationSpinner = dialogView.findViewById(R.id.durationSpinner);

        String[] cats = {"spam", "annoying", "work", "personal"};
        categorySpinner.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, cats));
        String[] durations = {"Permanent", "1 Hour", "1 Day", "1 Week"};
        durationSpinner.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, durations));

        new AlertDialog.Builder(requireContext())
               .setTitle("Add Number")
               .setView(dialogView)
               .setPositiveButton("Block", (d, w) -> {
                    String phone = phoneInput.getText().toString().trim();
                    if (phone.isEmpty()) {
                        Toast.makeText(requireContext(), "Enter number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String cat = (String) categorySpinner.getSelectedItem();
                    BlockedNumber bn = new BlockedNumber(phone, cat);
                    int durPos = durationSpinner.getSelectedItemPosition();
                    if (durPos == 1) bn.expiresAt = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1);
                    else if (durPos == 2) bn.expiresAt = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);
                    else if (durPos == 3) bn.expiresAt = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7);
                    db.blockedDao().insert(bn);
                    if (bn.expiresAt!= null) {
                        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(UnblockWorker.class)
                               .setInitialDelay(durPos == 1?1:durPos==2?24:168, TimeUnit.HOURS)
                               .build();
                        WorkManager.getInstance(requireContext()).enqueue(req);
                    }
                    loadList("");
                })
               .setNegativeButton("Cancel", null)
               .show();
    }
          }
