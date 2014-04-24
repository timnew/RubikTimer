package com.github.timnew.rubiktimer.timer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.domain.Profile;
import com.github.timnew.rubiktimer.shared.ViewAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EViewGroup(R.layout.profile_picker_view)
public class ProfilePickerView extends RelativeLayout {

    @ViewById(R.id.profile_list)
    protected ListView profileListView;

    private ProfileListAdapter dataAdapter;
    private AlphaInAnimationAdapter animationAdapter;


    private OnProfilePickedListener profilePickedListener;
    private Profile currentProfile;

    public ProfilePickerView(Context context) {
        super(context);
    }

    public ProfilePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfilePickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @AfterViews
    protected void afterViews() {
        dataAdapter = new ProfileListAdapter(getContext());

        animationAdapter = new AlphaInAnimationAdapter(dataAdapter);
        animationAdapter.setAbsListView(profileListView);

        profileListView.setAdapter(animationAdapter);
    }

    public void setProfileList(List<Profile> profileList) {
        dataAdapter.setItems(profileList);
    }

    public Profile getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(Profile current) {
        currentProfile = current;
        profileListView.setSelection(dataAdapter.indexOf(current));
    }

    @ItemClick(R.id.profile_list)
    protected void dispatchProfilePicked(Profile profile) {
        currentProfile = profile;

        if (profilePickedListener == null)
            return;

        profilePickedListener.onProfilePicked(profile);
    }

    public OnProfilePickedListener getProfilePickedListener() {
        return profilePickedListener;
    }

    public void setProfilePickedListener(OnProfilePickedListener profilePickedListener) {
        this.profilePickedListener = profilePickedListener;
    }

    @Click(R.id.add_profile)
    protected void onAddProfileClicked() {

    }

    @CheckedChange(R.id.delete_profile)
    protected void onDeleteProfileModeChanged(CompoundButton button, boolean isChecked) {

    }

    public static interface OnProfilePickedListener {
        void onProfilePicked(Profile profile);
    }

    public static class ProfileListAdapter extends ViewAdapter<Profile, ProfileItemView> {

        private boolean isDeleteMode;

        public ProfileListAdapter(Context context) {
            super(context);
        }

        public List<Profile> getProfiles() {
            return items;
        }

        @Override
        protected ProfileItemView createView() {
            return ProfileItemView_.build(context);
        }

        @Override
        protected void updateView(ProfileItemView view, Profile item) {
            view.updateView(item);
            view.setDeleteMode(isDeleteMode);
        }
    }
}
