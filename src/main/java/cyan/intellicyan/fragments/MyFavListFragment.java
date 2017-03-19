package cyan.intellicyan.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cyan.intellicyan.R;
import cyan.intellicyan.fragments.father.SuperFragment;

/**
 * .
 */
public class MyFavListFragment extends SuperFragment {
    public static final String TAG = "MyFavListFragment";


    public MyFavListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_fav_list, container, false);
    }

}
