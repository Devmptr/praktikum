package com.kelompokv.praktikum.activity.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.activity.LoginActivity;
import com.kelompokv.praktikum.activity.admin.DashboardAdminActivity;
import com.kelompokv.praktikum.adapter.AnggotaAdapter;
import com.kelompokv.praktikum.model.auth.FBToken;
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;
import com.kelompokv.praktikum.model.user.AnggotaKeluargaResult;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AnggotaService;
import com.kelompokv.praktikum.network.service.AuthService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView list_view;
    Button btn_logout, btn_create;
    String role;
    SharedPreferences auth_sp;
    private Integer user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth_sp = getSharedPreferences("authSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        user_id = auth_sp.getInt("log_id", 0);

        if (!auth_sp.contains("token")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        role = auth_sp.getString("role", "");
        if (role.equals("admin")){
            startActivity(new Intent(MainActivity.this, DashboardAdminActivity.class));
        }

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        btn_create = (Button) findViewById(R.id.btn_create_data);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateData.class));
            }
        });

        loadAnggota(user_id);

        list_view = (ListView) findViewById(R.id.list_anggota);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ViewAnggota.class);
                AnggotaKeluarga item = (AnggotaKeluarga) adapterView.getItemAtPosition(i);
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        });
    }

    private void logout() {
        auth_sp.edit().clear().commit();
        Log.e("Response body", auth_sp.getString("token", ""));
        Log.e("Response body", auth_sp.getString("role", ""));
        deleteFBToken(user_id);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void loadAnggota(Integer id) {
        if (id != 0){
            AnggotaService service = Client.getClient().create(AnggotaService.class);
            Call<AnggotaKeluargaResult> anggotas = service.getAnggota(id);
            anggotas.enqueue(new Callback<AnggotaKeluargaResult>() {
                @Override
                public void onResponse(Call<AnggotaKeluargaResult> call, Response<AnggotaKeluargaResult> response) {
                    if(response.isSuccessful()) {
                        //you can do whatever with the response body now...
                        String responseBodyString= response.body().getAnggota().toString();
                        Log.d("Response body", responseBodyString);

                        show(response.body().getAnggota());
                    }
                    else  {
                        Log.e("Response errorBody", String.valueOf(response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<AnggotaKeluargaResult> call, Throwable t) {
                    Log.e("Response errorDev", String.valueOf(t.getMessage()));
                }
            });
        }
    }

    public void show(List<AnggotaKeluarga> anggotas){
        AnggotaAdapter anggotaAdapter = new AnggotaAdapter(this, R.layout.item_anggota, anggotas);
        list_view = (ListView) findViewById(R.id.list_anggota);
        list_view.setAdapter(anggotaAdapter);
        anggotaAdapter.notifyDataSetChanged();
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