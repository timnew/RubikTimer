package com.github.timnew.rubiktimer.shared;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface ApplicationSettings {

    @DefaultInt(0)
    int currentUserId();

}
