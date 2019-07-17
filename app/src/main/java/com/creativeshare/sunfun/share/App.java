package com.creativeshare.sunfun.share;


import android.app.Application;
import android.content.Context;

import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.preferences.Preferences;


public class App extends Application {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Preferences.getInstance().getLanguage(newBase)));
    }
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.setDefaultFont(this, "DEFAULT", "fonts/ar_font.ttf");
        TypefaceUtil.setDefaultFont(this, "MONOSPACE", "fonts/ar_font.ttf");
        TypefaceUtil.setDefaultFont(this, "SERIF", "fonts/ar_font.ttf");
        TypefaceUtil.setDefaultFont(this, "SANS_SERIF", "fonts/ar_font.ttf");


    }
}

