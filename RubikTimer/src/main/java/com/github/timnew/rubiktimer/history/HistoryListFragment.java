package com.github.timnew.rubiktimer.history;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.common.ViewAdapter;
import com.github.timnew.rubiktimer.database.ProfileRepository;
import com.github.timnew.rubiktimer.database.TimeRecordRepository;
import com.github.timnew.rubiktimer.domain.Profile;
import com.github.timnew.rubiktimer.domain.TimeRecord;
import com.google.common.base.Function;
import com.j256.ormlite.dao.CloseableIterator;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Iterators.addAll;
import static com.google.common.collect.Iterators.limit;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.primitives.Ints.asList;

@EFragment(R.layout.history_list_fragment)
public class HistoryListFragment extends ListFragment {

    @Bean
    protected TimeRecordRepository timeRecordRepository;

    @Bean
    protected ProfileRepository profileRepository;

    @FragmentArg
    protected HistoryListProvider dataProvider;

    protected List<TimeRecord> items = new ArrayList<TimeRecord>();

    private TimeRecordAdapter dataAdapter;

    @AfterViews
    protected void afterViews() {
        ListAdapter listAdapter = buildAdapterChain();

        refreshData();

        setListAdapter(listAdapter);
    }

    private ListAdapter buildAdapterChain() {
        ListView listView = getListView();

        dataAdapter = new TimeRecordAdapter(getActivity(), profileRepository.currentProfile(), items);

        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(dataAdapter);
        scaleInAnimationAdapter.setAbsListView(listView);

        SwipeDismissAdapter swipeDismissAdapter = new SwipeDismissAdapter(scaleInAnimationAdapter, new OnDismissCallback() {
            @Override
            public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
                Iterable<TimeRecord> records = transform(asList(reverseSortedPositions), new Function<Integer, TimeRecord>() {
                    @Override
                    public TimeRecord apply(Integer index) {
                        return items.get(index);
                    }
                });

                timeRecordRepository.delete(newArrayList(records));

                refreshData();
            }
        });
        swipeDismissAdapter.setAbsListView(listView);

        ContextualUndoAdapter contextualUndoAdapter = new ContextualUndoAdapter(swipeDismissAdapter, R.layout.history_time_record_item_undo, R.id.undo_panel, 1000, new ContextualUndoAdapter.DeleteItemCallback() {
            @Override
            public void deleteItem(int position) {
                TimeRecord timeRecord = items.get(position);

                timeRecordRepository.delete(timeRecord);

                refreshData();
            }
        });
        contextualUndoAdapter.setAbsListView(listView);

        return contextualUndoAdapter;
    }

    private void refreshData() {
        CloseableIterator<TimeRecord> iterator = dataProvider.getIterator(timeRecordRepository);

        items.clear();

        addAll(items, limit(iterator, 10));

        dataAdapter.notifyDataSetChanged();

        iterator.closeQuietly();
    }

    public static interface HistoryListProvider extends Serializable {
        CloseableIterator<TimeRecord> getIterator(TimeRecordRepository repository);
    }

    public static class TimeRecordAdapter extends ViewAdapter<TimeRecord, HistoryTimeRecordItemView> {

        private final Profile currentProfile;

        public TimeRecordAdapter(Context context, Profile currentProfile, List<TimeRecord> items) {
            super(context, items);

            this.currentProfile = currentProfile;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        protected HistoryTimeRecordItemView createView() {
            return HistoryTimeRecordItemView_.build(context);
        }

        @Override
        protected void updateView(HistoryTimeRecordItemView view, TimeRecord item) {
            view.updateView(currentProfile == item.getProfile(), item);
        }
    }
}