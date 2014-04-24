package com.github.timnew.rubiktimer.history;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.database.ProfileRepository;
import com.github.timnew.rubiktimer.database.ProfileRepository_;
import com.github.timnew.rubiktimer.database.TimeRecordRepository;
import com.github.timnew.rubiktimer.database.TimeRecordRepository_;
import com.github.timnew.rubiktimer.domain.TimeRecord;
import com.j256.ormlite.dao.CloseableIterator;

import java.util.List;

import static com.github.timnew.rubiktimer.history.HistoryListFragment.HistoryListProvider;
import static com.google.common.collect.Iterators.addAll;
import static com.google.common.collect.Iterators.limit;

public enum HistoryTabs implements HistoryListProvider {

    PERSONAL_TIMESTAMP {
        @Override
        public boolean shouldShow(Context context) {
            return true;
        }

        @Override
        public int getTitle() {
            return R.string.tab_personal_time;
        }

        @Override
        public void loadData(Context context, int maxItemCount, List<TimeRecord> timeRecordList) {
            ProfileRepository repository = ProfileRepository_.getInstance_(context);

            CloseableIterator<TimeRecord> iterator = repository
                    .currentProfile()
                    .getRecordsByCreationTime()
                    .closeableIterator();

            addAll(timeRecordList, limit(iterator, maxItemCount));

            iterator.closeQuietly();
        }

        @Override
        public void delete(Context context, TimeRecord timeRecord) {
            TimeRecordRepository repository = TimeRecordRepository_.getInstance_(context);
            repository.delete(timeRecord);
        }
    },
    PERSONAL_BEST {
        @Override
        public boolean shouldShow(Context context) {
            return true;
        }

        @Override
        public int getTitle() {
            return R.string.tab_personal_best;
        }

        @Override
        public void loadData(Context context, int maxItemCount, List<TimeRecord> timeRecordList) {
            ProfileRepository repository = ProfileRepository_.getInstance_(context);

            CloseableIterator<TimeRecord> iterator = repository
                    .currentProfile()
                    .getRecordsByTime()
                    .closeableIterator();

            addAll(timeRecordList, limit(iterator, maxItemCount));

            iterator.closeQuietly();
        }

        @Override
        public void delete(Context context, TimeRecord timeRecord) {
            TimeRecordRepository repository = TimeRecordRepository_.getInstance_(context);
            repository.delete(timeRecord);
        }
    },
    LOCAL_TIMESTAMP {
        @Override
        public boolean shouldShow(Context context) {
            ProfileRepository profileRepository = ProfileRepository_.getInstance_(context);
            return profileRepository.profileCount() > 1;
        }

        @Override
        public int getTitle() {
            return R.string.tab_local_time;
        }

        @Override
        public void loadData(Context context, int maxItemCount, List<TimeRecord> timeRecordList) {
            TimeRecordRepository repository = TimeRecordRepository_.getInstance_(context);

            CloseableIterator<TimeRecord> iterator = repository.localTimeRecordByCreationTime();

            addAll(timeRecordList, limit(iterator, maxItemCount));

            iterator.closeQuietly();
        }

        @Override
        public void delete(Context context, TimeRecord timeRecord) {
            TimeRecordRepository repository = TimeRecordRepository_.getInstance_(context);
            repository.delete(timeRecord);
        }
    },
    LOCAL_BEST {
        @Override
        public boolean shouldShow(Context context) {
            ProfileRepository profileRepository = ProfileRepository_.getInstance_(context);
            return profileRepository.profileCount() > 1;
        }

        @Override
        public int getTitle() {
            return R.string.tab_local_best;
        }

        @Override
        public void loadData(Context context, int maxItemCount, List<TimeRecord> timeRecordList) {
            TimeRecordRepository repository = TimeRecordRepository_.getInstance_(context);

            CloseableIterator<TimeRecord> iterator = repository.localTimeRecordByTime();

            addAll(timeRecordList, limit(iterator, maxItemCount));

            iterator.closeQuietly();
        }

        @Override
        public void delete(Context context, TimeRecord timeRecord) {
            TimeRecordRepository repository = TimeRecordRepository_.getInstance_(context);
            repository.delete(timeRecord);
        }
    },
    WORLD_BEST {
        @Override
        public boolean shouldShow(Context context) {
            return false;
        }

        @Override
        public int getTitle() {
            return R.string.tab_global_best;
        }

        @Override
        public void loadData(Context context, int maxItemCount, List<TimeRecord> timeRecordList) {
        }

        @Override
        public void delete(Context context, TimeRecord timeRecord) {
        }
    };

    public Fragment buildFragment() {
        return HistoryListFragment_.builder().dataProvider(this).build();
    }

    public abstract boolean shouldShow(Context context);

    public abstract int getTitle();
}
