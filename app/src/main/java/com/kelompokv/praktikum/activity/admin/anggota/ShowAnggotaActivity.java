package com.kelompokv.praktikum.activity.admin.anggota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.model.admin.User;
import com.kelompokv.praktikum.model.admin.UserList;
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDAnggota;
import com.kelompokv.praktikum.network.response.SERAnggota;
import com.kelompokv.praktikum.network.service.AdminService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAnggotaActivity extends AppCompatActivity {
    Intent intent;
    Bundle extras;
    Integer id_anggota, id_keluarga;
    Spinner jenis_kelamin, tipe, validasi;
    EditText nama, tempat_lahir, tanggal_lahir, agama, pendidikan, pekerjaan, ayah, ibu;
    Button btn_update, btn_delete;
    AdminService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_anggota);

        intent = getIntent();
        extras = intent.getExtras();
        id_anggota = extras.getInt("id_anggota", 0);
        id_keluarga = extras.getInt("id_keluarga", 0);

        nama = findViewById(R.id.aaupdate_nama);
        tempat_lahir = findViewById(R.id.aaupdate_tempat_lahir);
        tanggal_lahir = findViewById(R.id.aaupdate_tanggal_lahir);
        agama = findViewById(R.id.aaupdate_agama);
        pendidikan = findViewById(R.id.aaupdate_pendidikan);
        pekerjaan = findViewById(R.id.aaupdate_pekerjaan);
        ayah = findViewById(R.id.aaupdate_ayah);
        ibu = findViewById(R.id.aaupdate_ibu);
        jenis_kelamin = findViewById(R.id.aaupdate_jenis_kelamin);
        tipe = findViewById(R.id.aaupdate_tipe);
        validasi = findViewById(R.id.aaupdate_validasi);
        btn_update = findViewById(R.id.aaupdate_update);
        btn_delete = findViewById(R.id.aaupdate_delete);
        service = Client.getClient().create(AdminService.class);

        Toast.makeText(getApplicationContext(), "id anggota "+id_anggota+"| id keluarga"+id_keluarga,
                Toast.LENGTH_SHORT).show();

        viewAnggota(id_anggota);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAnggota(id_anggota, nama.getText().toString(), tempat_lahir.getText().toString(),
                        tanggal_lahir.getText().toString(), agama.getText().toString(),
                        pendidikan.getText().toString(), pekerjaan.getText().toString(),
                        ayah.getText().toString(), ibu.getText().toString(),
                        jenis_kelamin.getSelectedItem().toString(), tipe.getSelectedItem().toString(),
                        id_keluarga, validasi.getSelectedItem().toString());
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAnggota(id_anggota);
            }
        });
    }

    private void viewAnggota(Integer id_ang){
        Call<SERAnggota> show = service.showAnggota(id_ang);

        show.enqueue(new Callback<SERAnggota>() {
            @Override
            public void onResponse(Call<SERAnggota> call, Response<SERAnggota> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Log.d("Response body", response.body().getAnggota().toString());
                    showAnggota(response.body().getAnggota());
                } else {
                    Log.d("Response body", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<SERAnggota> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Dev "+t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Response body", t.getMessage());
            }
        });
    }

    private void showAnggota(AnggotaKeluarga data){
        Integer index_jk, index_tipe, index_validate, index_email_user;
        DateFormat date = DateFormat.getDateInstance();
        nama.setText(data.getNama());
        tempat_lahir.setText(data.getTempatlahir());
        tanggal_lahir.setText(date.format(data.getTanggallahir()));
        agama.setText(data.getAgama());
        pendidikan.setText(data.getPendidikan());
        pekerjaan.setText(data.getPekerjaan());
        ayah.setText(data.getAyah());
        ibu.setText(data.getIbu());
        index_jk = indexSpinner(jenis_kelamin, data.getJeniskelamin());
        jenis_kelamin.setSelection(index_jk);
        index_tipe = indexSpinner(tipe, data.getTipe());
        tipe.setSelection(index_tipe);
        index_validate = indexSpinner(validasi, data.getValidated());
        validasi.setSelection(index_validate);

    }

    private int indexSpinner(Spinner spin, String text){
        for (int i = 0; i < spin.getCount(); i++){
            if (spin.getItemAtPosition(i).toString().equalsIgnoreCase(text)){
                return i;
            }
        }
        return 0;
    }

    private void updateAnggota(Integer id_ang, String cnama, String ctempat, String ctanggal, String cagama,
                               String cpendidikan, String cpekerjaan, String cayah, String cibu,
                               String cjenis_kelamin, String ctipe, Integer id_kel, String validasi){
        Call<CUDAnggota> update = service.updateAnggota(
                id_ang, cnama, cjenis_kelamin, ctempat, ctanggal, cagama, cpendidikan, cpekerjaan,
                ctipe, cayah, cibu, id_kel, validasi
        );

        update.enqueue(new Callback<CUDAnggota>() {
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
                    intent = new Intent(ShowAnggotaActivity.this, IndexAnggotaActivity.class);
                    intent.putExtra("id_keluarga", id_keluarga);
                    startActivity(intent);
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

    private void deleteAnggota(Integer id_ang){
        Call<CUDAnggota> create = service.deleteAnggota(id_ang);

        create.enqueue(new Callback<CUDAnggota>() {
            @Override
            public void onResponse(Call<CUDAnggota> call, Response<CUDAnggota> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Delete Anggota Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    intent = new Intent(ShowAnggotaActivity.this, IndexAnggotaActivity.class);
                    intent.putExtra("id_keluarga", id_keluarga);
                    startActivity(intent);
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
}