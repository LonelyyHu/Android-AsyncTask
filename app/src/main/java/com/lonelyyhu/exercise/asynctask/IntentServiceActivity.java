package com.lonelyyhu.exercise.asynctask;

import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.lonelyyhu.exercise.asynctask.ProgressIntentService.RECEIVER_RESULT_DONE;
import static com.lonelyyhu.exercise.asynctask.ProgressIntentService.RECEIVER_RESULT_KEY_PROGRESS;
import static com.lonelyyhu.exercise.asynctask.ProgressIntentService.RECEIVER_RESULT_PROGRESSING;
import static com.lonelyyhu.exercise.asynctask.ProgressIntentService.RECEIVER_RESULT_START;

public class IntentServiceActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        progressBar = findViewById(R.id.progressBar);
        tvStatus = findViewById(R.id.tv_status);
    }

    public void onClick(View view) {

        ProgressIntentService.startActionProgressStart(this, 1, new ProgressServiceReceiver(new Handler()));
    }

    private class ProgressServiceReceiver extends ResultReceiver {

        public ProgressServiceReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            Log.wtf("ProgressServiceReceiver", "onReceiveResult: " + resultCode);

            super.onReceiveResult(resultCode, resultData);

            if (RECEIVER_RESULT_PROGRESSING == resultCode) {

                int p = resultData.getInt(RECEIVER_RESULT_KEY_PROGRESS);
                progressBar.setProgress(p);

            } else if (RECEIVER_RESULT_DONE == resultCode) {

                progressBar.setProgress(100);
                tvStatus.setText("Done");

            } else if (RECEIVER_RESULT_START == resultCode) {
                progressBar.setProgress(0);
                tvStatus.setText("Progressing");
            }

        }
    }
}
