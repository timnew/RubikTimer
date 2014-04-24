package com.github.timnew.rubiktimer.timer;

import android.support.v4.app.FragmentActivity;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.database.ProfileRepository;
import com.github.timnew.rubiktimer.domain.Profile;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

@EBean
public class ProfileController {

    @RootContext
    protected FragmentActivity activity;

    @ViewById(R.id.profile_icon)
    protected ToggleButton profileIcon;

    @ViewById(R.id.profile_name)
    protected TextView profileName;

    @Bean
    protected ProfileRepository profileRepository;

    @AfterViews
    protected void afterViews() {
        refreshView();
    }

    public void refreshView() {
        Profile currentProfile = profileRepository.currentActiveProfile();
        profileName.setText(currentProfile.getName());
    }

    @Click(R.id.profile_section)
    protected void toggleProfile() {
        profileIcon.setChecked(!profileIcon.isChecked());
    }

    @CheckedChange(R.id.profile_icon)
    protected void profileToggled(CompoundButton button, boolean checked) {
        profileRepository.setAnonymousMode(!checked);
        refreshView();
    }

    @LongClick(R.id.profile_section)
    protected void renameCurrentProfile() {

    }

    public Profile currentActiveProfile() {
        return profileRepository.currentActiveProfile();
    }
}
