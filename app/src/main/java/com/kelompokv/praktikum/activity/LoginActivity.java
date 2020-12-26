package com.kelompokv.praktikum.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kelompokv.praktikum.activity.admin.DashboardAdminActivity;
import com.kelompokv.praktikum.activity.user.FirstLoginActivity;
import com.kelompokv.praktikum.activity.user.MainUser;
import com.kelompokv.praktikum.db.helper.DbHelper;
import com.kelompokv.praktikum.model.auth.FBToken;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AuthService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
    String token, role, token_firebase;
    Boolean is_profile;
    SharedPreferences auth_sp;
    Integer user_id;
    DbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.login_submit_btn);
        btn_view_register = (TextView) findViewById(R.id.register_link);
        form_email = (EditText) findViewById(R.id.login_email_form);
        form_password = (EditText) findViewById(R.id.login_password_form);
        service = Client.getClient().create(AuthService.class);
        helper = new DbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        getCurrentFirebaseToken();
        Bundle bundle = getIntent().getExtras();

        TextView msg = findViewById(R.id.message);
        TextView title = findViewById(R.id.title);

        if(bundle != null){
            title.setText(bundle.getString("title"));
            msg.setText(bundle.getString("message"));
        }

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
                    is_profile = response.body().getIs_profile();
                    user_id = response.body().getId();

                    editor.putString("token", token);
                    editor.putString("role", role);
                    editor.putInt("log_id", user_id);
                    editor.apply();

                    setFBToken(user_id, token_firebase);

                    if(role.equals("user")){
                        Log.d("is Profile", is_profile.toString());
                        if (is_profile) {
                            startActivity(new Intent(LoginActivity.this, MainUser.class));
                        }else{
                            startActivity(new Intent(LoginActivity.this, FirstLoginActivity.class));
                        }
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

    private void getCurrentFirebaseToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()) {
                    Log.w("TAG", "getInstaceId failed", task.getException());
                    return;
                }

                token_firebase = task.getResult().getToken();
                Log.e("currentToken", token_firebase);
            }
        });
    }

    private void setFBToken(Integer id_u, String token_fb){
        Call<FBToken> setToken = service.setFBToken(id_u, token_fb);

        setToken.enqueue(new Callback<FBToken>() {
            @Override
            public void onResponse(Call<FBToken> call, Response<FBToken> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Set Token Berhasil",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Set Token Gagal",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FBToken> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error fb token " + t,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}