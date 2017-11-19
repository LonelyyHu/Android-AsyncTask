package com.lonelyyhu.exercise.asynctask;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ProgressIntentService extends IntentService {

    private static final String ACTION_START_PROGRESS = "com.lonelyyhu.exercise.asynctask.action.FOO";

    private static final String EXTRA_SLEEP_SECONDS = "com.lonelyyhu.exercise.asynctask.extra.EXTRA_SLEEP_SECONDS";
    private static final String EXTRA_RESULT_RECEIVER = "com.lonelyyhu.exercise.asynctask.extra.EXTRA_RECEIVER";

    public static final int RECEIVER_RESULT_START = 99;
    public static final int RECEIVER_RESULT_PROGRESSING = 100;
    public static final int RECEIVER_RESULT_DONE = 101;

    public static final String RECEIVER_RESULT_KEY_PROGRESS = "RECEIVER_RESULT_KEY_PROGRESS";

    public ProgressIntentService() {
        super("ProgressIntentService");
    }


    public static void startActionProgressStart(Context context, int sleepSec, ResultReceiver resultReceiver) {
        Intent intent = new Intent(context, ProgressIntentService.class);
        intent.setAction(ACTION_START_PROGRESS);
        intent.putExtra(EXTRA_SLEEP_SECONDS, sleepSec);
        intent.putExtra(EXTRA_RESULT_RECEIVER, resultReceiver);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START_PROGRESS.equals(action)) {
                final int sleepSec = intent.getIntExtra(EXTRA_SLEEP_SECONDS, 1);
                ResultReceiver resultReceiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionProgressStart(sleepSec, resultReceiver);
            }
        }
    }

    private void handleActionProgressStart(int sleepSec, ResultReceiver resultReceiver) {

        resultReceiver.send(RECEIVER_RESULT_START, genProgressBundle(0));

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(sleepSec * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            resultReceiver.send(RECEIVER_RESULT_PROGRESSING, genProgressBundle(i * 10));


        }

        resultReceiver.send(RECEIVER_RESULT_DONE, genProgressBundle(100));


    }

    private Bundle genProgressBundle(int progress) {
        Bundle dataBundle = new Bundle();
        dataBundle.putInt(RECEIVER_RESULT_KEY_PROGRESS, progress);
        return dataBundle;
    }
}
