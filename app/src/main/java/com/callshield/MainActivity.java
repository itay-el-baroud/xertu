package com.callshield;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.callshield.data.TokenManager;
public class MainActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            TokenManager tm = new TokenManager(this);
            if (tm.isLoggedIn()) {
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
