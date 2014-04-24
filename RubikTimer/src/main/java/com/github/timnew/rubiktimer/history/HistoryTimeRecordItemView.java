package com.github.timnew.rubiktimer.history;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.domain.TimeRecord;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.joda.time.format.DateTimeFormatter;

import static org.joda.time.format.DateTimeFormat.forPattern;

@EViewGroup(R.layout.history_time_record_item)
public class HistoryTimeRecordItemView extends FrameLayout {

    public static final DateTimeFormatter TIMESTAMP_FORMATTER = forPattern("YYYY MMM dd");
    @ViewById(R.id.profile_icon)
    protected ImageView profileIconView;
    @ViewById(R.id.profile)
    protected TextView profileView;
    @ViewById(R.id.timestamp)
    protected TextView timestampView;
    @ViewById(R.id.time)
    protected TextView timeView;

    public HistoryTimeRecordItemView(Context context) {
        super(context);
    }

    public HistoryTimeRecordItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HistoryTimeRecordItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void updateView(boolean isCurrentProfile, TimeRecord item) {

        profileIconView.setSelected(isCurrentProfile);

        profileView.setText(item.getProfile().getName());

        timestampView.setText(item.getCreatedAt().toString(TIMESTAMP_FORMATTER));

        timeView.setText(item.getFormatedTime());
    }
}
