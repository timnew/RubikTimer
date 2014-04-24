package com.github.timnew.rubiktimer.database;

import com.github.timnew.rubiktimer.domain.Profile;
import com.github.timnew.rubiktimer.shared.ApplicationSettings_;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.sql.SQLException;
import java.util.List;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

@EBean(scope = Singleton)
public class ProfileRepository {

    @Pref
    protected ApplicationSettings_ appSettings;

    @OrmLiteDao(helper = DatabaseHelper.class, model = Profile.class)
    protected RuntimeExceptionDao<Profile, Integer> profileDao;

    private Profile currentProfile;

    private void initCurrentProfile() {
        Profile currentProfile;

        int currentUserId = appSettings.currentUserId().get();
        currentProfile = profileDao.queryForId(currentUserId);

        if (currentProfile == null) {
            try {
                currentProfile = profileDao.queryBuilder().queryForFirst();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        if (currentProfile == null) {
            currentProfile = Profile.ANONYMOUS;
        }

        activeProfile(currentProfile);
    }

    private void saveProfile() {
        profileDao.update(currentProfile());
    }

    public Profile currentProfile() {
        if (currentProfile == null)
            initCurrentProfile();

        return currentProfile;
    }

    public Profile currentActiveProfile() {
        return isAnonymousMode() ? Profile.ANONYMOUS : currentProfile();
    }

    public boolean isAnonymousMode() {
        return appSettings.anonymousMode().get();
    }

    public void setAnonymousMode(boolean anonymousMode) {
        appSettings.anonymousMode().put(anonymousMode);
    }

    public Profile activeProfile(Profile currentProfile) {
        appSettings.currentUserId().put(currentProfile.getId());
        this.currentProfile = currentProfile;
        return currentProfile;
    }

    public void renameProfile(String newName) {
        currentProfile().setName(newName);
        saveProfile();
    }

    public Profile newProfile(String name) {
        Profile profile = new Profile(name);
        activeProfile(profile);
        saveProfile();

        return profile;
    }

    public List<Profile> allProfiles() {
        return profileDao.queryForAll();
    }

    public Profile profileWithId(int id) {
        if (id == Profile.ANONYMOUS.getId())
            return Profile.ANONYMOUS;

        return profileDao.queryForId(id);
    }
}
