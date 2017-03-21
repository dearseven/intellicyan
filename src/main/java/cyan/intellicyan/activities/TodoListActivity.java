package cyan.intellicyan.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cyan.intellicyan.R;
import cyan.intellicyan.activities.base.BaseCompatActivity;

public class TodoListActivity extends BaseCompatActivity {
    public static final String EXTRA_DATE_Y = "extra_date_y";
    public static final String EXTRA_DATE_M = "extra_date_m";
    public static final String EXTRA_DATE_D = "extra_date_d";

    public FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("主界面");
       //getSubTitle().setText("更多");

        floatingActionButton=(FloatingActionButton)findViewById(R.id.todo_add_button);
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TodoListActivity.this,"???",Toast.LENGTH_LONG).show();
                    }
                });
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
