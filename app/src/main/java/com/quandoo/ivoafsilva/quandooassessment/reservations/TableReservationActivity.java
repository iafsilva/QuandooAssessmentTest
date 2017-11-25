package com.quandoo.ivoafsilva.quandooassessment.reservations;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.quandoo.ivoafsilva.quandooassessment.database.RealmUtils;
import com.quandoo.ivoafsilva.quandooassessment.network.QuandooService;
import com.quandoo.ivoafsilva.quandooassessment.utils.RecyclerItemClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
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
     * Key to use when sending/retrieving the reservations list from a bundle
     */
    public static final String KEY_RESERVATIONS_LIST = "reservationsList";
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
    /**
     * The table reservations list that will be added to the adapter
     */
    private List<TableReservationModel> mTableReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_reservation);
        //Retrieve state from the bundle
        loadStateFromBundle(getIntent(), savedInstanceState);
        if (mCustomer != null) {
            bindCustomerToReservingCustomerView(mCustomer);
        }
        //Create click listener
        RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mCustomer == null) {
                    Toast.makeText(view.getContext(), "No customer is selected.", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<TableReservationModel> tableReservationsList = mTableReservationAdapter.getTableReservationsList();
                final TableReservationModel table = tableReservationsList.get(position);
                if (!table.isAvailable()) {
                    Toast.makeText(view.getContext(), "Table " + position + " is NOT available!", Toast.LENGTH_SHORT).show();
                } else {
                    RealmUtils.executeRealmTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            table.setIsAvailable(Boolean.FALSE);
                        }
                    });
                    mTableReservationAdapter.notifyItemChanged(position);
                    Toast.makeText(view.getContext(), "Table reserved! Have a nice meal.", Toast.LENGTH_SHORT).show();
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
        loadTableReservations();
    }

    /**
     * Method to load table reservations from different sources
     */
    private void loadTableReservations() {
        if (mTableReservations != null && mTableReservations.size() > 0) {
            //Loaded from last state
            mTableReservationAdapter.setTableReservationsList(mTableReservations);
            return;
        }
        mTableReservations = RealmUtils.getTableReservations();
        if (mTableReservations != null && mTableReservations.size() > 0) {
            //Loaded from Realm
            mTableReservationAdapter.setTableReservationsList(mTableReservations);
            return;
        }
        //If it could not be loaded, get the tableReservations from network and handle response
        QuandooService quandooService = QuandooService.getInstance();
        quandooService.getTableReservationsList().enqueue(new com.quandoo.ivoafsilva.quandooassessment.reservations.TableReservationActivity.TableReservationCallback(this, mTableReservationAdapter));
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

    /**
     * Retrieves and loads necessary extras into the classes' fields
     *
     * @param intent             The intent received to get the extras from
     * @param savedInstanceState The savedInstanceState to get the extras from
     */
    public void loadStateFromBundle(Intent intent, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            savedInstanceState = intent.getExtras();
        }

        if (savedInstanceState != null) {
            mCustomer = savedInstanceState.getParcelable(CustomersActivity.KEY_CUSTOMER);
            mTableReservations = savedInstanceState.getParcelableArrayList(KEY_RESERVATIONS_LIST);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CustomersActivity.KEY_CUSTOMER, mCustomer);
        outState.putParcelableArrayList(KEY_RESERVATIONS_LIST, (ArrayList<? extends Parcelable>) mTableReservationAdapter.getTableReservationsList());
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
            List<Boolean> reservations = response.body();
            Log.d(TAG, "onResponse" + reservations);
            if (reservations != null) {
                //Convert the array of booleans to the TableReservationModel
                List<TableReservationModel> tableReservationModel = new ArrayList<>();
                for (int i = 0; i < reservations.size(); i++) {
                    tableReservationModel.add(new TableReservationModel(i, reservations.get(i)));
                }
                mTableReservationAdapter.setTableReservationsList(tableReservationModel);
                RealmUtils.insertTableReservations(tableReservationModel);
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<Boolean>> call, @NonNull Throwable t) {
            Log.e(TAG, "onFailure", t);
        }
    }
}
