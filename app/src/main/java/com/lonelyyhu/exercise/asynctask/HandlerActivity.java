package com.lonelyyhu.exercise.asynctask;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HandlerActivity extends AppCompatActivity {

    private static final int PROGRESS_UPDATE_EVENT = 0;
    private static final int PROGRESS_DONE_EVENT = 1;
    private Handler progressHandler;

    private ProgressBar progressBar;
    private TextView tvStatus;

    private ProgressWorkThread progressThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        progressBar = findViewById(R.id.progressBar);
        tvStatus = findViewById(R.id.tv_status);

        progressHandler = new ProgressHandler();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (progressThread!=null) {
            progressThread.teminate();
        }

    }

    public void onClick(View view) {

        if (progressThread!=null) {
            progressThread.teminate();
        }

        tvStatus.setText("Running");
        progressBar.setProgress(0);

        progressThread = new ProgressWorkThread();

        progressThread.start();

    }

    private class ProgressHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.wtf("ProgressHandler", "handleMessage: msg.getWhen() = " + msg.getWhen());
            Log.wtf("ProgressHandler", "handleMessage: msg.what = " + msg.what);

            if (msg.what == PROGRESS_DONE_EVENT) {
                tvStatus.setText("Done");
                progressBar.setProgress(100);
            } else {
                progressBar.incrementProgressBy(10);
            }


        }
    }

    private class ProgressWorkThread extends Thread {

        boolean isDone;

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isDone) {
                    break;
                }

//                Message message = new Message();
//                message.what = PROGRESS_UPDATE_EVENT;

                progressHandler.sendEmptyMessage(PROGRESS_UPDATE_EVENT);
            }

            progressHandler.sendEmptyMessage(PROGRESS_DONE_EVENT);

            Log.wtf("ProgressWorkThread", "run: Thread done");

        }

        public void teminate() {

            if (isAlive()) {
                isDone = true;
                Thread.interrupted();
            }

        }
    }
}
