package com.quandoo.ivoafsilva.quandooassessment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.evernote.android.job.JobManager;
import com.quandoo.ivoafsilva.quandooassessment.R;
import com.quandoo.ivoafsilva.quandooassessment.background.ClearReservationsJob;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    /**
     * TAG to use when logging
     */
    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * Name for the Realm used within this app
     */
    private static final String REALM_NAME = "quandooAssessment.realm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Init Realm
        Realm.init(getApplication());
        RealmConfiguration config = new RealmConfiguration.Builder().name(REALM_NAME).build();
        Realm.setDefaultConfiguration(config);
        //Init background job
        JobManager.instance().schedule(ClearReservationsJob.buildJobRequest());
    }

    /**
     * Method to show the Customers Activity
     *
     * @param view The view that was clicked
     */
    public void showCustomersActivity(View view) {
        Intent intent = new Intent(MainActivity.this, CustomersActivity.class);
        startActivity(intent);
    }

    /**
     * Method to show the Customers Activity
     *
     * @param view The view that was clicked
     */
    public void showTableReservationsActivity(View view) {
        Intent intent = new Intent(MainActivity.this, TableReservationActivity.class);
        startActivity(intent);
    }
}
