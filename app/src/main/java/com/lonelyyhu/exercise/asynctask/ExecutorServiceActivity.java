package com.lonelyyhu.exercise.asynctask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvStatus;

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executor_service);

        progressBar = findViewById(R.id.progressBar);
        tvStatus = findViewById(R.id.tv_status);

        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (executorService != null) {
            executorService.shutdown();
        }

    }

    public void onClick(View view) {

        Log.wtf("ExecutorServiceActivity", "onClick: ");
        
        executorService.execute(new ProgressWorkRunnable());
//        executorService.submit(new ProgressWorkRunnable());

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
