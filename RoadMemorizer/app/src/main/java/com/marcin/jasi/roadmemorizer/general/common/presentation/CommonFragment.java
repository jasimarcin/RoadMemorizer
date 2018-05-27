package com.marcin.jasi.roadmemorizer.general.common.presentation;

import android.support.v4.app.Fragment;

import com.marcin.jasi.roadmemorizer.main.MainActivity;

public abstract class CommonFragment extends Fragment {

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).setHeader(getFragmentTitle());
        super.onResume();
    }

    protected abstract String getFragmentTitle();
}
