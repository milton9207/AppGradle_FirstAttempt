package com.example.appgradle_firstattempt.app.materialtest.materialtest;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.example.appgradle_firstattempt.app.R;
import com.example.appgradle_firstattempt.app.materialtest.materialtest.Activity2;
import com.example.appgradle_firstattempt.app.materialtest.materialtest.Information;
import com.example.appgradle_firstattempt.app.materialtest.materialtest.MyAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements MyAdapter.ClickListener{

    private final String TAG = "MiltonInfo";
    private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View drawerContainerView;
    private MyAdapter adapter;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstance;




    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));

        if(savedInstanceState!=null)
        {
            mFromSavedInstance=true;
        }
    }

    public static List<Information> getData()
    {
        List<Information> data = new ArrayList();
        data.add(new Information(R.drawable.ic_action,"hola"));
        data.add(new Information(R.drawable.ic_home  ,"como"));
        data.add(new Information(R.drawable.ic_shop,"estas"));
        data.add(new Information(R.drawable.ic_search,"jeje"));


        return data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new MyAdapter(getActivity(),getData());

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                switch (position)
                {
                    case 0:
                        Toast.makeText(getActivity(),"OnClick" + position,Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(),Activity2.class));
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

                Toast.makeText(getActivity(),"OnLongClick" + position,Toast.LENGTH_SHORT).show();

            }
        }));



        return layout;
    }


    public void setUp(int framentId,DrawerLayout drawerLayout, final Toolbar toolbar) {

        drawerContainerView = getActivity().findViewById(framentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer)
                {
                    mUserLearnedDrawer = true;
                    saveToPreference(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                if(slideOffset<0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }

            }
        };

        if(!mUserLearnedDrawer && !mFromSavedInstance)
        {
            mDrawerLayout.openDrawer(drawerContainerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }

    public static void saveToPreference(Context context,String preferenceName, String preferenceValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context,String preferenceName, String defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_APPEND);

        return sharedPreferences.getString(preferenceName,defaultValue);
    }

    @Override
    public void ItemClicked(View view, int position) {

        startActivity(new Intent(getActivity(),Activity2.class));

    }


    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
    {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener)
        {
            Log.d(TAG,"Entered constructor");
            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    Log.d(TAG,"OnSingleTapUp" + e);
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                   View child =  recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if(child != null && clickListener != null)
                    {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }

                    Log.d(TAG, "OnLongPress" + e);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            View child =  recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

            if(child != null && clickListener!= null && gestureDetector.onTouchEvent(motionEvent))
            {
                clickListener.onClick(child,recyclerView.getChildPosition(child));
            }


            Log.d(TAG,"onInterceptTouchEvent " + gestureDetector.onTouchEvent(motionEvent) + " "+ motionEvent);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            Log.d(TAG,"onTouchEvent " + motionEvent);

        }
    }

    public static interface ClickListener{

        public void onClick(View view, int position);
        public void onLongClick(View view,int position);
    }
}
