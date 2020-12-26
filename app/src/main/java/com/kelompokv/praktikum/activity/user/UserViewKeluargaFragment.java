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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.model.admin.Keluarga;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDKeluarga;
import com.kelompokv.praktikum.network.response.SERKeluarga;
import com.kelompokv.praktikum.network.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class UserViewKeluargaFragment extends Fragment {
    View view;
    SharedPreferences auth_sp;
    Integer user_id;
    UserService service;
    EditText alamat, rtrw, kodepos, kelurahan, kecamatan, kabupaten, provinsi;
    Button btn_update;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_view_keluarga_fragment, container, false);


        auth_sp = view.getContext().getSharedPreferences("authSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        user_id = auth_sp.getInt("log_id", 0);
        service = Client.getClient().create(UserService.class);
        alamat = view.findViewById(R.id.ukeluarga_alamat);
        rtrw = view.findViewById(R.id.ukeluarga_rtrw);
        kodepos = view.findViewById(R.id.ukeluarga_kodepos);
        kelurahan = view.findViewById(R.id.ukeluarga_kelurahan);
        kecamatan = view.findViewById(R.id.ukeluarga_kecamatan);
        kabupaten = view.findViewById(R.id.ukeluarga_kabupaten);
        provinsi = view.findViewById(R.id.ukeluarga_provinsi);
        btn_update = view.findViewById(R.id.ukeluarga_update);

        viewKeluarga(user_id);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKeluarga(user_id, alamat.getText().toString(), rtrw.getText().toString(),
                        kodepos.getText().toString(), kelurahan.getText().toString(),
                        kecamatan.getText().toString(), kabupaten.getText().toString(),
                        provinsi.getText().toString());
            }
        });

        return view;
    }

    private void updateKeluarga(Integer uid, String ualamat, String urtrw, String ukodepos,
                                String ukelurahan, String ukecamatan, String ukabupaten, String uprovinsi){

        Call<CUDKeluarga> show = service.updateKeluarga(uid, ualamat, urtrw, ukodepos, ukelurahan,
                ukecamatan, ukabupaten, uprovinsi);
        show.enqueue(new Callback<CUDKeluarga>() {
            @Override
            public void onResponse(Call<CUDKeluarga> call, Response<CUDKeluarga> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));
                if(response.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Update Keluarga Berhasil",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Response body", response.body().getSuccess().toString());
                }
                else  {
                    Toast.makeText(view.getContext(), "Update Keluarga Gagal",
                            Toast.LENGTH_SHORT).show();
                    Log.e("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<CUDKeluarga> call, Throwable t) {
                Log.e("Response Failure", String.valueOf(t.getMessage().toString()));
            }
        });
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
                        String responseBodyString= response.body().getKeluarga().toString();
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
}
