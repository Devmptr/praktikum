package com.kelompokv.praktikum.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDAnggota;
import com.kelompokv.praktikum.network.response.CUDKeluarga;
import com.kelompokv.praktikum.network.service.AnggotaService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstLoginActivity extends AppCompatActivity {
    Spinner c_jenis_kelamin, c_tipe;
    EditText c_nama, c_tempat_lahir, c_tanggal_lahir, c_agama, c_pendidikan, c_pekerjaan, c_ayah, c_ibu,
                c_alamat, c_rtrw, c_kodepos, c_kelurahan, c_kecamatan, c_kabupaten, c_provinsi;
    Button btn_create;
    AnggotaService service;
    SharedPreferences auth_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        auth_sp = getApplicationContext().getSharedPreferences("authSharedPreferences",
                getApplicationContext().MODE_PRIVATE);

        c_nama = (EditText) findViewById(R.id.c_nama);
        c_tempat_lahir = (EditText) findViewById(R.id.c_tempat_lahir);
        c_tanggal_lahir = (EditText) findViewById(R.id.c_tanggal_lahir);
        c_agama = (EditText) findViewById(R.id.c_agama);
        c_pendidikan = (EditText) findViewById(R.id.c_pendidikan);
        c_pekerjaan = (EditText) findViewById(R.id.c_pekerjaan);
        c_ayah = (EditText) findViewById(R.id.c_ayah);
        c_ibu = (EditText) findViewById(R.id.c_ibu);
        c_jenis_kelamin = (Spinner) findViewById(R.id.c_jenis_kelamin);
        c_tipe = (Spinner) findViewById(R.id.c_tipe);

        c_alamat = (EditText) findViewById(R.id.c_alamat);
        c_rtrw = (EditText) findViewById(R.id.c_rtrw);
        c_kodepos = (EditText) findViewById(R.id.c_kodepos);
        c_kelurahan = (EditText) findViewById(R.id.c_kelurahan);
        c_kecamatan = (EditText) findViewById(R.id.c_kecamatan);
        c_kabupaten = (EditText) findViewById(R.id.c_kabupaten);
        c_provinsi = (EditText) findViewById(R.id.c_provinsi);

        service = Client.getClient().create(AnggotaService.class);

        btn_create = (Button) findViewById(R.id.btn_create_anggota);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createKeluarga();
            }
        });
    }

    private void createKeluarga(){
        Integer user_id = auth_sp.getInt("log_id", 0);
        Call<CUDKeluarga> storeKeluarga = service.firstLogin(
                c_alamat.getText().toString(),
                c_rtrw.getText().toString(),
                c_kodepos.getText().toString(),
                c_kelurahan.getText().toString(),
                c_kecamatan.getText().toString(),
                c_kabupaten.getText().toString(),
                c_provinsi.getText().toString(),
                c_nama.getText().toString(),
                c_jenis_kelamin.getSelectedItem().toString(),
                c_tempat_lahir.getText().toString(),
                c_tanggal_lahir.getText().toString(),
                c_agama.getText().toString(),
                c_pendidikan.getText().toString(),
                c_pekerjaan.getText().toString(),
                c_tipe.getSelectedItem().toString(),
                c_ayah.getText().toString(),
                c_ibu.getText().toString(),
                user_id
        );

        storeKeluarga.enqueue(new Callback<CUDKeluarga>() {
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

                    SharedPreferences.Editor editor = auth_sp.edit();
                    boolean is_profile = true;
                    editor.putBoolean("is_profile", is_profile);
                    editor.apply();

                    startActivity(new Intent(FirstLoginActivity.this, MainUser.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Create Keluarga Gagal",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.errorBody().toString());
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