package com.github.timnew.rubiktimer.history;

import android.support.v4.app.Fragment;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.database.TimeRecordRepository;
import com.github.timnew.rubiktimer.domain.TimeRecord;
import com.j256.ormlite.dao.CloseableIterator;

import java.sql.SQLException;

import static com.github.timnew.rubiktimer.history.HistoryListFragment.HistoryListProvider;

public enum HistoryTabs implements HistoryListProvider {

    PERSONAL_TIME {
        @Override
        public boolean shouldShow() {
            return true;
        }

        @Override
        public int getTitle() {
            return R.string.tab_personal_time;
        }

        @Override
        public CloseableIterator<TimeRecord> getIterator(TimeRecordRepository repository) {
            try {
                return repository.currentUserTimeRecordByCreationTime().iteratorThrow();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    },
    PERSONAL_BEST {
        @Override
        public boolean shouldShow() {
            return true;
        }

        @Override
        public int getTitle() {
            return R.string.tab_personal_best;
        }

        @Override
        public CloseableIterator<TimeRecord> getIterator(TimeRecordRepository repository) {
            try {
                return repository.currentUserTimeRecordsByTime().iteratorThrow();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    },
    LOCAL_TIME {
        @Override
        public boolean shouldShow() {
            return false;
        }

        @Override
        public int getTitle() {
            return R.string.tab_local_time;
        }

        @Override
        public CloseableIterator<TimeRecord> getIterator(TimeRecordRepository repository) {
            return repository.localTimeRecordByTime();
        }
    },
    LOCAL_BEST {
        @Override
        public boolean shouldShow() {
            return false;
        }

        @Override
        public int getTitle() {
            return R.string.tab_local_best;
        }

        @Override
        public CloseableIterator<TimeRecord> getIterator(TimeRecordRepository repository) {
            return repository.localTimeRecordByCreationTime();
        }
    },
    WORLD_BEST {
        @Override
        public boolean shouldShow() {
            return false;
        }

        @Override
        public int getTitle() {
            return R.string.tab_global_best;
        }

        @Override
        public CloseableIterator<TimeRecord> getIterator(TimeRecordRepository repository) {
            return null;
        }
    };

    public Fragment buildFragment() {
        return HistoryListFragment_.builder().dataProvider(this).build();
    }

    public abstract boolean shouldShow();

    public abstract int getTitle();
}
