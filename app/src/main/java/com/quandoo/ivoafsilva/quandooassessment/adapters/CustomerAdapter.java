package com.quandoo.ivoafsilva.quandooassessment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quandoo.ivoafsilva.quandooassessment.R;
import com.quandoo.ivoafsilva.quandooassessment.models.CustomerModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerItemViewHolder> {
    /**
     * The List of this Adapter
     */
    private final List<CustomerModel> mCustomerModelList;

    /**
     * A copy of the list of this Adapter. Used when filtering.
     */
    private final List<CustomerModel> mCustomerModelCopy;

    /**
     * Constructor for this adapter
     *
     * @param customerModelList The list of the customers to show in this {@link RecyclerView}
     */
    public CustomerAdapter(List<CustomerModel> customerModelList) {
        mCustomerModelList = new ArrayList<>();
        mCustomerModelCopy = new ArrayList<>();
        setCustomerModelList(customerModelList);
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

    public List<CustomerModel> getCustomerModelList() {
        return mCustomerModelList;
    }

    /**
     * Clears the current dataset and takes a copy of the given one
     *
     * @param customerList The new dataset to be copied
     */
    public void setCustomerModelList(List<CustomerModel> customerList) {
        if (customerList != null && customerList.size() > 0) {
            mCustomerModelList.clear();
            mCustomerModelList.addAll(customerList);
            mCustomerModelCopy.addAll(customerList);
            notifyDataSetChanged();
        }
    }

    /**
     * Filters the visible list of customers
     * @param text The text to be filtered by
     */
    public void filter(String text) {
        mCustomerModelList.clear();
        if(text.isEmpty()){
            mCustomerModelList.addAll(mCustomerModelCopy);
        } else{
            text = text.toLowerCase();
            for(CustomerModel customer: mCustomerModelCopy){
                if(customer.getCustomerFirstName().toLowerCase().contains(text)
                        || customer.getCustomerLastName().toLowerCase().contains(text)){
                    mCustomerModelList.add(customer);
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * View Holder for each customer entry
     */
    public static class CustomerItemViewHolder extends RecyclerView.ViewHolder {
        /**
         * {@link TextView} to hold the value of the customer's Id
         */
        private final TextView mTextViewId;
        /**
         * {@link TextView} to hold the value of the customer's First Name
         */
        private final TextView mTextViewFirstName;
        /**
         * {@link TextView} to hold the value of the customer's Last Name
         */
        private final TextView mTextViewLastName;

        /**
         * Constructor for this Holder
         *
         * @param view the layout of the item this holder represents
         */
        public CustomerItemViewHolder(View view) {
            super(view);
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
            mTextViewId.setTag(customer);
            mTextViewId.setText(String.valueOf(customer.getId()));
            mTextViewFirstName.setText(customer.getCustomerFirstName());
            mTextViewLastName.setText(customer.getCustomerLastName());
        }
    }
}
