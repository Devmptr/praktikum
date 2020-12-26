package com.kelompokv.praktikum.activity.admin.anggota;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.adapter.AdminAnggotaAdapter;
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;
import com.kelompokv.praktikum.model.user.AnggotaKeluargaResult;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AdminService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AAnggotaFragment extends Fragment {
    View view;
    Integer id_keluarga;
    AdminService service;
    ListView list_anggota, item_anggota;
    FloatingActionButton btn_add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_anggota_index_fragment, container, false);

        service = Client.getClient().create(AdminService.class);
        id_keluarga = getArguments().getInt("id_keluarga");
        item_anggota = view.findViewById(R.id.list_aanggota);
        btn_add = view.findViewById(R.id.btn_aanggota_add);

        loadAnggota(id_keluarga);

        item_anggota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AnggotaKeluarga item = (AnggotaKeluarga) adapterView.getItemAtPosition(i);
                Bundle extras = new Bundle();
                extras.putInt("id_anggota", item.getId());
                extras.putInt("id_keluarga", id_keluarga);
                loadFragment(new AAnggotaShowFragment(), extras);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putInt("id_keluarga", id_keluarga);
                loadFragment(new AAnggotaCreateFragment(), extras);
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

    private void loadAnggota(Integer id){
        if(id != 0){
            Call<AnggotaKeluargaResult> show = service.anggotaKeluarga(id);
            show.enqueue(new Callback<AnggotaKeluargaResult>() {
                @Override
                public void onResponse(Call<AnggotaKeluargaResult> call, Response<AnggotaKeluargaResult> response) {
                    Log.d("Call request", call.request().toString());
                    Log.d("Call request header", call.request().headers().toString());
                    Log.d("Response raw header", response.headers().toString());
                    Log.d("Response raw", String.valueOf(response.raw().body()));
                    Log.d("Response code", String.valueOf(response.code()));
                    if(response.isSuccessful()) {
                        //you can do whatever with the response body now...
                        String responseBodyString = response.body().getAnggota().toString();
                        Log.d("Response body", responseBodyString);

                        showAnggota(response.body().getAnggota());
                    }
                    else  {
                        Log.e("Response errorBody", String.valueOf(response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<AnggotaKeluargaResult> call, Throwable t) {
                    Log.e("Response Failure", String.valueOf(t.getMessage().toString()));
                }
            });
        }
    }

    private void showAnggota(List<AnggotaKeluarga> data){
        AdminAnggotaAdapter anggotaAdapter = new AdminAnggotaAdapter(view.getContext(), R.layout.item_anggota, data);
        list_anggota = (ListView) view.findViewById(R.id.list_aanggota);
        list_anggota.setAdapter(anggotaAdapter);
        anggotaAdapter.notifyDataSetChanged();
    }
}
