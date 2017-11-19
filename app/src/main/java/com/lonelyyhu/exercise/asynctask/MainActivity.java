package com.lonelyyhu.exercise.asynctask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        Intent intent = null;

        switch (view.getId()) {

            case R.id.btn_asynctask:
                intent = new Intent(this, AsyncTaskActivity.class);
                break;

            case R.id.btn_handler:
                intent = new Intent(this, HandlerActivity.class);
                break;

            case R.id.btn_handler_thread:
                intent = new Intent(this, HandlerThreadActivity.class);
                break;

            case R.id.btn_executor_service:
                intent = new Intent(this, ExecutorServiceActivity.class);
                break;

            case R.id.btn_intent_service:
                intent = new Intent(this, IntentServiceActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }


    }
}
