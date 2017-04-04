package cyan.intellicyan.activities;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cyan.intellicyan.R;
import cyan.intellicyan.activities.base.BaseCompatActivity;

public class TodoItemInputActivity extends BaseCompatActivity implements View.OnClickListener {
    /**
     * 打开新建事项
     */
    public static final int REQUSET_CODE_CREATE_NEW_TODO_ITEM = 101;

    private EditText dateNTimeEditText;
    private EditText titleEditText;
    private AlertDialog pickerDialog;

    private long settedDateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pickerDialog.cancel();
        pickerDialog = null;
    }

    private void initView() {
        dateNTimeEditText = (EditText) findViewById(R.id.todoitem_input_datetime);
        dateNTimeEditText.setInputType(InputType.TYPE_NULL);
        dateNTimeEditText.setOnClickListener(this);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateNTimeEditText.setText(formatter.format(new Date()) + ":00");

        titleEditText = (EditText) findViewById(R.id.todoitem_input_title);

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

    @Override
    public void onClick(View v) {
        if (v.getId() == dateNTimeEditText.getId()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
            if (isOpen) {//对键盘进行隐藏
                // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                imm.showSoftInput(dateNTimeEditText, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(dateNTimeEditText.getWindowToken(), 0); //强制隐藏键盘
                /**
                 * ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(WidgetSearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); (WidgetSearchActivity是当前的Activity)
                 */
            }
            imm = null;
            //弹出选择框
            if (pickerDialog == null) {
                AlertDialog.Builder bdr = new AlertDialog.Builder(this);
                LayoutInflater inflater = LayoutInflater.from(this);
                final View layout = inflater.inflate(R.layout.date_n_time_picker_layout, null);
                bdr.setCancelable(false);
                bdr.setView(layout);

                TextView closeTextView = (TextView) layout.findViewById(R.id.dnt_closer);
                closeTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickerDialog.cancel();

                        DatePicker dp = (DatePicker) layout.findViewById(R.id.dnt_datepicker);
                        TimePicker tp = (TimePicker) layout.findViewById(R.id.dnt_timepicker);

                        StringBuilder sb = new StringBuilder();
                        tp.setIs24HourView(true);
                        sb.append(dp.getYear()).append("-");
                        sb.append((dp.getMonth() + 1) < 10 ? "0" + (dp.getMonth() + 1) : (dp.getMonth() + 1)).append("-");
                        sb.append((dp.getDayOfMonth() + 1) < 10 ? "0" + (dp.getDayOfMonth() + 1) : (dp.getDayOfMonth() + 1));
                        sb.append(" ");
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                            sb.append((tp.getCurrentHour() < 10) ? "0" + (tp.getCurrentHour()) : (tp.getCurrentHour()));
                            sb.append(":");
                            sb.append((tp.getCurrentMinute() < 10) ? "0" + (tp.getCurrentMinute()) : (tp.getCurrentMinute()));
                            sb.append(":00");
                        } else {//>=24
                            sb.append((tp.getHour() < 10) ? "0" + (tp.getHour()) : (tp.getHour()));
                            sb.append(":");
                            sb.append((tp.getMinute() < 10) ? "0" + (tp.getMinute()) : (tp.getMinute()));
                            sb.append(":00");
                        }

                        dateNTimeEditText.setText(sb.toString());
                        sb.setLength(0);
                        sb = null;

                        Calendar cld = Calendar.getInstance();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            cld.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), tp.getHour(), tp.getMinute(), 0);
                        else
                            cld.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), tp.getCurrentHour(), tp.getCurrentMinute(), 0);
                        cld.set(Calendar.MILLISECOND, 0);
                        settedDateAndTime = cld.getTime().getTime();
                        cld = null;
                    }
                });


                pickerDialog = bdr.create();
            }
            pickerDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        if (pickerDialog.isShowing()) {
            pickerDialog.cancel();
        } else {
            super.onBackPressed();
        }
    }
}
