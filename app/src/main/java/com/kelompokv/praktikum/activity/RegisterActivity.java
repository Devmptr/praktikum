package com.kelompokv.praktikum.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.api.Client;
import com.kelompokv.praktikum.api.Interface;
import com.kelompokv.praktikum.model.auth.Login;
import com.kelompokv.praktikum.model.auth.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button btn_register;
    TextView btn_view_login;
    EditText form_email,form_password,form_name;
    Interface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = (Button) findViewById(R.id.register_submit_btn);
        btn_view_login = (TextView) findViewById(R.id.login_link);
        form_email = (EditText) findViewById(R.id.register_email_form);
        form_password = (EditText) findViewById(R.id.register_password_form);
        form_name = (EditText) findViewById(R.id.register_name_form);

        mApiInterface = Client.getClient().create(Interface.class);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Register> postRegisterExe = mApiInterface.postRegister(form_name.getText().toString(),
                        form_email.getText().toString(), form_password.getText().toString(),
                        form_password.getText().toString());

                postRegisterExe.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Register Berhasil",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }else{
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