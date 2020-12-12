package com.kelompokv.praktikum.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.activity.LoginActivity;
import com.kelompokv.praktikum.activity.admin.keluarga.IndexKeluargaActivity;
import com.kelompokv.praktikum.activity.admin.user.IndexUserActivity;
import com.kelompokv.praktikum.activity.user.MainActivity;

public class DashboardAdminActivity extends AppCompatActivity {
    Button btn_admin_user, btn_admin_keluarga, btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        btn_admin_user = findViewById(R.id.btn_admin_user);
        btn_admin_keluarga = findViewById(R.id.btn_admin_keluarga);
        btn_logout = findViewById(R.id.btn_admin_logout);

        btn_admin_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardAdminActivity.this, IndexUserActivity.class));
            }
        });

        btn_admin_keluarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardAdminActivity.this, IndexKeluargaActivity.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        SharedPreferences auth_sp;
        auth_sp = getSharedPreferences("authSharedPreferences", MODE_PRIVATE);
        auth_sp.edit().clear().commit();

        startActivity(new Intent(DashboardAdminActivity.this, LoginActivity.class));
    }
}