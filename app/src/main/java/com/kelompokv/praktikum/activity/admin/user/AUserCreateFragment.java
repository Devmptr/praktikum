package com.kelompokv.praktikum.activity.admin.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDUser;
import com.kelompokv.praktikum.network.service.AdminService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AUserCreateFragment extends Fragment {
    View view;

    Button btn_create;
    EditText name, email, password;
    Spinner role;
    AdminService service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_user_create_fragment, container, false);

        btn_create = view.findViewById(R.id.aucreate_add);
        name = view.findViewById(R.id.aucreate_name);
        email = view.findViewById(R.id.aucreate_email);
        password = view.findViewById(R.id.aucreate_password);
        role = view.findViewById(R.id.aucreate_role);
        service = Client.getClient().create(AdminService.class);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(name.getText().toString(), email.getText().toString(),
                        password.getText().toString(), role.getSelectedItem().toString());
            }
        });

        return view;
    }

    private void createUser(String cname, String cemail, String cpass, String crole){
        Call<CUDUser> create = service.createUser(cname, cemail, cpass, crole);

        create.enqueue(new Callback<CUDUser>() {
            @Override
            public void onResponse(Call<CUDUser> call, Response<CUDUser> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Create User Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    loadFragment(new AUserFragment());
                } else {
                    Toast.makeText(view.getContext(), "Create User Gagal",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CUDUser> call, Throwable t) {
                Toast.makeText(view.getContext(), "Error Dev "+t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Response body", t.getMessage());
            }
        });
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fl_admin_container, fragment);
        ft.commit();
    }
}
