package com.geno.pm.pmms_sx.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "informationData.db"; //数据库名称
    private static final int VERSION = 1; //数据库版本

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table information(ID integer primary  key autoincrement, ProjectNo TEXT not null , "
                + "ProjectName TEXT not null , " + " ProjectDetail TEXT not null);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
