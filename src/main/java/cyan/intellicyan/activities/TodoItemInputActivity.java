package cyan.intellicyan.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import cyan.intellicyan.R;
import cyan.intellicyan.activities.base.BaseCompatActivity;

public class TodoItemInputActivity extends BaseCompatActivity {
    /**
     * 打开新建事项
     */
    public static final int REQUSET_CODE_CREATE_NEW_TODO_ITEM = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_todo_item_input;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    public void whenOptionsItemSelected(MenuItem item) {

    }
}
