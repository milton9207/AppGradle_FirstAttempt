package com.example.appgradle_firstattempt.app.materialtest.materialtest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.*;
import android.widget.TextView;
import com.example.appgradle_firstattempt.app.R;
import com.example.appgradle_firstattempt.app.materialtest.tabs.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {


    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragmen_navigation_drawer);

        drawerFragment.setUp(R.id.fragmen_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));

        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.custom_tab_view,R.id.tabText);
//        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.material_green_A200);
//            }
//        });

        mTabs.setBackgroundColor(getResources().getColor(R.color.material_blue_500));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.material_green_A200));
        mTabs.setViewPager(mPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch(id)
        {
            case R.id.action_settings:
                return true;
            case R.id.navigate:
                startActivity(new Intent(this,Activity2.class));
                break;
            case R.id.activity_tab:
                startActivity(new Intent(this,ActivityUsingTabLibrary.class));
        }


        return super.onOptionsItemSelected(item);
    }

    class MyPageAdapter extends FragmentPagerAdapter {

        int[] icons = {R.drawable.ic_home,R.drawable.ic_action,R.drawable.ic_search,R.drawable.ic_shop};
        String[] tabs;

        public MyPageAdapter(FragmentManager fm) {
            super(fm);

            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,36,36);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        @Override
        public Fragment getItem(int position) {

            MyFragment fragment = MyFragment.getInstance(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
