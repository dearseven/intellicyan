package cyan.intellicyan.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import cyan.intellicyan.R;
import cyan.intellicyan.activities.base.BaseCompatActivity;
import cyan.intellicyan.beans.TodoItem;
import cyan.intellicyan.beans.TodoItemDetail;
import cyan.intellicyan.components.todolistview.TodoListRecycler;
import cyan.intellicyan.cyasql.DBClient;
import cyan.intellicyan.util.DLog;
import cyan.intellicyan.util.SizeUtil;

public class TodoListActivity extends BaseCompatActivity implements View.OnClickListener, TodoListRecycler.Father {
    public static final String EXTRA_DATE_Y = "extra_date_y";
    public static final String EXTRA_DATE_M = "extra_date_m";
    public static final String EXTRA_DATE_D = "extra_date_d";

    public FloatingActionButton floatingActionButton;
    public Button floatAddNewButton;
    public boolean isFirstInitDone = false;
    public AppBarLayout appBarLayout = null;

    private float distance = 0;

    TodoListRecycler recycler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("主界面");
        //getSubTitle().setText("更多");

        new LoadTodoItemsTask().execute();
        appBarLayout = (AppBarLayout) findViewById(R.id.base_app_bar_layout);
    }

    private class LoadTodoItemsTask extends AsyncTask<Void, TodoItem, List<TodoItem>> {

        @Override
        protected List<TodoItem> doInBackground(Void... params) {
            DBClient client = DBClient.getInstance(getApplication());
            List<Map<String, String>> mapList = client.retrieve(TodoItem.TABLE_NAME, null, null, null, null, null, "objKeyId desc", null);
            List<TodoItem> list = new ArrayList<TodoItem>(mapList.size());
            Map<String, String> m = null;
            TodoItem item = null;
            for (int i = 0; i < mapList.size(); i++) {
                m = mapList.get(i);
                item = new TodoItem();
                item.datetime = m.get(TodoItem.col.datetime.name());
                item.uuid = m.get(TodoItem.col.uuid.name());
                item.title = m.get(TodoItem.col.title.name());
                item.objKeyId = Integer.parseInt(m.get(TodoItem.col.objKeyId.name()));
                list.add(item);
                publishProgress(item);
            }
            return list;
        }

        @Override
        protected void onProgressUpdate(TodoItem... items) {
            super.onProgressUpdate(items);
            TodoItem item = items[0];
            DLog.log(LoadTodoItemsTask.class, "获取到item：" + item.toString());
        }

        @Override
        protected void onPostExecute(List<TodoItem> list) {
            super.onPostExecute(list);
            recycler = new TodoListRecycler(TodoListActivity.this, list, R.id.todo_recyclerview);

        }

    }

    /**
     * 处理事件
     */
    private void initEvent() {
        //1,开始，处理floatbutton的点击动画
        distance = SizeUtil.dip2px(getApplicationContext(), 100);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.todo_add_button);
        floatAddNewButton = (Button) findViewById(R.id.expand_float_item_addNew);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float alpha = floatAddNewButton.getAlpha();
                if (alpha == 0.0f) {
                    //Toast.makeText(getApplication(), "alpha==0.0f", Toast.LENGTH_SHORT).show();
                    ValueAnimator va = ValueAnimator.ofFloat(0f, distance);
                    va.setDuration(500);
                    va.setInterpolator(new android.view.animation.BounceInterpolator());
                    va.setTarget(floatAddNewButton);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            floatAddNewButton.setTranslationY(-(float) animation.getAnimatedValue());
                            floatAddNewButton.setAlpha(animation.getAnimatedFraction());
                            floatingActionButton.setRotation(-45 * animation.getAnimatedFraction());
                        }
                    });
                    va.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            floatAddNewButton.setOnClickListener(TodoListActivity.this);
                        }
                    });
                    va.start();
                } else if (alpha >= 1.0f) {
                    //Toast.makeText(getApplication(), "alpha==1.0f", Toast.LENGTH_SHORT).show();
                    ValueAnimator va = ValueAnimator.ofFloat(distance, 0f);
                    va.setInterpolator(new android.view.animation.AnticipateOvershootInterpolator());
                    va.setTarget(floatAddNewButton);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            floatAddNewButton.setTranslationY(-(float) animation.getAnimatedValue());
                            floatAddNewButton.setAlpha(1 - animation.getAnimatedFraction());
                            floatingActionButton.setRotation(45 + 45 * animation.getAnimatedFraction());
                        }
                    });
                    va.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            floatAddNewButton.setOnClickListener(null);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                        }
                    });
                    va.start();

                }
            }
        });
        //结束，处理floatbutton的点击动画

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    floatingActionButton.setVisibility(View.VISIBLE);

                } else {
                    DLog.log(TodoListActivity.class, floatAddNewButton.getAlpha() + "");
                    if (floatAddNewButton.getAlpha() >= 1.0f) {
                        floatingActionButton.callOnClick();
                    }
                    floatingActionButton.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirstInitDone == false) {
            isFirstInitDone = true;

            initEvent();

        }

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
        //Toast.makeText(getApplicationContext(), item.getItemId()+" "+item.getTitle() , Toast.LENGTH_SHORT).show();
        if (item.getItemId() == R.id.add_new_todo) {
            startAddNewActivty();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == floatAddNewButton.getId()) {
            //Toast.makeText(getApplicationContext(), "add new task!", Toast.LENGTH_SHORT).show();
            startAddNewActivty();
        }
    }

    //------------
    private void startAddNewActivty() {
        if (recycler == null) {
            Toast.makeText(getApplicationContext(), "正在初始化，请稍候!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(this, TodoItemInputActivity.class);
        startActivityForResult(i, TodoItemInputActivity.REQUSET_CODE_CREATE_NEW_TODO_ITEM);

        //Toast.makeText(getApplicationContext(), "startAddNewActivty!", Toast.LENGTH_SHORT).show();

        /*

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        DBClient client = DBClient.getInstance(getApplication());

        TodoItem item = new TodoItem();
        item.uuid = UUID.randomUUID().toString();
        item.title = "测试:" + item.uuid;
        item.datetime = format.format(new Date());
        String col[] = new String[]{TodoItem.col.title.name(), TodoItem.col.datetime.name(), TodoItem.col.uuid.name()};
        String vals[] = new String[]{item.title, item.datetime, item.uuid};
        client.insert(TodoItem.TABLE_NAME, col, vals);

        TodoItemDetail itemDetail = new TodoItemDetail();
        itemDetail.uuid = UUID.randomUUID().toString();
        itemDetail.todoItemUuid = item.uuid;
        col = new String[]{TodoItemDetail.col.uuid.name(), TodoItemDetail.col.todoItemUuid.name()};
        vals = new String[]{itemDetail.uuid, itemDetail.todoItemUuid};
        client.insert(TodoItemDetail.TABLE_NAME, col, vals);

        DLog.log(TodoListActivity.class, item.toString());
        DLog.log(TodoListActivity.class, itemDetail.toString());
        recycler.insertItem(0, item, true);
    */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TodoItemInputActivity.REQUSET_CODE_CREATE_NEW_TODO_ITEM) {


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void iterate(List<Map<String, String>> lm) {
        Iterator<Map<String, String>> it = lm.iterator();
        while (it.hasNext()) {
            Map<String, String> m = it.next();
            DLog.log(TodoListActivity.class, "\n-----element-----");
            Set<Map.Entry<String, String>> es = m.entrySet();
            Iterator<Map.Entry<String, String>> esit = es.iterator();
            while (esit.hasNext()) {
                Map.Entry<String, String> et = esit.next();
                DLog.log(TodoListActivity.class, et.getKey() + ":" + et.getValue());
            }
        }
    }

    @Override
    public <T> T findFatherViewById(int id) {
        return (T) findViewById(id);
    }

    @Override
    public void onItemListRecyclerClick(int viewId, Object param) {
        Toast.makeText(this, "viewid:" + viewId + ",param:" + param, Toast.LENGTH_SHORT).show();
    }

    @Override
    public <T> T getAnyContext() {
        return (T) this;
    }
}
