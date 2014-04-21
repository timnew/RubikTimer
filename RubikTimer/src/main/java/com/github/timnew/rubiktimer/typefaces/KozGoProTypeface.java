package com.github.timnew.rubiktimer.typefaces;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;

@EBean(scope = Scope.Singleton)
public class KozGoProTypeface {
    private static final String FONT_PATH = "fonts/KozGoPro-ExtraLight.otf";

    @RootContext
    protected Context context;

    private Typeface typeface;

    @AfterInject
    protected void afterInject() {
        typeface = Typeface.createFromAsset(context.getAssets(), FONT_PATH);
    }

    public void applyTo(TextView view) {
        view.setTypeface(typeface);
    }
}
