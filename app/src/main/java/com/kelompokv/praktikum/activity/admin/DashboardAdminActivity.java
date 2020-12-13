package com.kelompokv.praktikum.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.activity.LoginActivity;
import com.kelompokv.praktikum.activity.admin.keluarga.IndexKeluargaActivity;
import com.kelompokv.praktikum.activity.admin.user.IndexUserActivity;
import com.kelompokv.praktikum.activity.user.MainActivity;
import com.kelompokv.praktikum.model.auth.FBToken;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardAdminActivity extends AppCompatActivity {
    Button btn_admin_user, btn_admin_keluarga, btn_logout;
    SharedPreferences auth_sp;
    Integer user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        btn_admin_user = findViewById(R.id.btn_admin_user);
        btn_admin_keluarga = findViewById(R.id.btn_admin_keluarga);
        btn_logout = findViewById(R.id.btn_admin_logout);

        auth_sp = getSharedPreferences("authSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        user_id = auth_sp.getInt("log_id", 0);

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
        deleteFBToken(user_id);
        startActivity(new Intent(DashboardAdminActivity.this, LoginActivity.class));
    }

    private void deleteFBToken(Integer id_u){
        AuthService service;
        service = Client.getClient().create(AuthService.class);
        Call<FBToken> setToken = service.deleteFBToken(id_u);

        setToken.enqueue(new Callback<FBToken>() {
            @Override
            public void onResponse(Call<FBToken> call, Response<FBToken> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Delete Token Berhasil",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Delete Token Gagal",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FBToken> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error" + t,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}