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
import android.widget.Toast;

import com.quandoo.ivoafsilva.quandooassessment.R;
import com.quandoo.ivoafsilva.quandooassessment.customers.CustomerModel;
import com.quandoo.ivoafsilva.quandooassessment.customers.CustomersActivity;
import com.quandoo.ivoafsilva.quandooassessment.network.QuandooService;
import com.quandoo.ivoafsilva.quandooassessment.utils.RecyclerItemClickListener;

import java.lang.ref.WeakReference;
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
     * Numbers of columns to be displayed
     */
    public static final int NR_COLUMNS = 3;
    /**
     * The customer that is trying to reserve a table
     */
    private CustomerModel mCustomer;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_reservation);
        //Retrieve extras from the bundle
        loadExtrasFromIntent(getIntent());
        if (mCustomer != null) {
            bindCustomerToReservingCustomerView(mCustomer);
        }
        //Create click listener
        RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                List<Boolean> tableReservationsList = mTableReservationAdapter.getTableReservationsList();
                Boolean isAvailable = tableReservationsList.get(position);
                if (!isAvailable) {
                    Toast.makeText(view.getContext(), "Table " + position + " is NOT available!", Toast.LENGTH_SHORT).show();
                } else {
                    tableReservationsList.set(position, Boolean.FALSE);
                    mTableReservationAdapter.notifyItemChanged(position);
                    Toast.makeText(view.getContext(), "Table reserved! Have a nice meal", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Get the recycler view
        mTableReservationsRecyclerView = findViewById(R.id.recycler_reservations);
        mTableReservationsRecyclerView.setHasFixedSize(true);
        //Set Layout Manager
        mLayoutManager = new GridLayoutManager(this, NR_COLUMNS, LinearLayoutManager.VERTICAL, false);
        mTableReservationsRecyclerView.setLayoutManager(mLayoutManager);
        //Set Adapter
        mTableReservationAdapter = new TableReservationAdapter(null);
        mTableReservationsRecyclerView.setAdapter(mTableReservationAdapter);
        //Set Recycler View Properties
        mTableReservationsRecyclerView.setHasFixedSize(true);
        mTableReservationsRecyclerView.addOnItemTouchListener(recyclerItemClickListener);
        //TODO: Load reservations from network AND CACHE
        QuandooService quandooService = QuandooService.getInstance();
        quandooService.getTableReservationsList().enqueue(new TableReservationCallback(this, mTableReservationAdapter));
    }

    /**
     * Retrieves and loads necessary extras into the classes' fields
     *
     * @param intent The intent received to get the extras from
     */
    public void loadExtrasFromIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if(bundle !=null) {
            mCustomer = bundle.getParcelable(CustomersActivity.KEY_CUSTOMER);
        }
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
        view.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        QuandooService.getInstance().getTableReservationsList().cancel();
    }

    //------------------ INNER CLASSES ----------------

    private static class TableReservationCallback implements Callback<List<Boolean>> {
        /**
         * TAG to use when logging
         */
        private static final String TAG = TableReservationCallback.class.getSimpleName();

        private WeakReference<TableReservationActivity> mActivityReference;

        private TableReservationAdapter mTableReservationAdapter;

        public TableReservationCallback(TableReservationActivity activity, TableReservationAdapter tableReservationAdapter) {
            mActivityReference = new WeakReference<>(activity);
            mTableReservationAdapter = tableReservationAdapter;
        }

        @Override
        public void onResponse(@NonNull Call<List<Boolean>> call, @NonNull Response<List<Boolean>> response) {
            if (mActivityReference.get() == null) {
                Log.w(TAG, "onResponse Activity does not exist. Returning.");
                return;
            }
            List<Boolean> body = response.body();
            Log.d(TAG, "onResponse" + body);
            if (body != null) {
                mTableReservationAdapter.setTableReservationsList(body);
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<Boolean>> call, @NonNull Throwable t) {
            Log.e(TAG, "onFailure", t);
        }
    }
}
