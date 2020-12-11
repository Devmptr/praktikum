package com.kelompokv.praktikum.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.model.auth.Register;
import com.kelompokv.praktikum.network.service.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button btn_register;
    TextView btn_view_login;
    EditText form_email,form_password,form_name;
    AuthService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = (Button) findViewById(R.id.register_submit_btn);
        btn_view_login = (TextView) findViewById(R.id.login_link);
        form_email = (EditText) findViewById(R.id.register_email_form);
        form_password = (EditText) findViewById(R.id.register_password_form);
        form_name = (EditText) findViewById(R.id.register_name_form);

        service = Client.getClient().create(AuthService.class);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Register> postRegisterExe = service.postRegister(form_name.getText().toString(),
                        form_email.getText().toString(), form_password.getText().toString(),
                        form_password.getText().toString());

                postRegisterExe.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        Log.d("Call request", call.request().toString());
                        Log.d("Call request header", call.request().headers().toString());
                        Log.d("Response raw header", response.headers().toString());
                        Log.d("Response raw", String.valueOf(response.raw().body()));
                        Log.d("Response code", String.valueOf(response.code()));
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Register Berhasil",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }else{
                            Log.e("Response failed", response.errorBody().toString());
                            Toast.makeText(getApplicationContext(), "Register Gagal",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_view_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}