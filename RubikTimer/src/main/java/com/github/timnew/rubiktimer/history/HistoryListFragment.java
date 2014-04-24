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

import static com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter.DeleteItemCallback;

@EFragment(R.layout.history_list_fragment)
public class HistoryListFragment extends ListFragment implements OnDismissCallback, DeleteItemCallback {

    public static final int MAX_ITEM_COUNT = 10;
    public static final int DELETE_DELAY_TIME = 1000;
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

        SwipeDismissAdapter swipeDismissAdapter = new SwipeDismissAdapter(scaleInAnimationAdapter, this);
        swipeDismissAdapter.setAbsListView(listView);

        ContextualUndoAdapter contextualUndoAdapter = new ContextualUndoAdapter(swipeDismissAdapter, R.layout.history_time_record_item_undo, R.id.undo_panel, DELETE_DELAY_TIME, this);
        contextualUndoAdapter.setAbsListView(listView);

        return contextualUndoAdapter;
    }

    private void refreshData() {
        items.clear();

        dataProvider.loadData(getActivity(), MAX_ITEM_COUNT, items);
        dataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
    }

    @Override
    public void deleteItem(int position) {
        TimeRecord timeRecord = items.get(position);

        dataProvider.delete(getActivity(), timeRecord);

        refreshData();
    }

    public static interface HistoryListProvider extends Serializable {
        void loadData(Context context, int maxItemCount, List<TimeRecord> timeRecordList);

        void delete(Context context, TimeRecord timeRecord);
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