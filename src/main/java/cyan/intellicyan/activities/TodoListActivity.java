package cyan.intellicyan.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyan.intellicyan.R;
import cyan.intellicyan.activities.base.BaseCompatActivity;
import cyan.intellicyan.cyasql.DBClient;
import cyan.intellicyan.util.DLog;
import cyan.intellicyan.util.SizeUtil;

public class TodoListActivity extends BaseCompatActivity implements View.OnClickListener {
    public static final String EXTRA_DATE_Y = "extra_date_y";
    public static final String EXTRA_DATE_M = "extra_date_m";
    public static final String EXTRA_DATE_D = "extra_date_d";

    public FloatingActionButton floatingActionButton;
    public Button floatAddNewButton;
    public boolean isFirstInitDone = false;

    private float distance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("主界面");
        //getSubTitle().setText("更多");
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
        Toast.makeText(getApplicationContext(), "startAddNewActivty!", Toast.LENGTH_SHORT).show();

        /*这个是测试cyannosql数据库的使用
        DBClient client = DBClient.getInstance(getApplication());

        client.insert("ttable2", new String[]{"c"}, new String[]{"c value 1"});
        List<Map<String, String>> listMap = client.retrieve("ttable2", null, null, null);
        iterate(listMap);

        DLog.log(TodoListActivity.class, "\n~~~~~~~update~~~~~~~~");


        client.update("ttable2", new String[]{"c1", "c2", "c"}, new String[]{"c11", "c22", "c"}, "c=?", new String[]{"c value 1"});
        listMap = client.retrieve("ttable2", null, null, null);
        iterate(listMap);

        if (listMap.size() >= 2) {
            DLog.log(TodoListActivity.class, "\n~~~~~~~~delete~~~~~~~");
            DLog.log(TodoListActivity.class, "\n~~~~~~~~~~~~~~~");

            client.delete("ttable2", "objKeyId=?", new String[]{"1"});
            listMap = client.retrieve("ttable2", null, null, null);
            iterate(listMap);
        }
*/

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

}
