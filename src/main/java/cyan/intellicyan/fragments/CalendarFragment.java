package cyan.intellicyan.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cyan.intellicyan.R;
import cyan.intellicyan.activities.TodoListActivity;
import cyan.intellicyan.components.calendarview.RecyclerViewCalenar;
import cyan.intellicyan.fragments.father.SuperFragment;

/**
 * 这个是当完成新手引导以后，的主fragment
 */
public class CalendarFragment extends SuperFragment implements RecyclerViewCalenar.Parent {
    public static final int REQUEST_CODE_TODO_LIST = 1;

    public static final String TAG = "CalendarFragment";

    private static final String ARG_PARAM1 = "maybe";

    private String mParam1;

    public CalendarFragment() {
    }

    //---------------------
//    private RecyclerView mainClandar;
//    private TextView tvLastMonth;
//    private TextView tvNextMonth;
//    private TextView tvShowYandM;


    //---------------------

    /**
     * 用这个来构造
     *
     * @param param1
     * @return
     */
    public static CalendarFragment newInstance(String param1) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_calendar, container, false);
//        mainClandar = findAnyView(R.id.main_calendar_nav);
//        tvNextMonth = findAnyView(R.id.nextMonth);
//        tvShowYandM = findAnyView(R.id.showYandM);
//        tvLastMonth = findAnyView(R.id.lastMonth);

        RecyclerViewCalenar calendarView = new RecyclerViewCalenar(this, R.id.main_recyclerview, R.id.lastMonth, R.id.nextMonth, R.id.showYandM);
        calendarView.reshow();
        return mView;
    }

    //--------start implement RecyclerViewCalenar.Parent-------
    @Override
    public View findAndroidViewById(int id) {
        return findAnyView(id);
    }

    @Override
    public void clickDate(int y, int m, int d) {
        Intent intent = new Intent(getActivity(), TodoListActivity.class);
        intent.putExtra(TodoListActivity.EXTRA_DATE_Y, y);
        intent.putExtra(TodoListActivity.EXTRA_DATE_M, m);
        intent.putExtra(TodoListActivity.EXTRA_DATE_D, d);
        startActivityForResult(intent, REQUEST_CODE_TODO_LIST);
    }

    @Override
    public Context getParentContext() {
        return getContext();
    }

    //--------end implement RecyclerViewCalenar.Parent-------


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_TODO_LIST:
                if(requestCode==200){//成功

                }
                break;
        }
    }
}
