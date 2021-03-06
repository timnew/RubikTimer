package com.github.timnew.rubiktimer.database;

import com.github.timnew.rubiktimer.domain.Profile;
import com.github.timnew.rubiktimer.domain.TimeRecord;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.Collection;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

@EBean(scope = Singleton)
public class TimeRecordRepository {

    @Bean
    protected ProfileRepository profileRepository;

    @OrmLiteDao(helper = DatabaseHelper.class, model = TimeRecord.class)
    protected RuntimeExceptionDao<TimeRecord, Integer> timeRecordDao;

    private Profile currentProfile() {
        return profileRepository.currentProfile();
    }

    public ForeignCollection<TimeRecord> currentUserTimeRecordsByTime() {
        return currentProfile().getRecordsByTime();
    }

    public ForeignCollection<TimeRecord> currentUserTimeRecordByCreationTime() {
        return currentProfile().getRecordsByCreationTime();
    }

    public CloseableIterator<TimeRecord> localTimeRecordByTime() {
        try {
            return timeRecordDao.queryBuilder().orderBy("time", true).iterator();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public CloseableIterator<TimeRecord> localTimeRecordByCreationTime() {
        try {
            return timeRecordDao.queryBuilder().orderBy("createdAt", false).iterator();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(Collection<TimeRecord> records) {
        timeRecordDao.delete(records);
    }

    public void delete(TimeRecord timeRecord) {
        timeRecordDao.delete(timeRecord);
    }
}
