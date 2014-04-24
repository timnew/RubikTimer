package com.github.timnew.rubiktimer.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.github.timnew.rubiktimer.R;
import com.github.timnew.rubiktimer.common.dialog.DialogEditText;
import com.github.timnew.rubiktimer.common.dialog.DialogEditText_;
import com.github.timnew.rubiktimer.database.ProfileRepository;
import com.github.timnew.rubiktimer.domain.Profile;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

@EFragment
public class RenameProfileDialog extends DialogFragment {

    public static final String DIALOG_TAG = "RenameProfileDialog";

    @Bean
    protected ProfileRepository profileRepository;

    private OnProfileRenamedListener onProfileRenamedListener;

    public OnProfileRenamedListener getOnProfileRenamedListener() {
        return onProfileRenamedListener;
    }

    public void setOnProfileRenamedListener(OnProfileRenamedListener onProfileRenamedListener) {
        this.onProfileRenamedListener = onProfileRenamedListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final DialogEditText profileNameView = DialogEditText_.build(getActivity());

        profileNameView.setText(profileRepository.currentProfile().getName());
        profileNameView.setHint(R.string.profile_name);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.timer_profile_icon)
                .setTitle(R.string.rename_profile)
                .setView(profileNameView)
                .setPositiveButton(R.string.rename, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String profileNewName = profileNameView.getText();

                        profileRepository.renameProfile(profileNewName);

                        dialog.dismiss();

                        dispatchOnProfileRenamed();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    private void dispatchOnProfileRenamed() {
        if (onProfileRenamedListener != null)
            onProfileRenamedListener.onProfileRenamed(profileRepository.currentProfile());
    }

    public void show(FragmentManager fragmentManager, OnProfileRenamedListener onProfileRenamedListener) {
        setOnProfileRenamedListener(onProfileRenamedListener);

        show(fragmentManager, DIALOG_TAG);
    }

    public static interface OnProfileRenamedListener {
        void onProfileRenamed(Profile profile);
    }
}