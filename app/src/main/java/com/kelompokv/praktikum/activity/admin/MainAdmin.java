package com.kelompokv.praktikum.activity.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.activity.LoginActivity;
import com.kelompokv.praktikum.activity.admin.keluarga.AKeluargaFragment;
import com.kelompokv.praktikum.activity.admin.user.AUserFragment;
import com.kelompokv.praktikum.activity.user.MainUser;
import com.kelompokv.praktikum.activity.user.UserAddAnggotaFragment;
import com.kelompokv.praktikum.activity.user.UserFragment;
import com.kelompokv.praktikum.activity.user.UserProfileFragment;
import com.kelompokv.praktikum.activity.user.UserViewKeluargaFragment;
import com.kelompokv.praktikum.model.auth.FBToken;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainAdmin extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottom_nav;
    TextView title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_admin);

        title = findViewById(R.id.fl_admin_title);
        title.setText("Admin");

        bottom_nav = findViewById(R.id.bottom_admin_nav);
        bottom_nav.setOnNavigationItemSelectedListener(this);

        loadFragment(new AdminFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_admin_home:
                loadFragment(new AdminFragment());
                break;
            case R.id.menu_admin_user:
                loadFragment(new AUserFragment());
                break;
            case R.id.menu_admin_keluarga:
                loadFragment(new AKeluargaFragment());
                break;
            case R.id.menu_admin_logout:
                logout();
                break;
        }
        return true;
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fl_admin_container, fragment);
        ft.commit();
    }

    private void logout() {
        SharedPreferences auth_sp = getSharedPreferences("authSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        Integer user_id = auth_sp.getInt("log_id", 0);
        auth_sp.edit().clear().commit();
        Log.e("Response body", auth_sp.getString("token", ""));
        Log.e("Response body", auth_sp.getString("role", ""));
        deleteFBToken(user_id);
        startActivity(new Intent(MainAdmin.this, LoginActivity.class));
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
