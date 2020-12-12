package com.kelompokv.praktikum.activity.admin.keluarga;

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
import com.kelompokv.praktikum.activity.admin.user.CreateUserActivity;
import com.kelompokv.praktikum.activity.admin.user.IndexUserActivity;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDKeluarga;
import com.kelompokv.praktikum.network.response.CUDUser;
import com.kelompokv.praktikum.network.service.AdminService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateKeluargaActivity extends AppCompatActivity {
    Button btn_create;
    EditText alamat, rtrw, kodepos, kelurahan, kecamatan, kabupaten, provinsi;
    AdminService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_keluarga);

        btn_create = findViewById(R.id.akcreate_create);
        alamat = findViewById(R.id.akcreate_alamat);
        rtrw = findViewById(R.id.akcreate_rtrw);
        kodepos = findViewById(R.id.akcreate_kodepos);
        kelurahan = findViewById(R.id.akcreate_kelurahan);
        kecamatan = findViewById(R.id.akcreate_kecamatan);
        kabupaten = findViewById(R.id.akcreate_kabupaten);
        provinsi = findViewById(R.id.akcreate_provinsi);
        service = Client.getClient().create(AdminService.class);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createKeluarga(alamat.getText().toString(), rtrw.getText().toString(),
                               kodepos.getText().toString(), kelurahan.getText().toString(),
                               kecamatan.getText().toString(), kabupaten.getText().toString(),
                               provinsi.getText().toString());
            }
        });
    }

    private void createKeluarga(String calamat, String crtrw, String ckodepos, String ckelurahan,
                                String ckecamatan, String ckabupaten, String cprovinsi){

        Call<CUDKeluarga> create = service.createKeluarga(calamat, crtrw, ckodepos, ckelurahan,
                                                          ckecamatan, ckabupaten, cprovinsi);

        create.enqueue(new Callback<CUDKeluarga>() {
            @Override
            public void onResponse(Call<CUDKeluarga> call, Response<CUDKeluarga> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Create Keluarga Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    startActivity(new Intent(CreateKeluargaActivity.this, IndexKeluargaActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Create Keluarga Gagal",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getError().toString());
                }
            }

            @Override
            public void onFailure(Call<CUDKeluarga> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Dev "+t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Response body", t.getMessage());
            }
        });
    }
}