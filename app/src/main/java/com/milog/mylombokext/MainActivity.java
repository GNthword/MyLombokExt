package com.milog.mylombokext;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.milog.annotation.FunctionManager;

public class MainActivity extends Activity {

    @FunctionManager("function_state")
    private String string = com.milog.mylombokext.app.MyApplication.getApplication().getString(R.string.function_state);

    //@FunctionManager("function_log_state")
    private boolean isOpen;

    private TextView tvShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShow = findViewById(R.id.tv_show);

        //tvShow.setText(FunctionManager.getProperty("function_state"));

        if (string != null) {
            tvShow.setText(string);
        }

        User user = new User();
        tvShow.setText(user.getName());

    }



}
