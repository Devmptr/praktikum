package com.kelompokv.praktikum.activity.admin.keluarga;

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
import com.kelompokv.praktikum.activity.admin.user.CreateUserActivity;
import com.kelompokv.praktikum.activity.admin.user.IndexUserActivity;
import com.kelompokv.praktikum.activity.admin.user.ShowUserActivity;
import com.kelompokv.praktikum.adapter.KeluargaAdapter;
import com.kelompokv.praktikum.adapter.UserAdapter;
import com.kelompokv.praktikum.model.admin.Keluarga;
import com.kelompokv.praktikum.model.admin.KeluargaList;
import com.kelompokv.praktikum.model.admin.User;
import com.kelompokv.praktikum.model.admin.UserList;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AdminService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexKeluargaActivity extends AppCompatActivity {
    AdminService service;
    ListView keluarga_list, list_keluarga;
    Button btn_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_keluarga);

        btn_create = findViewById(R.id.a_keluarga_create);

        loadKeluarga();

        list_keluarga = findViewById(R.id.alist_keluarga);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IndexKeluargaActivity.this, CreateKeluargaActivity.class));
            }
        });

        list_keluarga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(IndexKeluargaActivity.this, ShowKeluargaActivity.class);
                Keluarga item = (Keluarga) adapterView.getItemAtPosition(i);
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        });
    }

    private void loadKeluarga(){
        service = Client.getClient().create(AdminService.class);
        Call<KeluargaList> userall = service.allKeluarga();
        userall.enqueue(new Callback<KeluargaList>() {
            @Override
            public void onResponse(Call<KeluargaList> call, Response<KeluargaList> response) {
                if(response.isSuccessful()) {
                    //you can do whatever with the response body now...
                    String responseBodyString= response.body().getKeluargas().toString();
                    Log.d("Response body", responseBodyString);

                    showUser(response.body().getKeluargas());
                }
                else  {
                    Log.e("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<KeluargaList> call, Throwable t) {
                Log.e("Response errorDev", String.valueOf(t.getMessage()));
            }
        });
    }

    private void showUser(List<Keluarga> data){
        KeluargaAdapter adapter = new KeluargaAdapter(this, R.layout.item_keluarga, data);
        keluarga_list = (ListView) findViewById(R.id.alist_keluarga);
        keluarga_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}