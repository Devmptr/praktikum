package com.kelompokv.praktikum.activity.admin.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.activity.user.UserViewFragment;
import com.kelompokv.praktikum.adapter.UserAdapter;
import com.kelompokv.praktikum.model.admin.User;
import com.kelompokv.praktikum.model.admin.UserList;
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AdminService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AUserFragment extends Fragment {
    AdminService service;
    ListView user_list, list_user;
    FloatingActionButton a_user_create;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_user_index_fragment, container, false);

        loadUser();
        a_user_create = view.findViewById(R.id.btn_auser_add);
        list_user = view.findViewById(R.id.alist_user);

        a_user_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AUserCreateFragment(), null);
            }
        });

        list_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User item = (User) adapterView.getItemAtPosition(i);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                loadFragment(new AUserShowFragment(), bundle);
            }
        });
        return view;
    }

    private void loadFragment(Fragment fragment, @Nullable Bundle mBundle){
        if(mBundle != null){
            fragment.setArguments(mBundle);
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fl_admin_container, fragment);
        ft.commit();
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
        UserAdapter adapter = new UserAdapter(view.getContext(), R.layout.item_user, users);
        user_list = (ListView) view.findViewById(R.id.alist_user);
        user_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
