package com.kelompokv.praktikum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;

import java.text.DateFormat;
import java.util.List;

public class AnggotaAdapter extends ArrayAdapter<AnggotaKeluarga> {
    public AnggotaAdapter (@NonNull Context context, int resource, @NonNull List<AnggotaKeluarga>
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

        TextView tanggal_lahir = (TextView) convertView.findViewById(R.id.list_info);
        tanggal_lahir.setText(anggota.getStrTanggal());

        TextView role = (TextView) convertView.findViewById(R.id.item_user_type);
        role.setText(anggota.getTipe());

        ImageView valid = (ImageView) convertView.findViewById(R.id.ic_status_anggota);
        String getValid = anggota.getValidated();
        if(getValid.equals("validated")){
            valid.setBackgroundResource(R.drawable.ic_success);
        }else if(getValid.equals("process")){
            valid.setBackgroundResource(R.drawable.ic_process);
        }else if(getValid.equals("problem")){
            valid.setBackgroundResource(R.drawable.ic_error);
        }else{
            valid.setBackgroundResource(R.drawable.ic_error);
        }

        return convertView;
    }
}
