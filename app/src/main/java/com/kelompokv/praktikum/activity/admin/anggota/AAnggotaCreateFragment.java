package com.kelompokv.praktikum.activity.admin.anggota;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.kelompokv.praktikum.model.admin.User;
import com.kelompokv.praktikum.model.admin.UserList;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDAnggota;
import com.kelompokv.praktikum.network.service.AdminService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AAnggotaCreateFragment extends Fragment {
    View view;
    Integer id_keluarga;
    Spinner jenis_kelamin, tipe, spin_user, validasi;
    EditText nama, tempat_lahir, tanggal_lahir, agama, pendidikan, pekerjaan, ayah, ibu;
    Button btn_create;
    AdminService service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_anggota_create_fragment, container, false);

        id_keluarga = getArguments().getInt("id_keluarga");
        nama = view.findViewById(R.id.aacreate_nama);
        tempat_lahir = view.findViewById(R.id.aacreate_tempat_lahir);
        tanggal_lahir = view.findViewById(R.id.aacreate_tanggal_lahir);
        agama = view.findViewById(R.id.aacreate_agama);
        pendidikan = view.findViewById(R.id.aacreate_pendidikan);
        pekerjaan = view.findViewById(R.id.aacreate_pekerjaan);
        ayah = view.findViewById(R.id.aacreate_ayah);
        ibu = view.findViewById(R.id.aacreate_ibu);
        jenis_kelamin = view.findViewById(R.id.aacreate_jenis_kelamin);
        tipe = view.findViewById(R.id.aacreate_tipe);
        validasi = view.findViewById(R.id.aacreate_validasi);
        spin_user = (Spinner) view.findViewById(R.id.aacreate_id_user);
        btn_create = view.findViewById(R.id.aacreate_create);
        service = Client.getClient().create(AdminService.class);

        loadSpinnerUser();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAnggota(nama.getText().toString(), tempat_lahir.getText().toString(),
                        tanggal_lahir.getText().toString(), agama.getText().toString(),
                        pendidikan.getText().toString(), pekerjaan.getText().toString(),
                        ayah.getText().toString(), ibu.getText().toString(),
                        jenis_kelamin.getSelectedItem().toString(), tipe.getSelectedItem().toString(),
                        id_keluarga, spin_user.getSelectedItem().toString(), validasi.getSelectedItem().toString());
            }
        });

        return view;
    }

    private void loadSpinnerUser(){
        service = Client.getClient().create(AdminService.class);
        Call<UserList> userall = service.allUser();
        userall.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if(response.isSuccessful()) {
                    //you can do whatever with the response body now...
                    String responseBodyString= response.body().getUsers().toString();
                    Log.d("Response body", responseBodyString);

                    setSpinner(response.body().getUsers());
                }
                else  {
                    Log.e("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Log.e("Response errorDev", String.valueOf(t.getMessage()));
            }
        });
    }

    private void setSpinner(List<User> users){
        List<String> list_id = new ArrayList<String>();
        list_id.add("NULL");
        for (int i = 0; i < users.size(); i++){
            list_id.add(users.get(i).getEmail());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item, list_id);
        spin_user.setAdapter(spinnerAdapter);
    }

    private void createAnggota(String cnama, String ctempat, String ctanggal, String cagama,
                               String cpendidikan, String cpekerjaan, String cayah, String cibu,
                               String cjenis_kelamin, String ctipe, Integer id, String email_user,
                               String validasi){
        Call<CUDAnggota> create = service.createAnggota(
                cnama, cjenis_kelamin, ctempat, ctanggal, cagama, cpendidikan, cpekerjaan,
                ctipe, cayah, cibu, id, email_user, validasi
        );

        create.enqueue(new Callback<CUDAnggota>() {
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
                    Bundle bundle = new Bundle();
                    bundle.putInt("id_keluarga", id_keluarga);
                    loadFragment(new AAnggotaFragment(), bundle);
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

    private void loadFragment(Fragment fragment, @Nullable Bundle mBundle){
        if(mBundle != null){
            fragment.setArguments(mBundle);
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fl_admin_container, fragment);
        ft.commit();
    }
}
