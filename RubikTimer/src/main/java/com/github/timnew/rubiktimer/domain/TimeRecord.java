package com.github.timnew.rubiktimer.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.DateTime;

import static java.lang.String.format;

@DatabaseTable(tableName = "timeRecords")
public class TimeRecord {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private long time;

    @DatabaseField(canBeNull = false)
    private DateTime createdAt;

    @DatabaseField(canBeNull = false, index = true, foreign = true, foreignAutoRefresh = true)
    private User user;

    public TimeRecord(User user, long time, DateTime createdAt) {
        this.user = user;
        this.time = time;
        this.createdAt = createdAt;
    }

    TimeRecord() {
    }

    public TimeRecord(User user, long time) {
        this(user, time, DateTime.now());
    }

    public static String formartTime(long time) {
        long seconds = time / 1000;
        long min = seconds / 60;
        seconds %= 60;
        long milli = time % 1000;

        return format("%02d:%02d.%03d", min, seconds, milli);
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }
}