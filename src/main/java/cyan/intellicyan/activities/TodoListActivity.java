package cyan.intellicyan.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cyan.intellicyan.R;
import cyan.intellicyan.activities.base.BaseCompatActivity;
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

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == floatAddNewButton.getId()) {
            Toast.makeText(getApplicationContext(), "add new task!", Toast.LENGTH_SHORT).show();
        }
    }
}
