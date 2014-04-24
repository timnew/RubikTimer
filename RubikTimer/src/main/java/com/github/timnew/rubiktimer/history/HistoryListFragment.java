package com.github.timnew.rubiktimer.history;

import android.content.Context;
import android.support.v4.app.ListFragment;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.common.ViewAdapter;
import com.github.timnew.rubiktimer.database.ProfileRepository;
import com.github.timnew.rubiktimer.database.TimeRecordRepository;
import com.github.timnew.rubiktimer.domain.Profile;
import com.github.timnew.rubiktimer.domain.TimeRecord;
import com.j256.ormlite.dao.CloseableIterator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterators.addAll;
import static com.google.common.collect.Iterators.limit;

@EFragment(R.layout.history_list_fragment)
public class HistoryListFragment extends ListFragment {

    @Bean
    protected TimeRecordRepository timeRecordRepository;

    @Bean
    protected ProfileRepository profileRepository;

    @FragmentArg
    protected HistoryListProvider dataProvider;

    protected List<TimeRecord> items = new ArrayList<TimeRecord>();

    private HistoryItemAdapter adapter;

    @AfterViews
    protected void afterViews() {
        adapter = new HistoryItemAdapter(getActivity(), profileRepository.currentProfile(), items);

        refreshData();

        setListAdapter(adapter);
    }

    private void refreshData() {
        CloseableIterator<TimeRecord> iterator = dataProvider.getIterator(timeRecordRepository);

        items.clear();

        addAll(items, limit(iterator, 10));

        adapter.notifyDataSetChanged();

        iterator.closeQuietly();
    }

    public static interface HistoryListProvider extends Serializable {
        CloseableIterator<TimeRecord> getIterator(TimeRecordRepository repository);
    }

    public static class HistoryItemAdapter extends ViewAdapter<TimeRecord, HistoryTimeRecordItemView> {

        private final Profile currentProfile;

        public HistoryItemAdapter(Context context, Profile currentProfile, List<TimeRecord> items) {
            super(context, items);

            this.currentProfile = currentProfile;
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