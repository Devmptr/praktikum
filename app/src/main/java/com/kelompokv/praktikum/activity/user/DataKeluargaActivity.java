package com.kelompokv.praktikum.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.model.admin.Keluarga;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDKeluarga;
import com.kelompokv.praktikum.network.response.SERKeluarga;
import com.kelompokv.praktikum.network.response.SERUser;
import com.kelompokv.praktikum.network.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKeluargaActivity extends AppCompatActivity {

    SharedPreferences auth_sp;
    Integer user_id;
    UserService service;
    EditText alamat, rtrw, kodepos, kelurahan, kecamatan, kabupaten, provinsi;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_keluarga);

        auth_sp = getSharedPreferences("authSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        user_id = auth_sp.getInt("log_id", 0);
        service = Client.getClient().create(UserService.class);
        alamat = findViewById(R.id.ukeluarga_alamat);
        rtrw = findViewById(R.id.ukeluarga_rtrw);
        kodepos = findViewById(R.id.ukeluarga_kodepos);
        kelurahan = findViewById(R.id.ukeluarga_kelurahan);
        kecamatan = findViewById(R.id.ukeluarga_kecamatan);
        kabupaten = findViewById(R.id.ukeluarga_kabupaten);
        provinsi = findViewById(R.id.ukeluarga_provinsi);
        btn_update = findViewById(R.id.ukeluarga_update);

        viewKeluarga(user_id);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKeluarga(user_id, alamat.getText().toString(), rtrw.getText().toString(),
                        kodepos.getText().toString(), kelurahan.getText().toString(),
                        kecamatan.getText().toString(), kabupaten.getText().toString(),
                        provinsi.getText().toString());
            }
        });
    }

    private void updateKeluarga(Integer uid, String ualamat, String urtrw, String ukodepos,
                                String ukelurahan, String ukecamatan, String ukabupaten, String uprovinsi){

        Call<CUDKeluarga> show = service.updateKeluarga(uid, ualamat, urtrw, ukodepos, ukelurahan,
                                                        ukecamatan, ukabupaten, uprovinsi);
        show.enqueue(new Callback<CUDKeluarga>() {
            @Override
            public void onResponse(Call<CUDKeluarga> call, Response<CUDKeluarga> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Update Keluarga Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    startActivity(new Intent(DataKeluargaActivity.this, MainActivity.class));
                }
                else  {
                    Toast.makeText(getApplicationContext(), "Update Keluarga Gagal",
                            Toast.LENGTH_SHORT).show();
                    Log.e("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<CUDKeluarga> call, Throwable t) {
                Log.e("Response Failure", String.valueOf(t.getMessage().toString()));
            }
        });
    }

    private void viewKeluarga(Integer id){
        if(id == 0){
            startActivity(new Intent(DataKeluargaActivity.this, MainActivity.class));
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
                        String responseBodyString= response.body().getKeluarga().toString();
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
}