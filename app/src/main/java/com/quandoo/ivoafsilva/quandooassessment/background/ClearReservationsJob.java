package com.quandoo.ivoafsilva.quandooassessment.background;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.quandoo.ivoafsilva.quandooassessment.models.TableReservationModel;
import com.quandoo.ivoafsilva.quandooassessment.utils.RealmUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;

public class ClearReservationsJob extends Job {
    /**
     * Tag to use when logging
     */
    public static final String TAG = ClearReservationsJob.class.getSimpleName();
    /**
     * The JOB_TAG to use when referencing to this job
     */
    public static final String JOB_TAG = "clear.reservations";
    /**
     * The waiting time, in minutes, between invocations to this {@link Job}
     */
    private static final int JOB_WAITING_PERIOD = 15;

    @Override
    @NonNull
    protected Result onRunJob(@NonNull Params params) {
        Log.d(TAG, "Job started: " + JOB_TAG);
        final List<TableReservationModel> tableReservations = RealmUtils.getTableReservations();
        RealmUtils.executeRealmTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                for (TableReservationModel tableReservation : tableReservations) {
                    tableReservation.setIsAvailable(Boolean.TRUE);
                }
            }
        });
        Log.d(TAG, "Job finished: " + JOB_TAG);
        return Result.SUCCESS;
    }

    /**
     * Static method to create a {@link JobRequest} for this job
     * @return A {@link JobRequest} for this job
     */
    public static JobRequest buildJobRequest() {
        return new JobRequest.Builder(JOB_TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(JOB_WAITING_PERIOD))
                .build();
    }
}