package com.creativeshare.sunfun.Activities_Fragments.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.creativeshare.sunfun.Activities_Fragments.activity_sign_in.activities.SignInActivity;
import com.creativeshare.sunfun.Language.Language;
import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.preferences.Preferences;

public class SplashScreenActivity extends AppCompatActivity {

    private ConstraintLayout f1;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Preferences.getInstance().getLanguage(newBase)));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        f1=findViewById(R.id.fl);
        Animation animation;

        animation= AnimationUtils.loadAnimation(getBaseContext(),R.anim.lanuch);
        f1.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent=new Intent(SplashScreenActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}