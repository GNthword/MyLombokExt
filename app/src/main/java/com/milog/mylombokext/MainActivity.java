package com.milog.mylombokext;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.milog.annotation.FunctionManager;

public class MainActivity extends Activity {

    @FunctionManager("function_state")
    private String string;

    @FunctionManager("function_log_state")
    private boolean isOpen;

    @FunctionManager("function_str_arr")
    private String[] strings;

    @FunctionManager("function_int")
    private int intTest;

    @FunctionManager("function_int_arr")
    private int[] ints;

    private TextView tvShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShow = findViewById(R.id.tv_show);

        //tvShow.setText(FunctionManager.getProperty("function_state"));

        User user = new User();

        if (user.IsMilo()) {
            tvShow.setText(user.getName() + "\n" + getShowString());
        }
    }

    private String getShowString() {
        StringBuilder sb = new StringBuilder();
        sb.append("string：").append(string)
                .append("\nisOpen：").append(isOpen)
                .append("\nstrings：");
        if (strings != null) {
            for (String s : strings) {
                sb.append(s).append(" ");
            }
        }
        sb.append("\nint：").append(intTest)
                .append("\nints：");
        if (ints != null) {
            for (int i : ints) {
                sb.append(i).append(" ");
            }
        }
        return sb.toString();
    }



}
