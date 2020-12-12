package com.kelompokv.praktikum.activity.admin.anggota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.activity.admin.keluarga.IndexKeluargaActivity;
import com.kelompokv.praktikum.activity.admin.keluarga.ShowKeluargaActivity;
import com.kelompokv.praktikum.activity.user.MainActivity;
import com.kelompokv.praktikum.activity.user.ViewAnggota;
import com.kelompokv.praktikum.adapter.AdminAnggotaAdapter;
import com.kelompokv.praktikum.adapter.AnggotaAdapter;
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;
import com.kelompokv.praktikum.model.user.AnggotaKeluargaResult;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.SERKeluarga;
import com.kelompokv.praktikum.network.service.AdminService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexAnggotaActivity extends AppCompatActivity {
    Intent intent;
    Integer id_keluarga;
    AdminService service;
    ListView list_anggota, item_anggota;
    Button btn_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_anggota);

        intent = getIntent();
        item_anggota = findViewById(R.id.list_aanggota);
        btn_create = findViewById(R.id.btn_create_aanggota);
        id_keluarga = intent.getIntExtra("id_keluarga", 0);
        service = Client.getClient().create(AdminService.class);

        Toast.makeText(getApplicationContext(), "id keluarga "+id_keluarga,
                Toast.LENGTH_SHORT).show();
        loadAnggota(id_keluarga);

        item_anggota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(IndexAnggotaActivity.this, ShowAnggotaActivity.class);
                AnggotaKeluarga item = (AnggotaKeluarga) adapterView.getItemAtPosition(i);
                Bundle extras = new Bundle();
                extras.putInt("id_anggota", item.getId());
                extras.putInt("id_keluarga", id_keluarga);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexAnggotaActivity.this, CreateAnggotaActivity.class);
                intent.putExtra("id_keluarga", id_keluarga);
                startActivity(intent);
            }
        });
    }

    private void loadAnggota(Integer id){
        if(id == 0){
            startActivity(new Intent(IndexAnggotaActivity.this, IndexKeluargaActivity.class));
        }else{
            Call<AnggotaKeluargaResult> show = service.anggotaKeluarga(id);
            show.enqueue(new Callback<AnggotaKeluargaResult>() {
                @Override
                public void onResponse(Call<AnggotaKeluargaResult> call, Response<AnggotaKeluargaResult> response) {
                    Log.d("Call request", call.request().toString());
                    Log.d("Call request header", call.request().headers().toString());
                    Log.d("Response raw header", response.headers().toString());
                    Log.d("Response raw", String.valueOf(response.raw().body()));
                    Log.d("Response code", String.valueOf(response.code()));
                    if(response.isSuccessful()) {
                        //you can do whatever with the response body now...
                        String responseBodyString = response.body().getAnggota().toString();
                        Log.d("Response body", responseBodyString);

                        showAnggota(response.body().getAnggota());
                    }
                    else  {
                        Log.e("Response errorBody", String.valueOf(response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<AnggotaKeluargaResult> call, Throwable t) {
                    Log.e("Response Failure", String.valueOf(t.getMessage().toString()));
                }
            });
        }
    }

    private void showAnggota(List<AnggotaKeluarga> data){
        AdminAnggotaAdapter anggotaAdapter = new AdminAnggotaAdapter(this, R.layout.item_anggota, data);
        list_anggota = (ListView) findViewById(R.id.list_aanggota);
        list_anggota.setAdapter(anggotaAdapter);
        anggotaAdapter.notifyDataSetChanged();
    }
}