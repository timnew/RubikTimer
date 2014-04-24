package com.github.timnew.rubiktimer.database.stubs;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.support.DatabaseResults;

import java.sql.SQLException;

public class EmptyClosableIterator<T> implements CloseableIterator<T> {
    @Override
    public void close() throws SQLException {
    }

    @Override
    public void closeQuietly() {
    }

    @Override
    public DatabaseResults getRawResults() {
        return null;
    }

    @Override
    public void moveToNext() {
    }

    @Override
    public T first() throws SQLException {
        return null;
    }

    @Override
    public T previous() throws SQLException {
        return null;
    }

    @Override
    public T current() throws SQLException {
        return null;
    }

    @Override
    public T nextThrow() throws SQLException {
        return null;
    }

    @Override
    public T moveRelative(int offset) throws SQLException {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }

    @Override
    public void remove() {
    }
}
