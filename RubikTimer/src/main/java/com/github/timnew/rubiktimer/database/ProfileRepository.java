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
            newProfile("anonymous");
            return;
        }

        activeUser(currentProfile);
    }

    private void saveProfile() {
        profileDao.update(currentProfile());
    }

    public Profile currentProfile() {
        if (currentProfile == null)
            initCurrentProfile();
        return currentProfile;
    }

    public void renameProfile(String newName) {
        currentProfile().setName(newName);
        saveProfile();
    }

    public Profile newProfile(String name) {
        Profile profile = new Profile(name);
        activeUser(profile);
        saveProfile();

        return profile;
    }

    public List<Profile> allProfiles() {
        return profileDao.queryForAll();
    }

    public Profile profileWithId(int id) {
        return profileDao.queryForId(id);
    }

    public void activeUser(Profile currentProfile) {
        this.currentProfile = currentProfile;
        appSettings.currentUserId().put(currentProfile.getId());
    }
}
