package com.quandoo.ivoafsilva.quandooassessment;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.quandoo.ivoafsilva.quandooassessment.background.QuandooJobCreator;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new QuandooJobCreator());
    }
}
