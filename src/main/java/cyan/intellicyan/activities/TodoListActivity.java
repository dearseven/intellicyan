package cyan.intellicyan.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import cyan.intellicyan.R;
import cyan.intellicyan.activities.base.BaseCompatActivity;

public class TodoListActivity extends BaseCompatActivity {
    public static final String EXTRA_DATE_Y = "extra_date_y";
    public static final String EXTRA_DATE_M = "extra_date_m";
    public static final String EXTRA_DATE_D = "extra_date_d";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("主界面");
       //getSubTitle().setText("更多");

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_todo_list;
    }

    @Override
    protected int getMenuId() {
        return R.menu.todo_list_menu;
    }

    @Override
    public void whenOptionsItemSelected(MenuItem item) {

    }
}
