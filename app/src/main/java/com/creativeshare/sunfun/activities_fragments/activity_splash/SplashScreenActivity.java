package com.creativeshare.sunfun.activities_fragments.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.activities_fragments.activity_home.activity.HomeActivity;
import com.creativeshare.sunfun.activities_fragments.activity_sign_in.activities.SignInActivity;
import com.creativeshare.sunfun.databinding.ActivitySplashBinding;
import com.creativeshare.sunfun.language.Language;
import com.creativeshare.sunfun.preferences.Preferences;
import com.creativeshare.sunfun.tags.Tags;

public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private Animation animation;
    private Preferences preferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Preferences.getInstance().getLanguage(newBase)));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
        preferences = Preferences.getInstance();

        animation= AnimationUtils.loadAnimation(getBaseContext(),R.anim.lanuch);
        binding.cons.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
               String session = preferences.getSession(SplashScreenActivity.this);
               if (session.equals(Tags.session_login))
               {
                   Intent intent=new Intent(SplashScreenActivity.this, HomeActivity.class);
                   startActivity(intent);
                   finish();
               }else
                   {
                       Intent intent=new Intent(SplashScreenActivity.this, SignInActivity.class);
                       startActivity(intent);
                       finish();
                   }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}