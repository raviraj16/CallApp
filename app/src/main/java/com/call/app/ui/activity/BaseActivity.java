package com.call.app.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by RAVI on 1/25/2017.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initValue();
        initClickListener();
    }

    protected void setContentView() {

    }

    protected void initValue() {
    }

    protected void initClickListener() {
    }

    protected void initView() {
    }
}
