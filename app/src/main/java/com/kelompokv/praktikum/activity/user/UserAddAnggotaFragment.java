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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDAnggota;
import com.kelompokv.praktikum.network.service.AnggotaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class UserAddAnggotaFragment extends Fragment {
    private Integer user_id;
    View view;
    Spinner c_jenis_kelamin, c_tipe;
    EditText c_nama, c_tempat_lahir, c_tanggal_lahir, c_agama, c_pendidikan, c_pekerjaan, c_ayah, c_ibu;
    Button btn_create;
    AnggotaService service;
    SharedPreferences auth_sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_add_anggota_fragment, container, false);

        c_nama = (EditText) view.findViewById(R.id.c_nama);
        c_tempat_lahir = (EditText) view.findViewById(R.id.c_tempat_lahir);
        c_tanggal_lahir = (EditText) view.findViewById(R.id.c_tanggal_lahir);
        c_agama = (EditText) view.findViewById(R.id.c_agama);
        c_pendidikan = (EditText) view.findViewById(R.id.c_pendidikan);
        c_pekerjaan = (EditText) view.findViewById(R.id.c_pekerjaan);
        c_ayah = (EditText) view.findViewById(R.id.c_ayah);
        c_ibu = (EditText) view.findViewById(R.id.c_ibu);
        c_jenis_kelamin = (Spinner) view.findViewById(R.id.c_jenis_kelamin);
        c_tipe = (Spinner) view.findViewById(R.id.c_tipe);

        service = Client.getClient().create(AnggotaService.class);

        auth_sp = view.getContext().getSharedPreferences("authSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        user_id = auth_sp.getInt("log_id", 0);

        btn_create = (Button) view.findViewById(R.id.btn_create_anggota);
        btn_create.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Call<CUDAnggota> storeAnggotaExe = service.storeAnggota(
                        c_nama.getText().toString(),
                        c_jenis_kelamin.getSelectedItem().toString(),
                        c_tempat_lahir.getText().toString(),
                        c_tanggal_lahir.getText().toString(),
                        c_agama.getText().toString(),
                        c_pendidikan.getText().toString(),
                        c_pekerjaan.getText().toString(),
                        c_tipe.getSelectedItem().toString(),
                        c_ayah.getText().toString(),
                        c_ibu.getText().toString(),
                        user_id
                );

                storeAnggotaExe.enqueue(new Callback<CUDAnggota>() {
                    @Override
                    public void onResponse(Call<CUDAnggota> call, Response<CUDAnggota> response) {
                        Log.d("Call request", call.request().toString());
                        Log.d("Call request header", call.request().headers().toString());
                        Log.d("Response raw header", response.headers().toString());
                        Log.d("Response raw", String.valueOf(response.raw().body()));
                        Log.d("Response code", String.valueOf(response.code()));

                        if (response.isSuccessful()) {
                            Toast.makeText(view.getContext(), "Create Anggota Berhasil",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Response body", response.body().getSuccess().toString());
                            loadFragment(new UserFragment());
                        } else {
                            Toast.makeText(view.getContext(), "Create Anggota Gagal",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Response body", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<CUDAnggota> call, Throwable t) {
                        Toast.makeText(view.getContext(), "Error Dev "+t.getMessage().toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("Response body", t.getMessage());
                    }
                });
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fl_container, fragment);
        ft.commit();
    }
}
