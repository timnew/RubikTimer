package com.github.timnew.rubiktimer.timer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.domain.Profile;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.profile_item_view)
public class ProfileItemView extends RelativeLayout {

    @ViewById(R.id.profile_name)
    protected TextView profileNameView;

    public ProfileItemView(Context context) {
        super(context);
    }

    public ProfileItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void updateView(Profile profile) {
        profileNameView.setText(profile.getName());
    }
}
