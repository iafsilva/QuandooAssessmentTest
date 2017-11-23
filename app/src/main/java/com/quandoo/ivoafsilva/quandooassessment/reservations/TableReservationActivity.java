package com.quandoo.ivoafsilva.quandooassessment.reservations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.quandoo.ivoafsilva.quandooassessment.R;
import com.quandoo.ivoafsilva.quandooassessment.customers.CustomerModel;
import com.quandoo.ivoafsilva.quandooassessment.customers.CustomersActivity;
import com.quandoo.ivoafsilva.quandooassessment.network.QuandooService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableReservationActivity extends AppCompatActivity {
    /**
     * TAG to use when logging
     */
    private static final String TAG = TableReservationActivity.class.getSimpleName();
    /**
     * The list of table reservations
     */
    private ArrayList<Boolean> mTableReservationsList;
    /**
     * The {@link RecyclerView} that will represent the reservations
     */
    private RecyclerView mTableReservationsRecyclerView;
    /**
     * The {@link RecyclerView.LayoutManager} of the mTableReservationsRecyclerView
     */
    private RecyclerView.LayoutManager mLayoutManager;
    /**
     * The {@link RecyclerView.Adapter} of the mTableReservationsRecyclerView
     */
    private TableReservationAdapter mTableReservationAdapter;
    /**
     * The customer that is trying to reserve a table
     */
    private CustomerModel mCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_reservation);

        loadExtrasFromIntent(getIntent());
        bindCustomerToReservingCustomerView(mCustomer);
        //TODO: Load reservations from network AND CACHE
        QuandooService quandooService = QuandooService.getInstance();
        quandooService.getTableReservationsList().enqueue(new Callback<List<Boolean>>() {
            @Override
            public void onResponse(@NonNull Call<List<Boolean>> call, @NonNull Response<List<Boolean>> response) {
                Log.d(TAG, "getTableReservationsList | onResponse");
                mTableReservationsList = (ArrayList<Boolean>) response.body();
                if (mTableReservationsList == null) {
                    Log.w(TAG, "getTableReservationsList returned NULL response");
                    return;
                }
                Log.w(TAG, "getTableReservationsList" + mTableReservationsList.toString());
                mTableReservationAdapter.setTableReservationsList(mTableReservationsList);
            }

            @Override
            public void onFailure(@NonNull Call<List<Boolean>> call, @NonNull Throwable t) {
                Log.e(TAG, "getTableReservationsList | onFailure", t);
            }
        });

        mTableReservationsRecyclerView = findViewById(R.id.recycler_reservations);
        mTableReservationsRecyclerView.setHasFixedSize(true);
        //Set Layout Manager
        mLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mTableReservationsRecyclerView.setLayoutManager(mLayoutManager);
        //Set Adapter
        mTableReservationAdapter = new TableReservationAdapter(mTableReservationsList);
        mTableReservationsRecyclerView.setAdapter(mTableReservationAdapter);
    }

    /**
     * Retrieves and loads necessary extras into the classes' fields
     *
     * @param intent The intent received to get the extras from
     */
    public void loadExtrasFromIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        mCustomer = bundle.getParcelable(CustomersActivity.KEY_CUSTOMER);
    }

    /**
     * Binds the current customer info to the reserving customer view
     *
     * @param customer The customer to be bound
     */
    public void bindCustomerToReservingCustomerView(CustomerModel customer) {
        View view = findViewById(R.id.reserving_customer);
        ((TextView) view.findViewById(R.id.text_id)).setText(String.valueOf(customer.getId()));
        ((TextView) view.findViewById(R.id.text_first_name)).setText(customer.getCustomerFirstName());
        ((TextView) view.findViewById(R.id.text_last_name)).setText(customer.getCustomerLastName());
    }
}
