package com.example.lntfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView logout, username;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation_bar);
        logout = findViewById(R.id.logout_button);
        username = findViewById(R.id.username);

        Intent intent = getIntent();
        if(intent != null){
            username.setText(intent.getStringExtra("EMAIL"));
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment currentFragment = null;
            int id = item.getItemId();
            if(id == R.id.Counter)
                currentFragment = new CounterFragment();
            else if(id == R.id.Area_Calculator)
                currentFragment = new AreaCalculatorFragment();
            else if(id == R.id.Volume_Calculator)
                currentFragment = new VolumeCalculatorFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();
            return true;
        });

        logout.setOnClickListener(v -> {
            Intent logoutintent = new Intent(MainActivity.this, AuthenticationActivity.class);
            startActivity(logoutintent);
            finish();
        });
    }
}