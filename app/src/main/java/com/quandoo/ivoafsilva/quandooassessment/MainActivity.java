package com.quandoo.ivoafsilva.quandooassessment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.quandoo.ivoafsilva.quandooassessment.customers.CustomersActivity;
import com.quandoo.ivoafsilva.quandooassessment.reservations.TableReservationActivity;

public class MainActivity extends AppCompatActivity {
    /**
     * TAG to use when logging
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
