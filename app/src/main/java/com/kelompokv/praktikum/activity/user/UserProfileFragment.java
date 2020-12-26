package com.kelompokv.praktikum.activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.model.admin.User;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDUser;
import com.kelompokv.praktikum.network.response.SERUser;
import com.kelompokv.praktikum.network.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class UserProfileFragment extends Fragment {
    View view;
    SharedPreferences auth_sp;
    Integer user_id;
    UserService service;
    EditText name, email, password;
    Button btn_update;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_profile_fragment, container, false);

        auth_sp = view.getContext().getSharedPreferences("authSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        user_id = auth_sp.getInt("log_id", 0);
        service = Client.getClient().create(UserService.class);
        name = view.findViewById(R.id.profile_name);
        email = view.findViewById(R.id.profile_email);
        password = view.findViewById(R.id.profile_password);
        btn_update = view.findViewById(R.id.btn_profile_update);

        viewUser(user_id);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(user_id, name.getText().toString(), email.getText().toString(),
                        password.getText().toString());
            }
        });

        return view;
    }

    private void updateProfile(Integer pid, String pname, String pemail, String ppassword){
        Call<CUDUser> show = service.updateUser(pid, pname, pemail, ppassword);
        show.enqueue(new Callback<CUDUser>() {
            @Override
            public void onResponse(Call<CUDUser> call, Response<CUDUser> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));
                if(response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Update Profile Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                }
                else  {
                    Toast.makeText(view.getContext(), "Update Profile Gagal",
                            Toast.LENGTH_SHORT).show();
                    Log.e("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<CUDUser> call, Throwable t) {
                Log.e("Response Failure", String.valueOf(t.getMessage().toString()));
            }
        });
    }

    private void viewUser(Integer id){
        if(id != 0){
            Call<SERUser> show = service.showUser(id);
            show.enqueue(new Callback<SERUser>() {
                @Override
                public void onResponse(Call<SERUser> call, Response<SERUser> response) {
                    Log.d("Call request", call.request().toString());
                    Log.d("Call request header", call.request().headers().toString());
                    Log.d("Response raw header", response.headers().toString());
                    Log.d("Response raw", String.valueOf(response.raw().body()));
                    Log.d("Response code", String.valueOf(response.code()));
                    if(response.isSuccessful()) {
                        //you can do whatever with the response body now...
                        String responseBodyString= response.body().getUser().toString();
                        Log.d("Response body", responseBodyString);

                        showUser(response.body().getUser());
                    }
                    else  {
                        Log.e("Response errorBody", String.valueOf(response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<SERUser> call, Throwable t) {
                    Log.e("Response Failure", String.valueOf(t.getMessage().toString()));
                }
            });
        }
    }

    private void showUser(User data){
        name.setText(data.getName());
        email.setText(data.getEmail());
    }
}
