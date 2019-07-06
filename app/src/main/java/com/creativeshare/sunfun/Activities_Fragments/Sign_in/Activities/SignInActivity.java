package com.creativeshare.sunfun.Activities_Fragments.Sign_in.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.creativeshare.sunfun.Activities_Fragments.Home.Activity.HomeActivity;
import com.creativeshare.sunfun.Language.Language;
import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.Activities_Fragments.Sign_in.Fragments.Fragment_Sign_In;
import com.creativeshare.sunfun.Activities_Fragments.Sign_in.Fragments.Fragment_Sign_Up;

import java.util.Locale;

import io.paperdb.Paper;


public class SignInActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    private int fragment_count = 0;
    private Fragment_Sign_In fragment_sign_in;
    private Fragment_Sign_Up fragment_sign_up;
    private String cuurent_language;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase, Language.getLanguage(newBase)));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
     initview();
        if(savedInstanceState==null){
        DisplayFragmentSignIN();}


    }

    private void initview() {
        Paper.init(this);
        cuurent_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fragmentManager = this.getSupportFragmentManager();
    }


    @Override
    public void onBackPressed() {
        Back();
    }

    public void Back() {
        if (fragment_count == 1) {
            finish();

        } else if (fragment_count > 1 && fragment_count < 3) {
            fragment_count -= 1;
            super.onBackPressed();

        }

    }

    public void DisplayFragmentSignIN() {
        fragment_count += 1;
        fragment_sign_in = Fragment_Sign_In.newInstance();
        if (fragment_sign_in.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sign_in).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_sign_in, "fragment_sign_in").addToBackStack("fragment_sign_in").commit();
        }
    }

    public void DisplayFragmentSignup() {
        fragment_count += 1;
        fragment_sign_up = Fragment_Sign_Up.newInstance();
        if (fragment_sign_up.isAdded()) {
            fragmentManager.beginTransaction().show(fragment_sign_up).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fragment_sign_in_container, fragment_sign_up, "fragment_sign_up").addToBackStack("fragment_sign_up").commit();
        }
    }

    public void gotohome() {
        Intent intent=new Intent(SignInActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
