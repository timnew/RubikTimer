package com.github.timnew.rubiktimer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.domain.Profile;
import com.github.timnew.rubiktimer.domain.TimeRecord;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import static com.j256.ormlite.table.TableUtils.createTable;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "database";
    public static final int SCHEMA_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            createTable(connectionSource, Profile.class);
            createTable(connectionSource, TimeRecord.class);

            insertInitData();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void insertInitData() {
        RuntimeExceptionDao<Profile, Integer> profileDao = this.getRuntimeExceptionDao(Profile.class);

        Profile timNew = new Profile("TimNew");
        profileDao.create(timNew);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }
}
