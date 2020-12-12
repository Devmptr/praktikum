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
import com.kelompokv.praktikum.model.admin.User;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDUser;
import com.kelompokv.praktikum.network.response.SERAnggota;
import com.kelompokv.praktikum.network.response.SERUser;
import com.kelompokv.praktikum.network.service.AdminService;
import com.kelompokv.praktikum.network.service.AnggotaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowUserActivity extends AppCompatActivity {
    Integer id_user, index_spinner;
    Intent intent;
    AdminService service;
    EditText name, email, password;
    Button btn_update, btn_delete;
    Spinner role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        intent = getIntent();
        name = findViewById(R.id.auupdate_name);
        email = findViewById(R.id.auupdate_email);
        role = findViewById(R.id.auupdate_role);
        password = findViewById(R.id.auupdate_password);
        btn_update = findViewById(R.id.auupdate_update);
        btn_delete = findViewById(R.id.auupdate_delete);

        id_user = intent.getIntExtra("id", 0);
        service = Client.getClient().create(AdminService.class);

        viewUser(id_user);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(id_user, name.getText().toString(), email.getText().toString(),
                           password.getText().toString(), role.getSelectedItem().toString());
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(id_user);
            }
        });
    }

    private void viewUser(Integer id){
        if(id == 0){
            startActivity(new Intent(ShowUserActivity.this, IndexUserActivity.class));
        }else{
            Call<SERUser> show = service.showUser(id);
            show.enqueue(new Callback<SERUser>() {
                @Override
                public void onResponse(Call<SERUser> call, Response<SERUser> response) {
                    Log.d("Call request", call.request().toString());
                    Log.d("Call request header", call.request().headers().toString());
                    Log.d("Response raw header", response.headers().toString());
                    Log.d("Response raw", String.valueOf(response.raw().body()));
                    Log.d("Response code", String.valueOf(response.code()));
                    if(response.isSuccessful()) {
                        //you can do whatever with the response body now...
                        String responseBodyString= response.body().getUser().toString();
                        Log.d("Response body", responseBodyString);

                        showUser(response.body().getUser());
                    }
                    else  {
                        Log.e("Response errorBody", String.valueOf(response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<SERUser> call, Throwable t) {
                    Log.e("Response Failure", String.valueOf(t.getMessage().toString()));
                }
            });
        }
    }

    private void showUser(User data){
        name.setText(data.getName());
        email.setText(data.getEmail());
        index_spinner = indexSpinner(role, data.getRole());
        role.setSelection(index_spinner);
    }

    private int indexSpinner(Spinner spin, String text){
        for (int i = 0; i < spin.getCount(); i++){
            if (spin.getItemAtPosition(i).toString().equalsIgnoreCase(text)){
                return i;
            }
        }
        return 0;
    }

    private void updateUser(Integer id, String uname, String uemail, String upass, String urole){
        Call<CUDUser> update = service.updateUser(id, uname, uemail, upass, urole);

        update.enqueue(new Callback<CUDUser>() {
            @Override
            public void onResponse(Call<CUDUser> call, Response<CUDUser> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Update User Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    startActivity(new Intent(ShowUserActivity.this, IndexUserActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Update User Gagal",
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

    private void deleteUser(Integer id){
        Call<CUDUser> delete = service.deleteUser(id);

        delete.enqueue(new Callback<CUDUser>() {
            @Override
            public void onResponse(Call<CUDUser> call, Response<CUDUser> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Delete User Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    startActivity(new Intent(ShowUserActivity.this, IndexUserActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Delete User Gagal",
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