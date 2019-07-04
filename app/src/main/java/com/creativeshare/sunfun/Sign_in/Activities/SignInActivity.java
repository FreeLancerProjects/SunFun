package com.creativeshare.sunfun.Sign_in.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.Sign_in.Fragments.Fragment_Sign_In;
import com.creativeshare.sunfun.Sign_in.Fragments.Fragment_Sign_Up;


public class SignInActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    private int fragment_count = 0;
    private Fragment_Sign_In fragment_sign_in;
    private Fragment_Sign_Up fragment_sign_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState==null){
        DisplayFragmentSignIN();}


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
}
