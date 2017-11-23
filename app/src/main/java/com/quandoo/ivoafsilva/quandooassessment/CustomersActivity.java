package com.quandoo.ivoafsilva.quandooassessment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.quandoo.ivoafsilva.quandooassessment.customerlist.CustomerAdapter;
import com.quandoo.ivoafsilva.quandooassessment.customerlist.CustomerModel;

import java.util.ArrayList;

public class CustomersActivity extends AppCompatActivity {
    /**
     * Key to use when sending/retrieving the customers list from a bundle
     */
    public static final String KEY_CUSTOMER_LIST = "customerList";
    /**
     * The customers list to show
     */
    private ArrayList<CustomerModel> mCustomersList;
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

        //Retrieve  from the bundle
        loadExtrasFromIntent(getIntent());

        mCustomersRecyclerView = findViewById(R.id.recycler_customers);
        mCustomersRecyclerView.setHasFixedSize(true);
        //Set Layout Manager
        mLayoutManager = new LinearLayoutManager(this);
        mCustomersRecyclerView.setLayoutManager(mLayoutManager);
        //Set Adapter
        mCustomerAdapter = new CustomerAdapter(mCustomersList);
        mCustomersRecyclerView.setAdapter(mCustomerAdapter);
        //Set Recycler View Properties
        mCustomersRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    /**
     * Retrieves and loads necessary extras into the classes' fields
     *
     * @param intent The intent received to get the extras from
     */
    public void loadExtrasFromIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        mCustomersList = bundle.getParcelableArrayList(KEY_CUSTOMER_LIST);
    }
}
