package com.dnagaraj.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class SocialMediaActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        setTitle("Social Media App!!");

        toolbar=findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        //Setting viewpager
        viewPager=findViewById(R.id.myViewPager);
        tabAdapter=new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        //Setting tabs
        tabLayout=findViewById(R.id.myTabLayout);
        tabLayout.setupWithViewPager(viewPager,true);

    }
}