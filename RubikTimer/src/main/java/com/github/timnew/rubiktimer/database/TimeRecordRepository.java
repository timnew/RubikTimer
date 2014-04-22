package com.github.timnew.rubiktimer.database;

import com.github.timnew.rubiktimer.domain.TimeRecord;
import com.github.timnew.rubiktimer.domain.User;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;

import java.sql.SQLException;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

@EBean(scope = Singleton)
public class TimeRecordRepository {

    @Bean
    protected UserRepository userRepository;

    @OrmLiteDao(helper = DatabaseHelper.class, model = TimeRecord.class)
    protected RuntimeExceptionDao<TimeRecord, Integer> timeRecordDao;

    private User currentUser() {
        return userRepository.currentUser();
    }

    public ForeignCollection<TimeRecord> currentUserTimeRecordsByTime() {
        return currentUser().getRecordsByTime();
    }

    public ForeignCollection<TimeRecord> currentUserTimeRecordByCreationTime() {
        return currentUser().getRecordsByCreationTime();
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

    public TimeRecord addRecord(long time) {
        TimeRecord record = new TimeRecord(currentUser(), time);
        timeRecordDao.create(record);
        return record;
    }
}
