package com.callshield;
import android.os.Bundle;
import android.view.Menu;
import androidx.appcompat.app.AppCompatActivity;
import com.callshield.ui.BlockedFragment;
import com.callshield.ui.HomeFragment;
import com.callshield.ui.LogsFragment;
import com.callshield.ui.ProfileFragment;
import com.callshield.ui.SettingsFragment;
import com.callshield.utils.PermissionHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class HomeActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_home);
            PermissionHelper.requestAllPermissions(this);
            BottomNavigationView nav = findViewById(R.id.bottomNav);
            Menu menu = nav.getMenu();
            menu.add(0,1,0,"Home").setIcon(R.drawable.ic_home);
            menu.add(0,2,1,"Blocked").setIcon(R.drawable.ic_block);
            menu.add(0,3,2,"Logs").setIcon(R.drawable.ic_logs);
            menu.add(0,4,3,"Profile").setIcon(R.drawable.ic_profile);
            menu.add(0,5,4,"Settings").setIcon(R.drawable.ic_settings);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
            nav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id==1) getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
                else if (id==2) getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new BlockedFragment()).commit();
                else if (id==3) getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new LogsFragment()).commit();
                else if (id==4) getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();
                else if (id==5) getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SettingsFragment()).commit();
                return true;
            });
        } catch (Exception e) { e.printStackTrace(); }
    }
}
