package com.kelompokv.praktikum.activity.admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.activity.admin.keluarga.AKeluargaFragment;
import com.kelompokv.praktikum.activity.admin.user.AUserFragment;

import static android.content.Context.MODE_PRIVATE;

public class AdminFragment extends Fragment {
    View view;
    Button btn_admin_user, btn_admin_keluarga;
    SharedPreferences auth_sp;
    Integer user_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_fragment, container, false);

        btn_admin_user = view.findViewById(R.id.btn_admin_user);
        btn_admin_keluarga = view.findViewById(R.id.btn_admin_keluarga);

        auth_sp = view.getContext().getSharedPreferences("authSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        user_id = auth_sp.getInt("log_id", 0);

        btn_admin_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AUserFragment());
//                startActivity(new Intent(DashboardAdminActivity.this, IndexUserActivity.class));
            }
        });

        btn_admin_keluarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AKeluargaFragment());
//                startActivity(new Intent(DashboardAdminActivity.this, IndexKeluargaActivity.class));
            }
        });
        return view;
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fl_admin_container, fragment);
        ft.commit();
    }

}
