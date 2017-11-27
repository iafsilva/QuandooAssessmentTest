package com.quandoo.ivoafsilva.quandooassessment.background;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class QuandooJobCreator implements JobCreator {
    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag) {
            case ClearReservationsJob.JOB_TAG:
                return new ClearReservationsJob();
            default:
                return null;
        }
    }
}
