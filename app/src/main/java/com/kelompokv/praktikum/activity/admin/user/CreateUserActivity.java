package com.kelompokv.praktikum.activity.admin.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDUser;
import com.kelompokv.praktikum.network.service.AdminService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUserActivity extends AppCompatActivity {
    Button btn_create;
    EditText name, email, password;
    Spinner role;
    AdminService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        btn_create = findViewById(R.id.aucreate_add);
        name = findViewById(R.id.aucreate_name);
        email = findViewById(R.id.aucreate_email);
        password = findViewById(R.id.aucreate_password);
        role = findViewById(R.id.aucreate_role);
        service = Client.getClient().create(AdminService.class);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(name.getText().toString(), email.getText().toString(),
                           password.getText().toString(), role.getSelectedItem().toString());
            }
        });
    }

    private void createUser(String cname, String cemail, String cpass, String crole){
        Call<CUDUser> create = service.createUser(cname, cemail, cpass, crole);

        create.enqueue(new Callback<CUDUser>() {
            @Override
            public void onResponse(Call<CUDUser> call, Response<CUDUser> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Create User Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    startActivity(new Intent(CreateUserActivity.this, IndexUserActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Create User Gagal",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getError().toString());
                }
            }

            @Override
            public void onFailure(Call<CUDUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Dev "+t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Response body", t.getMessage());
            }
        });
    }
}