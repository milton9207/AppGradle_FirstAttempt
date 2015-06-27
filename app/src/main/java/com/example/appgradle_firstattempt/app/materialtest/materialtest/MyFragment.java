package com.example.appgradle_firstattempt.app.materialtest.materialtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.appgradle_firstattempt.app.R;

public class MyFragment extends Fragment {
    private TextView textView;

    public static MyFragment getInstance(int position){

        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("position",position);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my,container,false);
        textView = (TextView) view.findViewById(R.id.positionText);

        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            textView.setText("The panel selected is " + bundle.getInt("position"));
        }

        return view;
    }
}