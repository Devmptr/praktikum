package com.kelompokv.praktikum.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kelompokv.praktikum.R;

public class FirebaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        getCurrentFirebaseToken();
        Bundle bundle = getIntent().getExtras();

        TextView msg = findViewById(R.id.message);
        TextView title = findViewById(R.id.title);

        if(bundle != null){
            title.setText(bundle.getString("title"));
            msg.setText(bundle.getString("message"));
        }
    }

    private void getCurrentFirebaseToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()) {
                    Log.w("TAG", "getInstaceId failed", task.getException());
                    return;
                }

                String token = task.getResult().getToken();
                Log.e("currentToken", token);
            }
        });
    }
}