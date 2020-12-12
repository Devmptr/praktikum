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
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDAnggota;
import com.kelompokv.praktikum.network.response.SERAnggota;
import com.kelompokv.praktikum.network.service.AnggotaService;

import java.text.DateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAnggota extends AppCompatActivity {

    String id;
    Spinner u_jenis_kelamin, u_tipe;
    EditText u_nama, u_tempat_lahir, u_tanggal_lahir, u_agama, u_pendidikan, u_pekerjaan, u_ayah, u_ibu;
    Button btn_update, btn_delete;
    AnggotaService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anggota);

        u_nama = (EditText) findViewById(R.id.u_nama);
        u_tempat_lahir = (EditText) findViewById(R.id.u_tempat_lahir);
        u_tanggal_lahir = (EditText) findViewById(R.id.u_tanggal_lahir);
        u_agama = (EditText) findViewById(R.id.u_agama);
        u_pendidikan = (EditText) findViewById(R.id.u_pendidikan);
        u_pekerjaan = (EditText) findViewById(R.id.u_pekerjaan);
        u_ayah = (EditText) findViewById(R.id.u_ayah);
        u_ibu = (EditText) findViewById(R.id.u_ibu);
        u_jenis_kelamin = (Spinner) findViewById(R.id.u_jenis_kelamin);
        u_tipe = (Spinner) findViewById(R.id.u_tipe);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        viewAnggota(id);

        btn_delete = (Button) findViewById(R.id.btn_delete_anggota);
        btn_delete.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Call<CUDAnggota> deleteAnggotaExe = service.deleteAnggota(id);

                deleteAnggotaExe.enqueue(new Callback<CUDAnggota>() {
                    @Override
                    public void onResponse(Call<CUDAnggota> call, Response<CUDAnggota> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Delete Anggota Berhasil",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Response body", response.body().getSuccess().toString());
                            startActivity(new Intent(ViewAnggota.this, MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Delete Anggota Gagal",
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

        btn_update = (Button) findViewById(R.id.btn_update_anggota);
        btn_update.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Call<CUDAnggota> updateAnggotaExe = service.updateAnggota(
                        id,
                        u_nama.getText().toString(),
                        u_jenis_kelamin.getSelectedItem().toString(),
                        u_tempat_lahir.getText().toString(),
                        u_tanggal_lahir.getText().toString(),
                        u_agama.getText().toString(),
                        u_pendidikan.getText().toString(),
                        u_pekerjaan.getText().toString(),
                        u_tipe.getSelectedItem().toString(),
                        u_ayah.getText().toString(),
                        u_ibu.getText().toString()
                );

                updateAnggotaExe.enqueue(new Callback<CUDAnggota>() {
                    @Override
                    public void onResponse(Call<CUDAnggota> call, Response<CUDAnggota> response) {
                        Log.d("Call request", call.request().toString());
                        Log.d("Call request header", call.request().headers().toString());
                        Log.d("Response raw header", response.headers().toString());
                        Log.d("Response raw", String.valueOf(response.raw().body()));
                        Log.d("Response code", String.valueOf(response.code()));

                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Update Anggota Berhasil",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Response body", response.body().getSuccess().toString());
                            startActivity(new Intent(ViewAnggota.this, MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Update Anggota Gagal",
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

    public void viewAnggota(String anggota){
        service = Client.getClient().create(AnggotaService.class);
        Call<SERAnggota> anggotas = service.editAnggota(anggota);
        anggotas.enqueue(new Callback<SERAnggota>() {
            @Override
            public void onResponse(Call<SERAnggota> call, Response<SERAnggota> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));
                if(response.isSuccessful()) {
                    //you can do whatever with the response body now...
                    String responseBodyString= response.body().getAnggota().toString();
                    Log.d("Response body", responseBodyString);

                    show(response.body().getAnggota());
                }
                else  {
                    Log.d("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<SERAnggota> call, Throwable t) {

            }
        });
    }

    public void show(AnggotaKeluarga anggotas){
        DateFormat date = DateFormat.getDateInstance();
        u_nama.setText(anggotas.getNama());
        u_tempat_lahir.setText(anggotas.getTempatlahir());
        u_tanggal_lahir.setText(date.format(anggotas.getTanggallahir()).toString());
        u_agama.setText(anggotas.getAgama());
        u_pendidikan.setText(anggotas.getPendidikan());
        u_pekerjaan.setText(anggotas.getPekerjaan());
        u_ayah.setText(anggotas.getAyah());
        u_ibu.setText(anggotas.getIbu());
    }
}