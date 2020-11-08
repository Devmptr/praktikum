package com.kelompokv.praktikum.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.kelompokv.praktikum.api.Client;
import com.kelompokv.praktikum.api.Interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.model.auth.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    TextView btn_view_register;
    EditText form_email,form_password;
    Interface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.login_submit_btn);
        btn_view_register = (TextView) findViewById(R.id.login_register_link);
        form_email = (EditText) findViewById(R.id.login_email_form);
        form_password = (EditText) findViewById(R.id.login_password_form);

        mApiInterface = Client.getClient().create(Interface.class);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Login> postLoginExe = mApiInterface.postLogin(form_email.getText().toString(),
                        form_password.getText().toString());

                postLoginExe.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Berhasil",
                                    Toast.LENGTH_SHORT).show();

                            SharedPreferences sgSharedPref = getApplicationContext().
                                    getSharedPreferences("sq_shared_pref", getApplicationContext().MODE_PRIVATE);
                            SharedPreferences.Editor editor = sgSharedPref.edit();
                            String token = String.valueOf(response.body().getSuccess().getToken());
                            editor.putString("token", token);
                            editor.apply();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Login Gagal",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_view_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}