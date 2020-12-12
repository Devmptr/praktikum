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
import com.kelompokv.praktikum.activity.admin.anggota.IndexAnggotaActivity;
import com.kelompokv.praktikum.activity.admin.user.IndexUserActivity;
import com.kelompokv.praktikum.activity.admin.user.ShowUserActivity;
import com.kelompokv.praktikum.model.admin.Keluarga;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDKeluarga;
import com.kelompokv.praktikum.network.response.SERKeluarga;
import com.kelompokv.praktikum.network.response.SERUser;
import com.kelompokv.praktikum.network.service.AdminService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowKeluargaActivity extends AppCompatActivity {
    Integer id_keluarga;
    Intent intent;
    AdminService service;
    EditText alamat, rtrw, kodepos, kelurahan, kecamatan, kabupaten, provinsi;
    Button btn_update, btn_delete, btn_lihat_anggota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_keluarga);

        intent = getIntent();
        btn_update = findViewById(R.id.akupdate_update);
        btn_delete = findViewById(R.id.akupdate_delete);
        btn_lihat_anggota = findViewById(R.id.akupdate_anggota);
        alamat = findViewById(R.id.akupdate_alamat);
        rtrw = findViewById(R.id.akupdate_rtrw);
        kodepos = findViewById(R.id.akupdate_kodepos);
        kelurahan = findViewById(R.id.akupdate_kelurahan);
        kecamatan = findViewById(R.id.akupdate_kecamatan);
        kabupaten = findViewById(R.id.akupdate_kabupaten);
        provinsi = findViewById(R.id.akupdate_provinsi);

        id_keluarga = intent.getIntExtra("id", 0);
        service = Client.getClient().create(AdminService.class);

        viewKeluarga(id_keluarga);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKeluarga(id_keluarga, alamat.getText().toString(), rtrw.getText().toString(),
                                kodepos.getText().toString(), kelurahan.getText().toString(),
                                kecamatan.getText().toString(), kabupaten.getText().toString(),
                                provinsi.getText().toString());
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteKeluarga(id_keluarga);
            }
        });

        btn_lihat_anggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ShowKeluargaActivity.this, IndexAnggotaActivity.class);
                intent.putExtra("id_keluarga", id_keluarga);
                startActivity(intent);
            }
        });
    }

    private void viewKeluarga(Integer id){
        if(id == 0){
            startActivity(new Intent(ShowKeluargaActivity.this, IndexKeluargaActivity.class));
        }else{
            Call<SERKeluarga> show = service.showKeluarga(id);
            show.enqueue(new Callback<SERKeluarga>() {
                @Override
                public void onResponse(Call<SERKeluarga> call, Response<SERKeluarga> response) {
                    Log.d("Call request", call.request().toString());
                    Log.d("Call request header", call.request().headers().toString());
                    Log.d("Response raw header", response.headers().toString());
                    Log.d("Response raw", String.valueOf(response.raw().body()));
                    Log.d("Response code", String.valueOf(response.code()));
                    if(response.isSuccessful()) {
                        //you can do whatever with the response body now...
                        String responseBodyString = response.body().getKeluarga().toString();
                        Log.d("Response body", responseBodyString);

                        showKeluarga(response.body().getKeluarga());
                    }
                    else  {
                        Log.e("Response errorBody", String.valueOf(response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<SERKeluarga> call, Throwable t) {
                    Log.e("Response Failure", String.valueOf(t.getMessage().toString()));
                }
            });
        }
    }

    private void showKeluarga(Keluarga data){
        alamat.setText(data.getAlamat());
        rtrw.setText(data.getRtrw());
        kodepos.setText(data.getKodepos());
        kelurahan.setText(data.getKelurahan());
        kecamatan.setText(data.getKecamatan());
        kabupaten.setText(data.getKabupaten());
        provinsi.setText(data.getProvinsi());
    }

    private void updateKeluarga(Integer id, String ualamat, String urtrw, String ukodepos,
                                String ukelurahan, String ukecamatan, String ukabupaten,
                                String uprovinsi){

        Call<CUDKeluarga> update = service.updateKeluarga(id, ualamat, urtrw, ukodepos, ukelurahan,
                                                          ukecamatan, ukabupaten, uprovinsi);

        update.enqueue(new Callback<CUDKeluarga>() {
            @Override
            public void onResponse(Call<CUDKeluarga> call, Response<CUDKeluarga> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Update Keluarga Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    startActivity(new Intent(ShowKeluargaActivity.this, IndexKeluargaActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Update Keluarga Gagal",
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

    private void deleteKeluarga(Integer id){

        Call<CUDKeluarga> delete = service.deleteKeluarga(id);

        delete.enqueue(new Callback<CUDKeluarga>() {
            @Override
            public void onResponse(Call<CUDKeluarga> call, Response<CUDKeluarga> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Delete Keluarga Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    startActivity(new Intent(ShowKeluargaActivity.this, IndexKeluargaActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Delete Keluarga Gagal",
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