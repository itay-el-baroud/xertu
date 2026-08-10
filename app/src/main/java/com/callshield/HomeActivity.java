package com.callshield;
import android.os.Bundle;
import android.view.Menu;
import androidx.appcompat.app.AppCompatActivity;
import com.callshield.ui.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class HomeActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_home);
            BottomNavigationView nav = findViewById(R.id.bottomNav);
            if(nav==null) return;
            Menu menu = nav.getMenu();
            menu.add(0,1,0,"Home");
            menu.add(0,2,1,"Blocked");
            menu.add(0,3,2,"Logs");
            menu.add(0,4,3,"Profile");
            menu.add(0,5,4,"Settings");
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
        } catch (Exception e) { }
    }
}
