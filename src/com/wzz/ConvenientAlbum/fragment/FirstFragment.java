package com.wzz.ConvenientAlbum.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wzz.ConvenientAlbum.R;

/**
 * Created by WANGZHENGZE on 2014/10/8.
 */
public class FirstFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_fragment, container, false);
        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        TextView tv = (TextView) v.findViewById(R.id.text);
        tv.setText("Xxxxxxxxxxxxxxxxxxxxx:" + i);
        return v;
    }
}
