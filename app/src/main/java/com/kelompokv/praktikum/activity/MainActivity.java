package com.kelompokv.praktikum.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.ViewAnggota;
import com.kelompokv.praktikum.activity.user.CreateData;
import com.kelompokv.praktikum.adapter.AnggotaAdapter;
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;
import com.kelompokv.praktikum.model.user.AnggotaKeluargaResult;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AnggotaService;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btn_logout, btn_create;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPrefs = getSharedPreferences("sq_shared_pref", MODE_PRIVATE);
        SharedPreferences.Editor ed;

        if (!sharedPrefs.contains("token")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        btn_create = (Button) findViewById(R.id.btn_create_data);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, CreateData.class));
            }
        });

        loadAnggota();

        listView = (ListView) findViewById(R.id.list_anggota);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ViewAnggota.class);
                AnggotaKeluarga item = (AnggotaKeluarga) adapterView.getItemAtPosition(i);
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        });
    }

    private void logout() {
        SharedPreferences sgSharedPref = getApplicationContext().getSharedPreferences("sg_shared_pref",
                getApplicationContext().MODE_PRIVATE);
        sgSharedPref.edit().clear().commit();

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void loadAnggota() {
        AnggotaService service = Client.getClient().create(AnggotaService.class);
        Call<AnggotaKeluargaResult> anggotas = service.getAnggota();
        anggotas.enqueue(new Callback<AnggotaKeluargaResult>() {
            @Override
            public void onResponse(Call<AnggotaKeluargaResult> call, Response<AnggotaKeluargaResult> response) {
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
            public void onFailure(Call<AnggotaKeluargaResult> call, Throwable t) {

            }
        });
    }

    public void show(List<AnggotaKeluarga> anggotas){
        AnggotaAdapter anggotaAdapter = new AnggotaAdapter(this, R.layout.item_anggota, anggotas);
        listView = (ListView) findViewById(R.id.list_anggota);
        listView.setAdapter(anggotaAdapter);
        anggotaAdapter.notifyDataSetChanged();
    }
}