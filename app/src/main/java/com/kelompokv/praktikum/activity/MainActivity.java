package com.kelompokv.praktikum.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.kelompokv.praktikum.R;

public class MainActivity extends AppCompatActivity {

    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = getSharedPreferences("sg_shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor ed;

        if (!sharedPrefs.contains("token")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        btn_logout = (Button) findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void logout(){
        SharedPreferences sgSharedPref = getApplicationContext().getSharedPreferences("sg_shared_pref",
                getApplicationContext().MODE_PRIVATE);
        sgSharedPref.edit().clear().commit();

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}