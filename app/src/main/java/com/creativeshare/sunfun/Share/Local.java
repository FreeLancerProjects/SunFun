package com.creativeshare.sunfun.Share;


import android.app.Application;
import android.content.Context;

import com.creativeshare.sunfun.Language.Language;


public class Local extends Application {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Language.getLanguage(newBase)));
    }
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/GE_SS_Two_Light.otf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }
}

