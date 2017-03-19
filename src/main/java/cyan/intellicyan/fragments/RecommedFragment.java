package cyan.intellicyan.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cyan.intellicyan.R;
import cyan.intellicyan.fragments.father.SuperFragment;

/**
 */
public class RecommedFragment extends SuperFragment {

    public static final String TAG="RecommedFragment";

    public RecommedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommed, container, false);
    }

}
