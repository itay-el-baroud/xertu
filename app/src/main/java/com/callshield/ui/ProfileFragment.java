package com.callshield.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.callshield.LoginActivity;
import com.callshield.R;
import com.callshield.data.TokenManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class ProfileFragment extends Fragment {
    private TextView profileText;
    private ProgressBar progressBar;
    private TokenManager tokenManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        profileText = v.findViewById(R.id.profileText);
        progressBar = v.findViewById(R.id.profileProgress);
        Button logoutBtn = v.findViewById(R.id.logoutButton);
        tokenManager = new TokenManager(requireContext());

        logoutBtn.setOnClickListener(view -> {
            tokenManager.clearToken();
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        });

        loadProfile();
        return v;
    }

    private void loadProfile() {
        String token = tokenManager.getToken();
        if (token == null) return;
        progressBar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
               .url("https://media-note.ct.ws/profile.php")
               .addHeader("Authorization", "Bearer " + token)
               .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    profileText.setText("Failed to load: " + e.getMessage());
                });
            }
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String body = response.body()!= null? response.body().string() : "Empty";
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    profileText.setText(body);
                });
            }
        });
    }
}
