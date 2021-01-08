package com.kelompokv.praktikum.activity.admin.keluarga;

import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDKeluarga;
import com.kelompokv.praktikum.network.service.AdminService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AKeluargaCreateFragment extends Fragment {
    View view;
    Button btn_create;
    EditText alamat, rtrw, kodepos, kelurahan, kecamatan, kabupaten, provinsi;
    AdminService service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_keluarga_create_fragment, container, false);

        btn_create = view.findViewById(R.id.akcreate_create);
        alamat = view.findViewById(R.id.akcreate_alamat);
        rtrw = view.findViewById(R.id.akcreate_rtrw);
        kodepos = view.findViewById(R.id.akcreate_kodepos);
        kelurahan = view.findViewById(R.id.akcreate_kelurahan);
        kecamatan = view.findViewById(R.id.akcreate_kecamatan);
        kabupaten = view.findViewById(R.id.akcreate_kabupaten);
        provinsi = view.findViewById(R.id.akcreate_provinsi);
        service = Client.getClient().create(AdminService.class);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createKeluarga(alamat.getText().toString(), rtrw.getText().toString(),
                        kodepos.getText().toString(), kelurahan.getText().toString(),
                        kecamatan.getText().toString(), kabupaten.getText().toString(),
                        provinsi.getText().toString());
            }
        });

        return view;
    }

    private void createKeluarga(String calamat, String crtrw, String ckodepos, String ckelurahan,
                                String ckecamatan, String ckabupaten, String cprovinsi){

        Call<CUDKeluarga> create = service.createKeluarga(calamat, crtrw, ckodepos, ckelurahan,
                ckecamatan, ckabupaten, cprovinsi);

        create.enqueue(new Callback<CUDKeluarga>() {
            @Override
            public void onResponse(Call<CUDKeluarga> call, Response<CUDKeluarga> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));
                String code = String.valueOf(response.code());
                if(code.equals("401")){
                    Toast.makeText(view.getContext(), "Harap Isi Form dengan Lengkap",
                            Toast.LENGTH_SHORT).show();
                }else{
                    if (response.isSuccessful()) {
                        Toast.makeText(view.getContext(), "Create Keluarga Berhasil",
                                Toast.LENGTH_SHORT).show();
                        Log.d("Response body", response.body().getSuccess().toString());
                        loadFragment(new AKeluargaFragment());
                    } else {
                        Toast.makeText(view.getContext(), "Create Keluarga Gagal",
                                Toast.LENGTH_SHORT).show();
                        Log.d("Response body", response.body().getError().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<CUDKeluarga> call, Throwable t) {
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
