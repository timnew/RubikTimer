package com.github.timnew.rubiktimer.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class ViewAdapter<T, TView extends View> extends BaseAdapter {

    protected Context context;

    protected List<T> items;

    protected ViewAdapter(Context context) {
        this(context, null);
    }

    protected ViewAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public int indexOf(T item) {
        return items.indexOf(item);
    }

    @Override
    public int getCount() {
        if (items == null)
            return 0;

        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TView view;

        if (convertView == null) {
            view = createView();
        } else {
            //noinspection unchecked
            view = (TView) convertView;
        }

        T item = getItem(position);
        updateView(view, item);

        return view;
    }

    protected abstract TView createView();

    protected abstract void updateView(TView view, T item);
}
