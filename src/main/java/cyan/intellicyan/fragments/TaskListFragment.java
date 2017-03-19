package cyan.intellicyan.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cyan.intellicyan.R;
import cyan.intellicyan.fragments.father.SuperFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends SuperFragment {
    public static final String TAG="TaskListFragment";


    public TaskListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

}
