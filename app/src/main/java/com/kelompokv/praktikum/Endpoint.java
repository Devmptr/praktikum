package com.kelompokv.praktikum;

public class Endpoint {
    public static final String USER_ANGGOTA_BASE = "user/anggota";
    public static final String USER_ANGGOTA_WITH_ID = "user/anggota/{anggota}";
    public static final String USER_ANGGOTA_SELECTED = "user/anggota/{id}/selected";
    public static final String USER_ANGGOTA_EDIT = "user/anggota/{anggota}/edit";
    public static final String USER_FIRST_LOGIN = "user/keluarga/firstlogin";

    public static final String ADMIN_USER_ALL = "admin/user/all";
    public static final String ADMIN_USER_CREATE = "admin/user/create";
    public static final String ADMIN_USER_SHOW = "admin/user/show/{id}";
    public static final String ADMIN_USER_UPDATE = "admin/user/update/{id}";
    public static final String ADMIN_USER_DELETE = "admin/user/delete/{id}";

    public static final String ADMIN_KELUARGA_ALL = "admin/keluarga/all";
    public static final String ADMIN_KELUARGA_CREATE = "admin/keluarga/create";
    public static final String ADMIN_KELUARGA_SHOW = "admin/keluarga/show/{id}";
    public static final String ADMIN_KELUARGA_UPDATE = "admin/keluarga/update/{id}";
    public static final String ADMIN_KELUARGA_DELETE = "admin/keluarga/delete/{id}";

    public static final String ADMIN_ANGGOTA_KELUARGA = "admin/anggota/keluarga/{id}";
    public static final String ADMIN_ANGGOTA_CREATE = "admin/anggota/create";
    public static final String ADMIN_ANGGOTA_SHOW = "admin/anggota/show/{id}";
    public static final String ADMIN_ANGGOTA_UPDATE = "admin/anggota/update/{id}";
    public static final String ADMIN_ANGGOTA_DELETE = "admin/anggota/delete/{id}";
}
