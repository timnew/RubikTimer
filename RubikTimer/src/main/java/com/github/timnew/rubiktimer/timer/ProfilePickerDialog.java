package com.github.timnew.rubiktimer.timer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.database.ProfileRepository;
import com.github.timnew.rubiktimer.domain.Profile;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import static com.github.timnew.rubiktimer.timer.ProfilePickerView.OnProfilePickedListener;

@EFragment
public class ProfilePickerDialog extends DialogFragment {

    @Bean
    protected ProfileRepository profileRepository;
    private OnProfilePickedListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ProfilePickerView profilePickerView = ProfilePickerView_.build(getActivity());

        profilePickerView.setProfileList(profileRepository.allProfiles());
        profilePickerView.setCurrentProfile(profileRepository.currentProfile());
        profilePickerView.setProfilePickedListener(new OnProfilePickedListener() {
            @Override
            public void onProfilePicked(Profile profile) {
                profileRepository.activeUser(profile);

                dismiss();

                if (listener != null)
                    listener.onProfilePicked(profile);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setView(profilePickerView)
                .create();
    }

    public ProfilePickerDialog setOnProfilePickedListener(OnProfilePickedListener listener) {
        this.listener = listener;
        return this;
    }
}
