package com.kelompokv.praktikum.activity.user;

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
import com.kelompokv.praktikum.network.response.CUDAnggota;
import com.kelompokv.praktikum.network.service.AnggotaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateData extends AppCompatActivity {
    Spinner c_jenis_kelamin, c_tipe;
    EditText c_nama, c_tempat_lahir, c_tanggal_lahir, c_agama, c_pendidikan, c_pekerjaan, c_ayah, c_ibu;
    Button btn_create;
    AnggotaService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

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

        service = Client.getClient().create(AnggotaService.class);

        btn_create = (Button) findViewById(R.id.btn_create_anggota);
        btn_create.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Call<CUDAnggota> storeAnggotaExe = service.storeAnggota(
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
                        5,
                        0
                );

                storeAnggotaExe.enqueue(new Callback<CUDAnggota>() {
                    @Override
                    public void onResponse(Call<CUDAnggota> call, Response<CUDAnggota> response) {
                        Log.d("Call request", call.request().toString());
                        Log.d("Call request header", call.request().headers().toString());
                        Log.d("Response raw header", response.headers().toString());
                        Log.d("Response raw", String.valueOf(response.raw().body()));
                        Log.d("Response code", String.valueOf(response.code()));

                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Create Anggota Berhasil",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Response body", response.body().getSuccess().toString());
                            startActivity(new Intent(CreateData.this, MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Create Anggota Gagal",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Response body", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<CUDAnggota> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error Dev "+t.getMessage().toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("Response body", t.getMessage());
                    }
                });
            }
        });

    }
}