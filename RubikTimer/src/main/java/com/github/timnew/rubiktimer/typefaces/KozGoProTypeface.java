package com.github.timnew.rubiktimer.typefaces;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import static org.androidannotations.annotations.EBean.Scope;

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
