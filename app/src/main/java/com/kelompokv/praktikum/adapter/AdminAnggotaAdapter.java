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
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;

import java.text.DateFormat;
import java.util.List;

public class AdminAnggotaAdapter extends ArrayAdapter<AnggotaKeluarga> {
    public AdminAnggotaAdapter (@NonNull Context context, int resource, @NonNull List<AnggotaKeluarga>
            objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_anggota, parent, false
            );
        }

        AnggotaKeluarga anggota = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.list_name);
        name.setText(anggota.getNama());

        TextView info = (TextView) convertView.findViewById(R.id.list_info);
        DateFormat date = DateFormat.getDateInstance();
        info.setText(date.format(anggota.getTanggallahir()).toString() +" | "+anggota.getValidated());

        return convertView;
    }
}
