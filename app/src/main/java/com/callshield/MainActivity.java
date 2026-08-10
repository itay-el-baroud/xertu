package com.callshield;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            TextView tv = new TextView(this);
            tv.setText("CallShield شغال - دوس رجوع وادخل Home");
            tv.setTextSize(20);
            tv.setPadding(40,300,40,40);
            setContentView(tv);
        }catch(Exception e){}
    }
}
