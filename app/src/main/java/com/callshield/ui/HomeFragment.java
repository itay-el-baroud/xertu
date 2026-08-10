package com.callshield.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.callshield.R;
import com.callshield.data.AppDatabase;
import com.callshield.data.BlockedNumber;
import java.util.List;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        TextView totalBlocked = v.findViewById(R.id.totalBlocked);
        TextView totalAttempts = v.findViewById(R.id.totalAttempts);
        TextView topNumber = v.findViewById(R.id.topNumber);
        TextView analysis = v.findViewById(R.id.analysisText);

        AppDatabase db = AppDatabase.getInstance(requireContext());
        List<BlockedNumber> all = db.blockedDao().getAll();
        totalBlocked.setText("Blocked: " + all.size());
        int sum = 0;
        BlockedNumber top = null;
        for (BlockedNumber b : all) {
            sum += b.attemptsCount;
            if (top == null || b.attemptsCount > top.attemptsCount) top = b;
        }
        totalAttempts.setText("Attempts: " + sum);
        if (top!= null) {
            topNumber.setText("Top: " + top.phoneNumber + " (" + top.attemptsCount + ")");
            if (top.attemptsCount >= 15) {
                analysis.setText("الرقم " + top.phoneNumber + " حاول يتصل بيك " + top.attemptsCount + " مرة. تحب تحظره نهائيا؟");
            } else {
                analysis.setText("تحليل الإزعاج: لا يوجد إزعاج شديد حاليا");
            }
        } else {
            topNumber.setText("No blocked numbers yet");
            analysis.setText("ضيف أول رقم عايز تحظره");
        }
        return v;
    }
}
