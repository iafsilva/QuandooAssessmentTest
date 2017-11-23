package com.quandoo.ivoafsilva.quandooassessment.customers;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Model class that represents a Customer
 */
public class CustomerModel implements Parcelable {
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

    /**
     * @return The customer's id
     */
    public int getId() {
        return mId;
    }

    /**
     * @return The customer's first name
     */
    public String getCustomerFirstName() {
        return mCustomerFirstName;
    }

    /**
     * @return The customer's last name
     */
    public String getCustomerLastName() {
        return mCustomerLastName;
    }

    /**
     * @return The String representation of this customer
     */
    @Override
    public String toString() {
        return "Customer{" +
                "ID=" + mId +
                ", FirstName='" + mCustomerFirstName + '\'' +
                ", LastName='" + mCustomerLastName + '\'' +
                '}';
    }

    // --------------- PARCELABLE METHODS ---------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mCustomerFirstName);
        dest.writeString(mCustomerLastName);
    }

    private CustomerModel(Parcel in) {
        mId = in.readInt();
        mCustomerFirstName = in.readString();
        mCustomerLastName = in.readString();
    }

    public static final Parcelable.Creator<CustomerModel> CREATOR = new Parcelable.Creator<CustomerModel>() {
        @Override
        public CustomerModel createFromParcel(Parcel source) {
            return new CustomerModel(source);
        }

        @Override
        public CustomerModel[] newArray(int size) {
            return new CustomerModel[size];
        }
    };
}
