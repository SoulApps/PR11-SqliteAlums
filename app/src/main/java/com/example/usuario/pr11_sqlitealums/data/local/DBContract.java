package com.example.usuario.pr11_sqlitealums.data.local;

import android.provider.BaseColumns;

/**
 * Created by usuario on 29/01/2018.
 */

public class DBContract {
    public static final String DB_NAME = "students";
    public static final int DB_VERSION = 1;

    // Constructor privado para que NO pueda instanciarse.
    private DBContract() {
    }

    public abstract static class Student implements BaseColumns {
        public static final String TABLE_NAME = "students";
        public static final String NAME = "name";
        public static final String GRADE = "grade";
        public static final String PHONE = "phone";
        public static final String ADDRESS = "address";
        public static final String[] ALL_FIELDS = new String[]{_ID, NAME, GRADE, PHONE, ADDRESS};
        public static final String NOTIFICATION_URI = "content://com.example.usuario/students";
        public static final String DROP_TABLE_QUERY = "drop table if exists " + TABLE_NAME;
    }
}
