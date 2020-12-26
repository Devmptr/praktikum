package com.kelompokv.praktikum.activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.adapter.AnggotaAdapter;
import com.kelompokv.praktikum.db.helper.DbHelper;
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;
import com.kelompokv.praktikum.model.user.AnggotaKeluargaResult;
import com.kelompokv.praktikum.network.Client;
import com.kelompokv.praktikum.network.service.AnggotaService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends Fragment {
    View view;
    String role;
    SharedPreferences auth_sp;
    DbHelper helper;
    SQLiteDatabase db;
    private Integer user_id;
    private ListView list_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment, container, false);

        auth_sp = view.getContext().getSharedPreferences("authSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        user_id = auth_sp.getInt("log_id", 0);
        helper = new DbHelper(view.getContext());
        db = helper.getWritableDatabase();

        loadAnggota(user_id);

        list_view = (ListView) view.findViewById(R.id.list_anggota);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AnggotaKeluarga item = (AnggotaKeluarga) adapterView.getItemAtPosition(i);
            Bundle bundle = new Bundle();
            bundle.putInt("id", item.getId());
            loadFragment(new UserViewFragment(), bundle, "Data Anggota Keluarga");
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment, @Nullable Bundle mBundle, String title){
        fragment.setArguments(mBundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fl_container, fragment);
        ft.commit();
    }

    private void loadAnggota(Integer id) {
        if (id != 0){
            AnggotaService service = Client.getClient().create(AnggotaService.class);
            Call<AnggotaKeluargaResult> anggotas = service.getAnggota(id);
            anggotas.enqueue(new Callback<AnggotaKeluargaResult>() {
                @Override
                public void onResponse(Call<AnggotaKeluargaResult> call, Response<AnggotaKeluargaResult> response) {
                    if(response.isSuccessful()) {
                        //you can do whatever with the response body now...
                        String responseBodyString= response.body().getAnggota().toString();
                        Log.d("Response body", responseBodyString);

                        show(response.body().getAnggota());
                    } else  {
                        Log.e("Response errorBody", String.valueOf(response.errorBody()));
                    }
                }

                @Override
                public void onFailure(Call<AnggotaKeluargaResult> call, Throwable t) {
                    show(helper.getAllAnggota());
                    Log.e("Response errorDev", String.valueOf(t.getMessage()));
                }
            });
        }
    }

    public void show(List<AnggotaKeluarga> anggotas){
        if (anggotas.size() > 0){
            String sql_delete = "delete from anggota";
            db.execSQL(sql_delete);
            for (int i=0 ; i<anggotas.size(); i++){
                db.execSQL("insert into anggota (nama, jenis_kelamin, tempat_lahir, agama, pendidikan, " +
                        "pekerjaan, tipe, ayah, ibu, tanggal_lahir, id_keluarga, validated) values (" +
                        "'"+anggotas.get(i).getNama()+"', '"+anggotas.get(i).getJeniskelamin()+"', " +
                        "'"+anggotas.get(i).getTempatlahir()+"', '"+anggotas.get(i).getAgama()+"', " +
                        "'"+anggotas.get(i).getPendidikan()+"', '"+anggotas.get(i).getPekerjaan()+"', " +
                        "'"+anggotas.get(i).getTipe()+"', '"+anggotas.get(i).getAyah()+"', " +
                        "'"+anggotas.get(i).getIbu()+"', '"+anggotas.get(i).getStrTanggal()+"', "+
                        ""+anggotas.get(i).getId_keluarga()+", '"+anggotas.get(i).getValidated()+"')");
            }
        }
        AnggotaAdapter anggotaAdapter = new AnggotaAdapter(view.getContext(), R.layout.item_anggota, anggotas);
        list_view = (ListView) view.findViewById(R.id.list_anggota);
        list_view.setAdapter(anggotaAdapter);
        anggotaAdapter.notifyDataSetChanged();
    }
}
