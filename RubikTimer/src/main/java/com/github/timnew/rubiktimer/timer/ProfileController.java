package com.github.timnew.rubiktimer.timer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.database.ProfileRepository;
import com.github.timnew.rubiktimer.domain.Profile;
import com.github.timnew.rubiktimer.shared.ViewAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EBean
public class ProfileController {

    @RootContext
    protected Activity activity;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle(R.string.profile_picker_title)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        buildProfileAdapter(builder);

        AlertDialog alertDialog = builder.create();

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                refreshView();
            }
        });

        alertDialog.show();
    }

    private ProfileListAdapter buildProfileAdapter(AlertDialog.Builder builder) {
        final ProfileListAdapter adapter = new ProfileListAdapter(activity);
        List<Profile> items = profileRepository.allProfiles();
        Profile currentProfile = profileRepository.currentProfile();

        int index = items.indexOf(currentProfile);

        adapter.setItems(items);

        builder.setSingleChoiceItems(adapter, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Profile profile = adapter.getItem(which);
                profileRepository.activeUser(profile);
                dialog.dismiss();
            }
        });

        return adapter;
    }

    public static class ProfileListAdapter extends ViewAdapter<Profile, ProfileItemView> {

        public ProfileListAdapter(Context context) {
            super(context);
        }

        @Override
        protected ProfileItemView createView() {
            return ProfileItemView_.build(context);
        }

        @Override
        protected void updateView(ProfileItemView view, Profile item) {
            view.updateView(item);
        }
    }
}
