package com.kelompokv.praktikum.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.kelompokv.praktikum.activity.admin.DashboardAdminActivity;
import com.kelompokv.praktikum.activity.user.MainActivity;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AuthService;

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
    AuthService service;
    String token, role;
    SharedPreferences auth_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkAuth();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.login_submit_btn);
        btn_view_register = (TextView) findViewById(R.id.register_link);
        form_email = (EditText) findViewById(R.id.login_email_form);
        form_password = (EditText) findViewById(R.id.login_password_form);
        service = Client.getClient().create(AuthService.class);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authLogin(form_email.getText().toString(), form_password.getText().toString());
            }
        });

        btn_view_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void checkAuth(){
        auth_sp = getApplicationContext().getSharedPreferences("authSharedPreferences",
                getApplicationContext().MODE_PRIVATE);
        if (auth_sp.contains("token")) {
            String check_role = auth_sp.getString("role", "");
            if (check_role.equals("user")){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else if(check_role.equals("admin")){
                startActivity(new Intent(LoginActivity.this, DashboardAdminActivity.class));
            }
        }
    }

    private void authLogin(String in_email, String in_password){
        Call<Login> postLoginExe = service.postLogin(in_email, in_password);

        postLoginExe.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login Berhasil",
                            Toast.LENGTH_SHORT).show();

                    auth_sp = getApplicationContext().getSharedPreferences("authSharedPreferences",
                            getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = auth_sp.edit();
                    token = String.valueOf(response.body().getSuccess().getToken());
                    role = String.valueOf(response.body().getRole());
                    System.out.print(role);
                    editor.putString("token", token);
                    editor.putString("role", role);
                    editor.apply();

                    if(role.equals("user")){
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else if(role.equals("admin")){
                        startActivity(new Intent(LoginActivity.this, DashboardAdminActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(), "Failed to Find Role",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Login Gagal",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error" + t,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}