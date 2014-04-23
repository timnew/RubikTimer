package com.github.timnew.rubiktimer.domain;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "profiles")
public class Profile {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @ForeignCollectionField(orderColumnName = "time", orderAscending = true)
    private ForeignCollection<TimeRecord> recordsByTime;

    @ForeignCollectionField(orderColumnName = "createdAt", orderAscending = false)
    private ForeignCollection<TimeRecord> recordsByCreateTime;

    @SuppressWarnings("UnusedDeclaration")
    Profile() {
    }

    public Profile(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ForeignCollection<TimeRecord> getRecordsByTime() {
        return recordsByTime;
    }

    public ForeignCollection<TimeRecord> getRecordsByCreationTime() {
        return recordsByCreateTime;
    }
}
