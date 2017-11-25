package com.quandoo.ivoafsilva.quandooassessment.customers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;

import com.quandoo.ivoafsilva.quandooassessment.R;
import com.quandoo.ivoafsilva.quandooassessment.network.QuandooService;
import com.quandoo.ivoafsilva.quandooassessment.reservations.TableReservationActivity;
import com.quandoo.ivoafsilva.quandooassessment.utils.RecyclerItemClickListener;

import java.lang.ref.WeakReference;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
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
        //Get the customers lists and handle response
        QuandooService quandooService = QuandooService.getInstance();
        quandooService.getCustomerList().enqueue(new CustomerCallback(this, mCustomerAdapter));
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
            List<CustomerModel> body = response.body();
            Log.d(TAG, "onResponse" + body);
            if (body != null) {
                mCustomerAdapter.setCustomerModelList(body);
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<CustomerModel>> call, @NonNull Throwable t) {
            Log.e(TAG, "onFailure", t);
        }
    }
}
