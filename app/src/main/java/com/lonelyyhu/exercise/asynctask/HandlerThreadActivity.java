package com.lonelyyhu.exercise.asynctask;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HandlerThreadActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvStatus;

    private HandlerThread progressHandlerThread;
    private Handler progressHandler;
    private ProgressWorkRunnable progressWorkRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);

        progressBar = findViewById(R.id.progressBar);
        tvStatus = findViewById(R.id.tv_status);

        progressHandlerThread = new HandlerThread("ProgressHandlerThread");
        progressHandlerThread.start();

        progressHandler = new Handler(progressHandlerThread.getLooper());

        progressWorkRunnable = new ProgressWorkRunnable();
    }

    public void onClick(View view) {

        progressHandler.removeCallbacks(progressWorkRunnable);
        progressHandler.post(progressWorkRunnable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (progressHandler != null) {
            progressHandler.removeCallbacks(progressWorkRunnable);
        }

        if (progressHandlerThread != null) {
            progressHandlerThread.quitSafely();
        }

    }

    private class ProgressWorkRunnable implements Runnable {

        @Override
        public void run() {

            Log.wtf("ProgressWorkRunnable", "run: start");

            tvStatus.post(new Runnable() {
                @Override
                public void run() {
                    tvStatus.setText("Running");
                    progressBar.setProgress(0);
                }
            });

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                progressBar.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.incrementProgressBy(10);
                    }
                });
            }

            tvStatus.post(new Runnable() {
                @Override
                public void run() {
                    tvStatus.setText("Done");
                }
            });

            Log.wtf("ProgressWorkRunnable", "run: done");

        }
    }
}
