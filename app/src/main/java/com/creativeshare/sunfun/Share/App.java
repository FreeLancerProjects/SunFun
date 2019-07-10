package com.creativeshare.sunfun.Share;


import android.app.Application;
import android.content.Context;

import com.creativeshare.sunfun.Language.Language;
import com.creativeshare.sunfun.preferences.Preferences;


public class App extends Application {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Preferences.getInstance().getLanguage(newBase)));
    }
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.setDefault(this, "DEFAULT", "fonts/GE_SS_Two_Light.otf");
        TypefaceUtil.setDefault(this, "MONOSPACE", "fonts/GE_SS_Two_Light.otf");
        TypefaceUtil.setDefault(this, "SERIF", "fonts/GE_SS_Two_Light.otf");
        TypefaceUtil.setDefault(this, "SANS_SERIF", "fonts/GE_SS_Two_Light.otf");

    }
}

