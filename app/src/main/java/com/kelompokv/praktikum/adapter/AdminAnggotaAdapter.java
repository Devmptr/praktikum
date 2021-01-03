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

import org.w3c.dom.Text;

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

        TextView type = (TextView) convertView.findViewById(R.id.item_user_type);
        type.setText(anggota.getTipe());

        TextView info = (TextView) convertView.findViewById(R.id.list_info);
        DateFormat date = DateFormat.getDateInstance();
        info.setText(date.format(anggota.getTanggallahir()).toString());

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
