package com.callshield;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.callshield.ui.BlockedFragment;
import com.callshield.ui.HomeFragment;
import com.callshield.ui.LogsFragment;
import com.callshield.ui.ProfileFragment;
import com.callshield.ui.SettingsFragment;
import com.callshield.util.PermissionHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PermissionHelper.requestAllPermissions(this);
        bottomNav = findViewById(R.id.bottomNav);

        loadFragment(new HomeFragment());

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            if (id == R.id.nav_home) fragment = new HomeFragment();
            else if (id == R.id.nav_blocked) fragment = new BlockedFragment();
            else if (id == R.id.nav_logs) fragment = new LogsFragment();
            else if (id == R.id.nav_profile) fragment = new ProfileFragment();
            else if (id == R.id.nav_settings) fragment = new SettingsFragment();
            if (fragment!= null) {
                loadFragment(fragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
               .replace(R.id.fragmentContainer, fragment)
               .commit();
    }
}
