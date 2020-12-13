package com.kelompokv.praktikum.db.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kelompokv.praktikum.model.user.AnggotaKeluarga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.lang.System.*;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "praktikum.db";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table anggota(_id integer primary key, nama text," +
                " jenis_kelamin text, tanggal_lahir text, tempat_lahir text, " +
                "agama text, pendidikan text, pekerjaan text, tipe text, " +
                "ayah text, ibu text, id_keluarga integer, validated text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<AnggotaKeluarga> getAllAnggota(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<AnggotaKeluarga> anggotas = new ArrayList<AnggotaKeluarga>();
        Cursor c = db.query("anggota", null, null, null,
                null, null, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            anggotas.add(new AnggotaKeluarga(c.getInt(c.getColumnIndex("_id")),
                                            c.getString(c.getColumnIndex("nama")),
                                            null,
                                            c.getString(c.getColumnIndex("jenis_kelamin")),
                                            c.getString(c.getColumnIndex("tempat_lahir")),
                                            c.getString(c.getColumnIndex("tanggal_lahir")),
                                            c.getString(c.getColumnIndex("agama")),
                                            c.getString(c.getColumnIndex("pendidikan")),
                                            c.getString(c.getColumnIndex("pekerjaan")),
                                            c.getString(c.getColumnIndex("tipe")),
                                            c.getString(c.getColumnIndex("ayah")),
                                            c.getString(c.getColumnIndex("ibu")),
                                            c.getString(c.getColumnIndex("validated")),
                                            c.getInt(c.getColumnIndex("_id")))
            );
        }
        return anggotas;
    }

    public AnggotaKeluarga getSelectAnggota(Integer id_offline){
        SQLiteDatabase db = this.getReadableDatabase();
        AnggotaKeluarga anggota;
        Cursor c = db.query("anggota", null, "_id="+id_offline, null,
                null, null, null);
        c.moveToFirst();
        anggota = new AnggotaKeluarga(c.getInt(c.getColumnIndex("_id")),
                                    c.getString(c.getColumnIndex("nama")),
                                    null,
                                    c.getString(c.getColumnIndex("jenis_kelamin")),
                                    c.getString(c.getColumnIndex("tempat_lahir")),
                                    c.getString(c.getColumnIndex("tanggal_lahir")),
                                    c.getString(c.getColumnIndex("agama")),
                                    c.getString(c.getColumnIndex("pendidikan")),
                                    c.getString(c.getColumnIndex("pekerjaan")),
                                    c.getString(c.getColumnIndex("tipe")),
                                    c.getString(c.getColumnIndex("ayah")),
                                    c.getString(c.getColumnIndex("ibu")),
                                    c.getString(c.getColumnIndex("validated")),
                                    c.getInt(c.getColumnIndex("_id")));
        return anggota;
    }
}
