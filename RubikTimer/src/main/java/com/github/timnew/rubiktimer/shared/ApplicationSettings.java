package com.github.timnew.rubiktimer.shared;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.APPLICATION_DEFAULT)
public interface ApplicationSettings {

    @DefaultInt(0)
    int currentUserId();

    @DefaultBoolean(true)
    boolean anonymousMode();
}
