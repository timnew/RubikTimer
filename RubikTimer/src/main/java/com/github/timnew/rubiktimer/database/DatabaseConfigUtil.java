package com.github.timnew.rubiktimer.database;

import com.github.timnew.rubiktimer.domain.Profile;
import com.github.timnew.rubiktimer.domain.TimeRecord;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[]{
            TimeRecord.class,
            Profile.class
    };

    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt", classes);
    }
}
