package com.kelompokv.praktikum.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.db.helper.DbHelper;
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.response.CUDAnggota;
import com.kelompokv.praktikum.network.response.SERAnggota;
import com.kelompokv.praktikum.network.service.AnggotaService;

import java.text.DateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewFragment extends Fragment {
    View view;
    Integer id;
    Spinner u_jenis_kelamin, u_tipe;
    EditText u_nama, u_tempat_lahir, u_tanggal_lahir, u_agama, u_pendidikan, u_pekerjaan, u_ayah, u_ibu;
    Button btn_update, btn_delete;
    AnggotaService service;
    DbHelper helper;
    ImageView statvalid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_view_fragment, container, false);

        id = getArguments().getInt("id");
        if(id != null){
            viewAnggota(id);

            helper = new DbHelper(view.getContext());

            u_nama = (EditText) view.findViewById(R.id.u_nama);
            u_tempat_lahir = (EditText) view.findViewById(R.id.u_tempat_lahir);
            u_tanggal_lahir = (EditText) view.findViewById(R.id.u_tanggal_lahir);
            u_agama = (EditText) view.findViewById(R.id.u_agama);
            u_pendidikan = (EditText) view.findViewById(R.id.u_pendidikan);
            u_pekerjaan = (EditText) view.findViewById(R.id.u_pekerjaan);
            u_ayah = (EditText) view.findViewById(R.id.u_ayah);
            u_ibu = (EditText) view.findViewById(R.id.u_ibu);
            u_jenis_kelamin = (Spinner) view.findViewById(R.id.u_jenis_kelamin);
            u_tipe = (Spinner) view.findViewById(R.id.u_tipe);
            statvalid = (ImageView) view.findViewById(R.id.user_view_status_valid);
        }

        btn_delete = (Button) view.findViewById(R.id.btn_delete_anggota);
        btn_delete.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Call<CUDAnggota> deleteAnggotaExe = service.deleteAnggota(id);

                deleteAnggotaExe.enqueue(new Callback<CUDAnggota>() {
                    @Override
                    public void onResponse(Call<CUDAnggota> call, Response<CUDAnggota> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(view.getContext(), "Delete Anggota Berhasil",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Response body", response.body().getSuccess().toString());
                            loadFragment(new UserFragment());
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
        });

        btn_update = (Button) view.findViewById(R.id.btn_update_anggota);
        btn_update.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Call<CUDAnggota> updateAnggotaExe = service.updateAnggota(
                        id,
                        u_nama.getText().toString(),
                        u_jenis_kelamin.getSelectedItem().toString(),
                        u_tempat_lahir.getText().toString(),
                        u_tanggal_lahir.getText().toString(),
                        u_agama.getText().toString(),
                        u_pendidikan.getText().toString(),
                        u_pekerjaan.getText().toString(),
                        u_tipe.getSelectedItem().toString(),
                        u_ayah.getText().toString(),
                        u_ibu.getText().toString()
                );

                updateAnggotaExe.enqueue(new Callback<CUDAnggota>() {
                    @Override
                    public void onResponse(Call<CUDAnggota> call, Response<CUDAnggota> response) {
                        Log.d("Call request", call.request().toString());
                        Log.d("Call request header", call.request().headers().toString());
                        Log.d("Response raw header", response.headers().toString());
                        Log.d("Response raw", String.valueOf(response.raw().body()));
                        Log.d("Response code", String.valueOf(response.code()));

                        if (response.isSuccessful()) {
                            Toast.makeText(view.getContext(), "Update Anggota Berhasil",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Response body", response.body().getSuccess().toString());
                            loadFragment(new UserFragment());
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
        });

        return view;
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fl_container, fragment);
        ft.commit();
    }

    private void viewAnggota(final Integer anggota){
        service = Client.getClient().create(AnggotaService.class);
        Call<SERAnggota> anggotas = service.editAnggota(anggota);
        anggotas.enqueue(new Callback<SERAnggota>() {
            @Override
            public void onResponse(Call<SERAnggota> call, Response<SERAnggota> response) {
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));
                if(response.isSuccessful()) {
                    //you can do whatever with the response body now...
                    String responseBodyString= response.body().getAnggota().toString();
                    Log.d("Response body", responseBodyString);

                    show(response.body().getAnggota());
                }
                else  {
                    Log.d("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<SERAnggota> call, Throwable t) {
                show(helper.getSelectAnggota(anggota));
            }
        });
    }

    private void show(AnggotaKeluarga anggotas){
        DateFormat date = DateFormat.getDateInstance();
        u_nama.setText(anggotas.getNama());
        u_tempat_lahir.setText(anggotas.getTempatlahir());
        u_tanggal_lahir.setText(date.format(anggotas.getTanggallahir()).toString());
        u_agama.setText(anggotas.getAgama());
        u_pendidikan.setText(anggotas.getPendidikan());
        u_pekerjaan.setText(anggotas.getPekerjaan());
        u_ayah.setText(anggotas.getAyah());
        u_ibu.setText(anggotas.getIbu());

        String getValid = anggotas.getValidated();
        if(getValid.equals("validated")){
            statvalid.setImageResource(R.drawable.ic_success);
        }else if(getValid.equals("process")){
            statvalid.setImageResource(R.drawable.ic_process);
        }else if(getValid.equals("problem")){
            statvalid.setImageResource(R.drawable.ic_error);
        }else{
            statvalid.setImageResource(R.drawable.ic_error);
        }
    }
}
