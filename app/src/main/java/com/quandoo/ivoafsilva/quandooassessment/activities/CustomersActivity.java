package com.quandoo.ivoafsilva.quandooassessment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;

import com.quandoo.ivoafsilva.quandooassessment.R;
import com.quandoo.ivoafsilva.quandooassessment.adapters.CustomerAdapter;
import com.quandoo.ivoafsilva.quandooassessment.models.CustomerModel;
import com.quandoo.ivoafsilva.quandooassessment.utils.RealmUtils;
import com.quandoo.ivoafsilva.quandooassessment.network.QuandooService;
import com.quandoo.ivoafsilva.quandooassessment.listeners.RecyclerItemClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomersActivity extends AppCompatActivity {
    /**
     * TAG to use when logging
     */
    private static final String TAG = CustomersActivity.class.getSimpleName();
    /**
     * Key to use when sending/retrieving a customer
     */
    public static final String KEY_CUSTOMER = "aCustomer";
    /**
     * Key to use when sending/retrieving the customers list from a bundle
     */
    public static final String KEY_CUSTOMER_LIST = "customerList";
    /**
     * The {@link RecyclerView} containing the customers list
     */
    private RecyclerView mCustomersRecyclerView;
    /**
     * The {@link RecyclerView.LayoutManager} for the mCustomersRecyclerView
     */
    private LinearLayoutManager mLayoutManager;
    /**
     * The {@link android.support.v7.widget.RecyclerView.Adapter} to use for the mCustomersRecyclerView
     */
    private CustomerAdapter mCustomerAdapter;
    /**
     * The customer model list that will be added to the adapter.
     */
    private static List<CustomerModel> mCustomerModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        //Retrieve state from the bundle
        loadStateFromBundle(getIntent(), savedInstanceState);
        //Create click listener
        RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), TableReservationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(CustomersActivity.KEY_CUSTOMER, mCustomerAdapter.getCustomerModelList().get(position));
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        //Get the recycler view
        mCustomersRecyclerView = findViewById(R.id.recycler_customers);
        //Set Layout Manager
        mLayoutManager = new LinearLayoutManager(this);
        mCustomersRecyclerView.setLayoutManager(mLayoutManager);
        //Set Adapter
        mCustomerAdapter = new CustomerAdapter(null);
        mCustomersRecyclerView.setAdapter(mCustomerAdapter);
        //Set Recycler View Properties
        mCustomersRecyclerView.setHasFixedSize(true);
        mCustomersRecyclerView.addOnItemTouchListener(recyclerItemClickListener);
        mCustomersRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //Link the SearchView to the adapter
        SearchView searchBox = findViewById(R.id.search_customers);
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mCustomerAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCustomerAdapter.filter(newText);
                return true;
            }
        });
        //Load data
        loadCustomersList();
    }

    /**
     * Method to load customers list from different sources
     */
    private void loadCustomersList() {
        if (mCustomerModelList != null && mCustomerModelList.size() > 0) {
            //Loaded from last state
            mCustomerAdapter.setCustomerModelList(mCustomerModelList);
            return;
        }
        mCustomerModelList = RealmUtils.getCustomers();
        if (mCustomerModelList != null && mCustomerModelList.size() > 0) {
            //Loaded from Realm
            mCustomerAdapter.setCustomerModelList(mCustomerModelList);
            return;
        }
        //Get the customers from network and handle response
        QuandooService quandooService = QuandooService.getInstance();
        quandooService.getCustomerList().enqueue(new CustomerCallback(this, mCustomerAdapter));
    }

    /**
     * Loads from bundle into the classes' fields
     *
     * @param intent             The intent received to get the extras from
     * @param savedInstanceState The savedInstanceState to get the extras from
     */
    public void loadStateFromBundle(Intent intent, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            savedInstanceState = intent.getExtras();
        }

        if (savedInstanceState != null) {
            mCustomerModelList = savedInstanceState.getParcelableArrayList(KEY_CUSTOMER_LIST);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_CUSTOMER_LIST, (ArrayList<? extends Parcelable>) mCustomerAdapter.getCustomerModelList());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        QuandooService.getInstance().getCustomerList().cancel();
    }

    //------------------ INNER CLASSES ----------------

    private static class CustomerCallback implements Callback<List<CustomerModel>> {
        /**
         * TAG to use when logging
         */
        private static final String TAG = CustomersActivity.class.getSimpleName();

        private WeakReference<CustomersActivity> mActivityReference;

        private CustomerAdapter mCustomerAdapter;

        public CustomerCallback(CustomersActivity activity, CustomerAdapter customerAdapter) {
            mActivityReference = new WeakReference<>(activity);
            mCustomerAdapter = customerAdapter;
        }

        @Override
        public void onResponse(@NonNull Call<List<CustomerModel>> call, @NonNull Response<List<CustomerModel>> response) {
            if (mActivityReference.get() == null) {
                Log.w(TAG, "onResponse Activity does not exist. Returning.");
                return;
            }
            mCustomerModelList = response.body();
            Log.d(TAG, "onResponse" + mCustomerModelList);
            if (mCustomerModelList != null) {
                mCustomerAdapter.setCustomerModelList(mCustomerModelList);
                RealmUtils.insertCustomers(mCustomerModelList);
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<CustomerModel>> call, @NonNull Throwable t) {
            Log.e(TAG, "onFailure", t);
        }
    }
}
