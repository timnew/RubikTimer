package com.github.timnew.rubiktimer.database;

import com.github.timnew.rubiktimer.domain.User;
import com.github.timnew.rubiktimer.shared.ApplicationSettings_;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.sql.SQLException;
import java.util.List;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

@EBean(scope = Singleton)
public class UserRepository {

    @Pref
    protected ApplicationSettings_ appSettings;
    @OrmLiteDao(helper = DatabaseHelper.class, model = User.class)
    protected RuntimeExceptionDao<User, Integer> userDao;
    private User currentUser;

    private void initCurrentUser() {
        User currentUser;


        int currentUserId = appSettings.currentUserId().get();
        currentUser = userDao.queryForId(currentUserId);

        if (currentUser == null) {
            try {
                currentUser = userDao.queryBuilder().queryForFirst();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        if (currentUser == null) {
            newUser("anonymous");
            return;
        }

        activeUser(currentUser);
    }

    private void saveUser() {
        userDao.update(currentUser());
    }

    public User currentUser() {
        if (currentUser == null)
            initCurrentUser();
        return currentUser;
    }

    public void renameUser(String newName) {
        currentUser().setName(newName);
        saveUser();
    }

    public User newUser(String name) {
        User user = new User(name);
        activeUser(user);
        saveUser();

        return user;
    }

    public List<User> allUsers() {
        return userDao.queryForAll();
    }

    public User userWithId(int userId) {
        return userDao.queryForId(userId);
    }

    public void activeUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
