package com.lonelyyhu.exercise.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsyncTaskActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        progressBar = findViewById(R.id.progressBar);
        tvStatus = findViewById(R.id.tv_status);


    }

    public void onClick(View view) {
//        progressBar.incrementProgressBy(10);

        MyTask task = new MyTask();
        task.execute(10);

        progressBar.setProgress(0);
        tvStatus.setText("Running");

    }


    private class MyTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress(integers[0]);

            }

            return "Done";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            progressBar.incrementProgressBy(values[0]);

        }

        @Override
        protected void onPostExecute(String s) {

            progressBar.setProgress(100);
            tvStatus.setText(s);

        }
    }
}
