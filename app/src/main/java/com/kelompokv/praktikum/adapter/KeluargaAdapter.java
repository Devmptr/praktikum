package com.kelompokv.praktikum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.model.admin.Keluarga;

import java.util.List;

public class KeluargaAdapter extends ArrayAdapter<Keluarga> {
    public KeluargaAdapter (@NonNull Context context, int resource, @NonNull List<Keluarga> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_keluarga, parent, false
            );
        }

        Keluarga keluarga = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.item_keluarga_alamat);
        name.setText(keluarga.getAlamat());

        TextView info = (TextView) convertView.findViewById(R.id.item_keluarga_info);
        info.setText(keluarga.getKodepos());

        return convertView;
    }
}
