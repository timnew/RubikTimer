package com.github.timnew.rubiktimer.timer;

import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.database.ProfileRepository;
import com.github.timnew.rubiktimer.domain.Profile;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

@EBean
public class ProfileController {

    public static final String PROFILE_PICKER_DIALOG = "ProfilePickerDialog";

    @RootContext
    protected FragmentActivity activity;

    @ViewById(R.id.profile_icon)
    protected ImageView profileIcon;

    @ViewById(R.id.profile_name)
    protected TextView profileName;

    @Bean
    protected ProfileRepository profileRepository;

    @AfterViews
    protected void afterViews() {
        refreshView();
    }

    public void refreshView() {
        Profile currentProfile = profileRepository.currentProfile();
        profileName.setText(currentProfile.getName());
    }

    @Click(R.id.profile_section)
    protected void onUserProfileClick() {
        ProfilePickerDialog_.builder().build()
                .setOnProfilePickedListener(new ProfilePickerView.OnProfilePickedListener() {
                    @Override
                    public void onProfilePicked(Profile profile) {
                        refreshView();
                    }
                })
                .show(activity.getSupportFragmentManager(), PROFILE_PICKER_DIALOG);
    }
}
