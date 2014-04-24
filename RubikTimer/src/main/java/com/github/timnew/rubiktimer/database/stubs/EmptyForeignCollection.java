package com.github.timnew.rubiktimer.database.stubs;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.ForeignCollection;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public class EmptyForeignCollection<T> implements ForeignCollection<T> {

    @Override
    public CloseableIterator<T> iterator(int flags) {
        return new EmptyClosableIterator<T>();
    }

    @Override
    public CloseableIterator<T> closeableIterator(int flags) {
        return new EmptyClosableIterator<T>();
    }

    @Override
    public CloseableIterator<T> iteratorThrow() throws SQLException {
        return new EmptyClosableIterator<T>();
    }

    @Override
    public CloseableIterator<T> iteratorThrow(int flags) throws SQLException {
        return new EmptyClosableIterator<T>();
    }

    @Override
    public CloseableWrappedIterable<T> getWrappedIterable() {
        return new EmptyWrappedIterable<T>();
    }

    @Override
    public CloseableWrappedIterable<T> getWrappedIterable(int flags) {
        return new EmptyWrappedIterable<T>();
    }

    @Override
    public void closeLastIterator() throws SQLException {
    }

    @Override
    public boolean isEager() {
        return false;
    }

    @Override
    public int update(T obj) throws SQLException {
        return 0;
    }

    @Override
    public int updateAll() throws SQLException {
        return 0;
    }

    @Override
    public int refresh(T obj) throws SQLException {
        return 0;
    }

    @Override
    public int refreshAll() throws SQLException {
        return 0;
    }

    @Override
    public int refreshCollection() throws SQLException {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new EmptyClosableIterator<T>();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] array) {
        return array;
    }

    @Override
    public boolean add(T obj) {
        return false;
    }

    @Override
    public boolean remove(Object object) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> collection) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public CloseableIterator<T> closeableIterator() {
        return new EmptyClosableIterator<T>();
    }
}

