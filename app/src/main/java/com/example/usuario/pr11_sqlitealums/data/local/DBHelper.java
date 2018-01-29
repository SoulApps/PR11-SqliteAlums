package com.example.usuario.pr11_sqlitealums.data.local;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.usuario.pr11_sqlitealums.utils.DatabaseUtils;
import com.example.usuario.pr11_sqlitealums.R;

/**
 * Created by usuario on 29/01/2018.
 */

public class DBHelper extends SQLiteOpenHelper{

    private final AssetManager mAssets;
    private static DBHelper sInstance;
    private final String mTag;

    private DBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
        mAssets = context.getAssets();
        mTag = context.getString(R.string.app_name);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        // Se activa el log de la base de datos.
        setWriteAheadLoggingEnabled(true);
        // Se activa el uso de foreign keys en SQLite.
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecutan las sentencias SQL de creación de las tablas de la BD.
        DatabaseUtils.executeSqlFromAssetsFile(db, DBContract.DB_VERSION, mAssets);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBContract.Alumno.DROP_TABLE_QUERY);
        DatabaseUtils.executeSqlFromAssetsFile(db, DBContract.DB_VERSION, mAssets);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBContract.Alumno.DROP_TABLE_QUERY);
        DatabaseUtils.executeSqlFromAssetsFile(db, DBContract.DB_VERSION, mAssets);
    }

}
