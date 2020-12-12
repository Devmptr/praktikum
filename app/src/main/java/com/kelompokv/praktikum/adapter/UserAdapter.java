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
import com.kelompokv.praktikum.model.admin.User;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter (@NonNull Context context, int resource, @NonNull List<User> objects) {
    super(context, resource, objects);
}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_user, parent, false
            );
        }

        User user = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.item_user_name);
        name.setText(user.getName());

        TextView info = (TextView) convertView.findViewById(R.id.item_user_info);
        info.setText(user.getEmail()+" | "+user.getRole());

        return convertView;
    }
}
