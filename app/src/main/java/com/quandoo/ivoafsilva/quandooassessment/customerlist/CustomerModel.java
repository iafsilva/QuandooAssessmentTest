package com.quandoo.ivoafsilva.quandooassessment.customerlist;

import com.google.gson.annotations.SerializedName;

/**
 * Model class that represents a Customer
 */
public class CustomerModel {
    /**
     * The id of the Customer
     */
    @SerializedName("id")
    private int mId;

    /**
     * The customer's first name
     */
    @SerializedName("customerFirstName")
    private String mCustomerFirstName;

    /**
     * The customer's last name
     */
    @SerializedName("customerLastName")
    private String mCustomerLastName;

    /**
     * Empty Constructor to be used by reflection
     */
    public CustomerModel() {
    }

    public CustomerModel(int id, String customerFirstName, String customerLastName) {
        mId = id;
        mCustomerFirstName = customerFirstName;
        mCustomerLastName = customerLastName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "ID=" + mId +
                ", FirstName='" + mCustomerFirstName + '\'' +
                ", LastName='" + mCustomerLastName + '\'' +
                '}';
    }
}
