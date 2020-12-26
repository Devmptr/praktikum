package com.kelompokv.praktikum.activity.admin.keluarga;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.adapter.KeluargaAdapter;
import com.kelompokv.praktikum.model.admin.Keluarga;
import com.kelompokv.praktikum.model.admin.KeluargaList;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AdminService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AKeluargaFragment extends Fragment {
    View view;

    AdminService service;
    ListView keluarga_list, list_keluarga;
    FloatingActionButton btn_create;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_keluarga_index_fragment, container, false);

        loadKeluarga();

        list_keluarga = view.findViewById(R.id.alist_keluarga);
        btn_create = view.findViewById(R.id.btn_akeluarga_add);

        list_keluarga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Keluarga item = (Keluarga) adapterView.getItemAtPosition(i);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                loadFragment(new AKeluargaShowFragment(), bundle);
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AKeluargaCreateFragment(), null);
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

    private void loadKeluarga(){
        service = Client.getClient().create(AdminService.class);
        Call<KeluargaList> userall = service.allKeluarga();
        userall.enqueue(new Callback<KeluargaList>() {
            @Override
            public void onResponse(Call<KeluargaList> call, Response<KeluargaList> response) {
                if(response.isSuccessful()) {
                    //you can do whatever with the response body now...
                    String responseBodyString= response.body().getKeluargas().toString();
                    Log.d("Response body", responseBodyString);

                    showUser(response.body().getKeluargas());
                }
                else  {
                    Log.e("Response errorBody", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<KeluargaList> call, Throwable t) {
                Log.e("Response errorDev", String.valueOf(t.getMessage()));
            }
        });
    }

    private void showUser(List<Keluarga> data){
        KeluargaAdapter adapter = new KeluargaAdapter(view.getContext(), R.layout.item_keluarga, data);
        keluarga_list = (ListView) view.findViewById(R.id.alist_keluarga);
        keluarga_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
