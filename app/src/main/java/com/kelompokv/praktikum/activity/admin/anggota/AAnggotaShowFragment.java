package com.kelompokv.praktikum.activity.admin.anggota;

import android.content.Intent;
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
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDAnggota;
import com.kelompokv.praktikum.network.response.SERAnggota;
import com.kelompokv.praktikum.network.service.AdminService;

import java.text.DateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AAnggotaShowFragment extends Fragment {
    View view;
    Bundle extras;
    Integer id_anggota, id_keluarga;
    Spinner jenis_kelamin, tipe, validasi;
    EditText nama, tempat_lahir, tanggal_lahir, agama, pendidikan, pekerjaan, ayah, ibu;
    Button btn_update, btn_delete;
    AdminService service;
    String tfb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_anggota_show_fragment, container, false);

        id_anggota = getArguments().getInt("id_anggota");
        id_keluarga = getArguments().getInt("id_keluarga");
        Log.e("ID KELUARGA", id_keluarga.toString());

        nama = view.findViewById(R.id.aaupdate_nama);
        tempat_lahir = view.findViewById(R.id.aaupdate_tempat_lahir);
        tanggal_lahir = view.findViewById(R.id.aaupdate_tanggal_lahir);
        agama = view.findViewById(R.id.aaupdate_agama);
        pendidikan = view.findViewById(R.id.aaupdate_pendidikan);
        pekerjaan = view.findViewById(R.id.aaupdate_pekerjaan);
        ayah = view.findViewById(R.id.aaupdate_ayah);
        ibu = view.findViewById(R.id.aaupdate_ibu);
        jenis_kelamin = view.findViewById(R.id.aaupdate_jenis_kelamin);
        tipe = view.findViewById(R.id.aaupdate_tipe);
        validasi = view.findViewById(R.id.aaupdate_validasi);
        btn_update = view.findViewById(R.id.aaupdate_update);
        btn_delete = view.findViewById(R.id.aaupdate_delete);
        service = Client.getClient().create(AdminService.class);

        viewAnggota(id_anggota);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAnggota(id_anggota, nama.getText().toString(), tempat_lahir.getText().toString(),
                        tanggal_lahir.getText().toString(), agama.getText().toString(),
                        pendidikan.getText().toString(), pekerjaan.getText().toString(),
                        ayah.getText().toString(), ibu.getText().toString(),
                        jenis_kelamin.getSelectedItem().toString(), tipe.getSelectedItem().toString(),
                        id_keluarga, validasi.getSelectedItem().toString());
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAnggota(id_anggota);
            }
        });

        return view;
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

    private void updateAnggota(Integer id_ang, String cnama, String ctempat, String ctanggal, String cagama,
                               String cpendidikan, String cpekerjaan, String cayah, String cibu,
                               String cjenis_kelamin, String ctipe, Integer id_kel, String validasi){
        Call<CUDAnggota> update = service.updateAnggota(
                id_ang, cnama, cjenis_kelamin, ctempat, ctanggal, cagama, cpendidikan, cpekerjaan,
                ctipe, cayah, cibu, id_kel, validasi
        );

        final String test = validasi;
        final Integer idkeluarga = id_kel;

        update.enqueue(new Callback<CUDAnggota>() {
            @Override
            public void onResponse(Call<CUDAnggota> call, Response<CUDAnggota> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));
                Log.e("ID KELUARGA", String.valueOf(idkeluarga));

                if (response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Update Anggota Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    Log.d("TFB", String.valueOf(tfb.isEmpty()));
                    System.out.print(String.valueOf(tfb.isEmpty()));
                    if(!tfb.isEmpty()){
                        sendNotifUpdate("Status Update", "Validasi Anggota : "+test, tfb);
                    }
                    extras = new Bundle();
                    extras.putInt("id_keluarga", idkeluarga);
                    loadFragment(new AAnggotaFragment(), extras);
                } else {
                    Toast.makeText(view.getContext(), "Update Anggota Gagal",
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

    private void deleteAnggota(Integer id_ang){
        Call<CUDAnggota> create = service.deleteAnggota(id_ang);

        create.enqueue(new Callback<CUDAnggota>() {
            @Override
            public void onResponse(Call<CUDAnggota> call, Response<CUDAnggota> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Delete Anggota Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    extras.putInt("id_keluarga", id_keluarga);
                    loadFragment(new AAnggotaFragment(), extras);
                } else {
                    Toast.makeText(view.getContext(), "Delete Anggota Gagal",
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

    private void sendNotifUpdate(String title, String msg, String token){
        Call<CUDAnggota> send = service.sendNotifUpdate(title, msg, token);

        send.enqueue(new Callback<CUDAnggota>() {
            @Override
            public void onResponse(Call<CUDAnggota> call, Response<CUDAnggota> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Send Notif Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                } else {
                    Toast.makeText(view.getContext(), "Send Notif Gagal",
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

    private void viewAnggota(Integer id_ang){
        Call<SERAnggota> show = service.showAnggota(id_ang);

        show.enqueue(new Callback<SERAnggota>() {
            @Override
            public void onResponse(Call<SERAnggota> call, Response<SERAnggota> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Log.d("Response body", response.body().getAnggota().toString());
                    showAnggota(response.body().getAnggota(), response.body().getToken_fb());
                } else {
                    Log.d("Response body", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<SERAnggota> call, Throwable t) {
                Toast.makeText(view.getContext(), "Error Dev "+t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Response body", t.getMessage());
            }
        });
    }

    private void showAnggota(AnggotaKeluarga data, String token_fb){
        Integer index_jk, index_tipe, index_validate, index_email_user;
        DateFormat date = DateFormat.getDateInstance();
        nama.setText(data.getNama());
        tempat_lahir.setText(data.getTempatlahir());
        tanggal_lahir.setText(date.format(data.getTanggallahir()));
        agama.setText(data.getAgama());
        pendidikan.setText(data.getPendidikan());
        pekerjaan.setText(data.getPekerjaan());
        ayah.setText(data.getAyah());
        ibu.setText(data.getIbu());
        index_jk = indexSpinner(jenis_kelamin, data.getJeniskelamin());
        jenis_kelamin.setSelection(index_jk);
        index_tipe = indexSpinner(tipe, data.getTipe());
        tipe.setSelection(index_tipe);
        index_validate = indexSpinner(validasi, data.getValidated());
        validasi.setSelection(index_validate);
        tfb = token_fb;
    }

    private int indexSpinner(Spinner spin, String text){
        for (int i = 0; i < spin.getCount(); i++){
            if (spin.getItemAtPosition(i).toString().equalsIgnoreCase(text)){
                return i;
            }
        }
        return 0;
    }
}
