package com.example.kunda.aqiapp.data.sync;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import static com.example.kunda.aqiapp.utils.Constants.SYNC_TAG;

/**
 * Created by Kundan on 17-10-2018.
 */
public class SyncUtils {

    /*
     * Interval at which to sync with the weather. Use TimeUnit for convenience, rather than
     * writing out a bunch of multiplication ourselves and risk making a silly mistake.
     */
    private static final int SYNC_INTERVAL_HOURS = 3;
//  private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_INTERVAL_SECONDS = 30;

    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;

    private static boolean sInitialized;

    synchronized public static void initializeSync(Context context) {
        /*
         * Only perform initialization once per app lifetime. If initialization has already been
         * performed, we have nothing to do in this method.
         */
        if (sInitialized) return;

        sInitialized = true;
        scheduleFirebaseJobDispatcher(context);
    }

    private static void scheduleFirebaseJobDispatcher(Context context) {
        com.firebase.jobdispatcher.Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(driver);

        /* Create the Job to periodically sync the app data */
        Job syncLocationDataJob = jobDispatcher.newJobBuilder()
                /* The Service that will be used to sync data */
                .setService(SyncDataFirebaseService.class)
                /* Set the UNIQUE tag used to identify this Job */
                .setTag(SYNC_TAG)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .setConstraints(Constraint.DEVICE_CHARGING)
                /*
                 * setLifetime sets how long this job should persist. The options are to keep the
                 * Job "forever" or to have it die the next time the device boots up.
                 */
                .setLifetime(Lifetime.FOREVER)
                /*
                 * We want this Job to recur.
                 */
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                /*
                 * If a Job with the tag with provided already exists, this new job will replace
                 * the old one.
                 */
                .setReplaceCurrent(true)
                /* Once the Job is ready, call the builder's build method to return the Job */
                .build();

        /* Schedule the Job with the dispatcher */
        jobDispatcher.schedule(syncLocationDataJob);
    }
}
