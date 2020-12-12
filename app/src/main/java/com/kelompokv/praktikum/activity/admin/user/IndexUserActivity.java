package com.kelompokv.praktikum.activity.admin.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.adapter.UserAdapter;
import com.kelompokv.praktikum.model.admin.User;
import com.kelompokv.praktikum.model.admin.UserList;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AdminService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexUserActivity extends AppCompatActivity {
    AdminService service;
    ListView user_list, list_user;
    Button a_user_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_user);

        a_user_create = findViewById(R.id.a_user_create);

        loadUser();

        list_user = findViewById(R.id.alist_user);

        a_user_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IndexUserActivity.this, CreateUserActivity.class));
            }
        });

        list_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(IndexUserActivity.this, ShowUserActivity.class);
                User item = (User) adapterView.getItemAtPosition(i);
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        });
    }

    private void loadUser(){
        service = Client.getClient().create(AdminService.class);
        Call<UserList> userall = service.allUser();
        userall.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if(response.isSuccessful()) {
                    //you can do whatever with the response body now...
                    String responseBodyString= response.body().getUsers().toString();
                    Log.d("Response body", responseBodyString);

                    showUser(response.body().getUsers());
                }
                else  {
                    Log.e("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Log.e("Response errorDev", String.valueOf(t.getMessage()));
            }
        });
    }

    private void showUser(List<User> users){
        UserAdapter adapter = new UserAdapter(this, R.layout.item_user, users);
        user_list = (ListView) findViewById(R.id.alist_user);
        user_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}