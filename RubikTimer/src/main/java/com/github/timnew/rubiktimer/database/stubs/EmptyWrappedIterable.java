package com.github.timnew.rubiktimer.database.stubs;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;

import java.sql.SQLException;
import java.util.Iterator;

public class EmptyWrappedIterable<T> implements CloseableWrappedIterable<T> {

    @Override
    public void close() throws SQLException {
    }

    @Override
    public CloseableIterator<T> closeableIterator() {
        return new EmptyClosableIterator<T>();
    }

    @Override
    public Iterator<T> iterator() {
        return new EmptyClosableIterator<T>();
    }
}

