package com.example.kunda.aqiapp.data.sync;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.Calendar;

import static com.example.kunda.aqiapp.utils.Constants.SYNC_TAG;

/**
 * Created by Kundan on 17-10-2018.
 * Used Sunshine app as a reference to create this Firebase Job Dispatcher
 * This Util class will be used to initialize data sync from server
 */
public class SyncUtils {

    // Flex time will be 5 minutes
    private static final int SYNC_FLEXTIME_SECONDS = 300;

    private static boolean sInitialized;

    /**
     * Initialize the sync of data , if data is already syncing it will return
     * @param context the activity
     */
    synchronized public static void initializeSync(Context context) {
        /*
         * Only perform initialization once per app lifetime. If initialization has already been
         * performed, we have nothing to do in this method.
         */
        if (sInitialized) return;

        sInitialized = true;
        scheduleFirebaseJobDispatcher(context);
    }

    /**
     * This function creates a schedule to start syncData Intent Service
     * this sync will happen every day at 1 AM
     * @param context the activity context
     */
    private static void scheduleFirebaseJobDispatcher(Context context) {
        com.firebase.jobdispatcher.Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(driver);
        // Sync will occur every day at 1 AM
        int startSeconds = getTimeInSecondsToStart();
        /* Create the Job to periodically sync the app data */
        Job syncLocationDataJob = jobDispatcher.newJobBuilder()
                /* The Service that will be used to sync data */
                .setService(SyncDataFirebaseService.class)
                /* Set the UNIQUE tag used to identify this Job */
                .setTag(SYNC_TAG)
                /* Net work restraint*/
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                /* Device should be charging*/
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
                /* Sync will occur every day at 1 AM at a window of 5 minutes */
                .setTrigger(Trigger.executionWindow(startSeconds,startSeconds + SYNC_FLEXTIME_SECONDS))
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

    /**
     * got method from : https://stackoverflow.com/questions/45836789/android-how-schedule-task-for-every-night-with-jobdispatcher-api
     * @return the time at which the Job should start
     */
    private static int getTimeInSecondsToStart(){
        int startSeconds;
        // Time at which the job is scheduled , currently 1 AM
        int HOURS = 1;
        int MINUTES = 0;
        int SECONDS = 0;
        int MILLISECONDS = 0;
        int AM_PM = Calendar.AM;

        Calendar now = Calendar.getInstance();
        Calendar midNight = Calendar.getInstance();
        midNight.set(Calendar.HOUR, HOURS);
        midNight.set(Calendar.MINUTE, MINUTES);
        midNight.set(Calendar.SECOND, SECONDS);
        midNight.set(Calendar.MILLISECOND, MILLISECONDS);
        midNight.set(Calendar.AM_PM, AM_PM);

        long diff = now.getTimeInMillis() - midNight.getTimeInMillis();

        if (diff < 0) {
            midNight.add(Calendar.DAY_OF_MONTH, 1);
            diff = midNight.getTimeInMillis() - now.getTimeInMillis();
        }
        startSeconds = (int) (diff / 1000);
        return startSeconds;
    }
}
