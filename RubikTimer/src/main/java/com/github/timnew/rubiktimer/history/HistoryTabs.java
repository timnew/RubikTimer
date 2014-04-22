package com.github.timnew.rubiktimer.history;

import android.support.v4.app.Fragment;

public enum HistoryTabs {
    PERSONAL_TIME {
        @Override
        public Fragment buildFragment() {
            return HistoryListFragment_.builder().build();
        }

        @Override
        public boolean shouldShow() {
            return true;
        }
    },
    PERSONAL_BEST {
        @Override
        public Fragment buildFragment() {
            return HistoryListFragment_.builder().build();
        }

        @Override
        public boolean shouldShow() {
            return false;
        }
    },
    LOCAL_TIME {
        @Override
        public Fragment buildFragment() {
            return null;
        }

        @Override
        public boolean shouldShow() {
            return false;
        }
    },
    LOCAL_BEST {
        @Override
        public Fragment buildFragment() {
            return null;
        }

        @Override
        public boolean shouldShow() {
            return false;
        }
    },
    WORLD_BEST {
        @Override
        public Fragment buildFragment() {
            return null;
        }

        @Override
        public boolean shouldShow() {
            return false;
        }
    };

    public abstract Fragment buildFragment();

    public abstract boolean shouldShow();
}
