package com.kelompokv.praktikum.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.activity.admin.DashboardAdminActivity;
import com.kelompokv.praktikum.activity.user.MainActivity;

public class SplashActivity extends AppCompatActivity {

    Button toLogin;
    TextView toRegister;
    SharedPreferences auth_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkAuth();

        toLogin = findViewById(R.id.btn_splash_login);
        toRegister = findViewById(R.id.btn_splash_register);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        });

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
            }
        });
    }

    private void checkAuth(){
        auth_sp = getApplicationContext().getSharedPreferences("authSharedPreferences",
                getApplicationContext().MODE_PRIVATE);
        if (auth_sp.contains("token")) {
            String check_role = auth_sp.getString("role", "");
            if (check_role.equals("user")){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }else if(check_role.equals("admin")){
                startActivity(new Intent(SplashActivity.this, DashboardAdminActivity.class));
            }
        }
    }
}