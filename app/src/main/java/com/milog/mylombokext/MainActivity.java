package com.milog.mylombokext;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.milog.MyGetter;

public class MainActivity extends Activity {

    @MyGetter("function_state")
    private String string;

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
    }



}