package com.quandoo.ivoafsilva.quandooassessment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.quandoo.ivoafsilva.quandooassessment.customerlist.CustomerModel;
import com.quandoo.ivoafsilva.quandooassessment.network.QuandooService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    /**
     * TAG to use when logging
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * The list of customers
     */
    private List<CustomerModel> mCustomersList;

    /**
     * The list of reservations
     */
    private List<Boolean> mTableReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuandooService quandooService = QuandooService.getInstance();
        quandooService.getCustomerList().enqueue(new Callback<List<CustomerModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CustomerModel>> call, @NonNull Response<List<CustomerModel>> response) {
                Log.d(TAG, "getCustomerList | onResponse");

                mCustomersList = response.body();
                if (mCustomersList == null) {
                    Log.w(TAG, "getCustomerList returned NULL response");
                    return;
                }

                Log.d(TAG, "getCustomerList" + mCustomersList.toString());
            }

            @Override
            public void onFailure(@NonNull Call<List<CustomerModel>> call, @NonNull Throwable t) {
                Log.e(TAG, "getCustomerList | onFailure", t);
            }
        });

        quandooService.getTableReservationsList().enqueue(new Callback<List<Boolean>>() {
            @Override
            public void onResponse(@NonNull Call<List<Boolean>> call, @NonNull Response<List<Boolean>> response) {
                Log.d(TAG, "getTableReservationsList | onResponse");
                mTableReservations = response.body();
                if (mTableReservations == null) {
                    Log.w(TAG, "getTableReservationsList returned NULL response");
                    return;
                }
                Log.w(TAG, "getTableReservationsList" + mTableReservations.toString());
            }

            @Override
            public void onFailure(@NonNull Call<List<Boolean>> call, @NonNull Throwable t) {
                Log.e(TAG, "getTableReservationsList | onFailure", t);
            }
        });
    }
}
