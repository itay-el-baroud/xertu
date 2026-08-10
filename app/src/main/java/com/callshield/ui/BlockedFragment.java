package com.callshield.ui;
import android.os.Bundle; import android.view.*; import android.widget.TextView; import androidx.annotation.*; import androidx.fragment.app.Fragment;
public class BlockedFragment extends Fragment {
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b){
        TextView tv=new TextView(getContext()); tv.setText("Blocked"); return tv;
    }
}
