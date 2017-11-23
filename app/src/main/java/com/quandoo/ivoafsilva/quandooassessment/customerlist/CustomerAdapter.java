package com.quandoo.ivoafsilva.quandooassessment.customerlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quandoo.ivoafsilva.quandooassessment.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerItemViewHolder> {
    /**
     * The List of this Adapter
     */
    private final List<CustomerModel> mCustomerModelList;

    /**
     * Constructor for this adapter
     *
     * @param customerModelList The list of the customers to show in this {@link RecyclerView}
     */
    public CustomerAdapter(List<CustomerModel> customerModelList) {
        mCustomerModelList = new ArrayList<>();
        mCustomerModelList.addAll(customerModelList);
    }

    @Override
    public CustomerItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_customer, parent, false);
        return new CustomerItemViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(CustomerItemViewHolder holder, int position) {
        CustomerModel customer = mCustomerModelList.get(position);
        holder.bindCustomer(customer);
    }

    @Override
    public int getItemCount() {
        return mCustomerModelList.size();
    }

    /**
     * View Holder for each customer entry
     */
    public static class CustomerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * {@link TextView} to hold the value of the customer's Id
         */
        private TextView mTextViewId;
        /**
         * {@link TextView} to hold the value of the customer's First Name
         */
        private TextView mTextViewFirstName;
        /**
         * {@link TextView} to hold the value of the customer's Last Name
         */
        private TextView mTextViewLastName;

        /**
         * Constructor for this Holder
         *
         * @param view the layout of the item this holder represents
         */
        public CustomerItemViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mTextViewId = view.findViewById(R.id.text_id);
            mTextViewFirstName = view.findViewById(R.id.text_first_name);
            mTextViewLastName = view.findViewById(R.id.text_last_name);
        }

        /**
         * Method to bind a customer model data to this holder
         *
         * @param customer The customer's data to be bound
         */
        public void bindCustomer(CustomerModel customer) {
            mTextViewId.setText(String.valueOf(customer.getId()));
            mTextViewFirstName.setText(customer.getCustomerFirstName());
            mTextViewLastName.setText(customer.getCustomerLastName());
        }

        /**
         * Method called when an item is clicked
         *
         * @param view The item that is clicked
         */
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Clicked customer position: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}
