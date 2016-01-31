package com.ap.collegespacev2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by amaneureka on 31-Jan-16.
 */
public class DetailsFragment extends Fragment
{
    View v;
    Bundle saveState;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActivity().setTheme(R.style.ThemeFading);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        saveState = savedInstanceState;

        v = inflater.inflate(R.layout.fragment_post, null);
        return v;
    }
}
