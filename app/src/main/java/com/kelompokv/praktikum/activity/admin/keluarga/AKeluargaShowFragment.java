package com.kelompokv.praktikum.activity.admin.keluarga;

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
import com.kelompokv.praktikum.activity.admin.anggota.AAnggotaFragment;
import com.kelompokv.praktikum.model.admin.Keluarga;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDKeluarga;
import com.kelompokv.praktikum.network.response.SERKeluarga;
import com.kelompokv.praktikum.network.service.AdminService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AKeluargaShowFragment extends Fragment {
    View view;
    Integer id_keluarga;
    AdminService service;
    EditText alamat, rtrw, kodepos, kelurahan, kecamatan, kabupaten, provinsi;
    Button btn_update, btn_delete, btn_lihat_anggota;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_keluarga_show_fragment, container, false);

        btn_update = view.findViewById(R.id.akupdate_update);
        btn_delete = view.findViewById(R.id.akupdate_delete);
        btn_lihat_anggota = view.findViewById(R.id.akupdate_anggota);
        alamat = view.findViewById(R.id.akupdate_alamat);
        rtrw = view.findViewById(R.id.akupdate_rtrw);
        kodepos = view.findViewById(R.id.akupdate_kodepos);
        kelurahan = view.findViewById(R.id.akupdate_kelurahan);
        kecamatan = view.findViewById(R.id.akupdate_kecamatan);
        kabupaten = view.findViewById(R.id.akupdate_kabupaten);
        provinsi = view.findViewById(R.id.akupdate_provinsi);

        id_keluarga = getArguments().getInt("id");
        service = Client.getClient().create(AdminService.class);

        viewKeluarga(id_keluarga);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKeluarga(id_keluarga, alamat.getText().toString(), rtrw.getText().toString(),
                        kodepos.getText().toString(), kelurahan.getText().toString(),
                        kecamatan.getText().toString(), kabupaten.getText().toString(),
                        provinsi.getText().toString());
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteKeluarga(id_keluarga);
            }
        });

        btn_lihat_anggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_keluarga", id_keluarga);
                loadFragment(new AAnggotaFragment(), bundle);
            }
        });
        return view;
    }


    private void viewKeluarga(Integer id){
        if(id != 0){
            Call<SERKeluarga> show = service.showKeluarga(id);
            show.enqueue(new Callback<SERKeluarga>() {
                @Override
                public void onResponse(Call<SERKeluarga> call, Response<SERKeluarga> response) {
                    Log.d("Call request", call.request().toString());
                    Log.d("Call request header", call.request().headers().toString());
                    Log.d("Response raw header", response.headers().toString());
                    Log.d("Response raw", String.valueOf(response.raw().body()));
                    Log.d("Response code", String.valueOf(response.code()));
                    if(response.isSuccessful()) {
                        //you can do whatever with the response body now...
                        String responseBodyString = response.body().getKeluarga().toString();
                        Log.d("Response body", responseBodyString);

                        showKeluarga(response.body().getKeluarga());
                    }
                    else  {
                        Log.e("Response errorBody", String.valueOf(response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<SERKeluarga> call, Throwable t) {
                    Log.e("Response Failure", String.valueOf(t.getMessage().toString()));
                }
            });
        }
    }

    private void showKeluarga(Keluarga data){
        alamat.setText(data.getAlamat());
        rtrw.setText(data.getRtrw());
        kodepos.setText(data.getKodepos());
        kelurahan.setText(data.getKelurahan());
        kecamatan.setText(data.getKecamatan());
        kabupaten.setText(data.getKabupaten());
        provinsi.setText(data.getProvinsi());
    }


    private void updateKeluarga(Integer id, String ualamat, String urtrw, String ukodepos,
                                String ukelurahan, String ukecamatan, String ukabupaten,
                                String uprovinsi){

        Call<CUDKeluarga> update = service.updateKeluarga(id, ualamat, urtrw, ukodepos, ukelurahan,
                ukecamatan, ukabupaten, uprovinsi);

        update.enqueue(new Callback<CUDKeluarga>() {
            @Override
            public void onResponse(Call<CUDKeluarga> call, Response<CUDKeluarga> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Update Keluarga Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    loadFragment(new AKeluargaFragment(), null);
                } else {
                    Toast.makeText(view.getContext(), "Update Keluarga Gagal",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getError().toString());
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

    private void deleteKeluarga(Integer id){

        Call<CUDKeluarga> delete = service.deleteKeluarga(id);

        delete.enqueue(new Callback<CUDKeluarga>() {
            @Override
            public void onResponse(Call<CUDKeluarga> call, Response<CUDKeluarga> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Delete Keluarga Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                    loadFragment(new AKeluargaFragment(), null);
                } else {
                    Toast.makeText(view.getContext(), "Delete Keluarga Gagal",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getError().toString());
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
