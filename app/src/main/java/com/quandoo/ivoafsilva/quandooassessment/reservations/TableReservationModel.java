package com.quandoo.ivoafsilva.quandooassessment.reservations;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Model class that represents a table reservation
 */
public class TableReservationModel extends RealmObject implements Parcelable {
    /**
     * The id (or number) of the table
     */
    @PrimaryKey
    private int mId;
    /**
     * The customer's first name
     */
    private boolean mIsAvailable;

    /**
     * Empty Constructor to be used by reflection
     */
    public TableReservationModel() {
    }

    public TableReservationModel(int id, boolean isAvailable) {
        mId = id;
        mIsAvailable = isAvailable;
    }

    /**
     * @return The table id/number
     */
    public int getId() {
        return mId;
    }

    /**
     * @return Whether the table is available
     */
    public boolean isAvailable() {
        return mIsAvailable;
    }

    /**
     * Sets this table id
     *
     * @param id
     */
    public void setId(int id) {
        mId = id;
    }

    /**
     * Sets the table reservation
     *
     * @param isAvailable whether the table reservation is available
     */
    public void setIsAvailable(Boolean isAvailable) {
        mIsAvailable = isAvailable;
    }

    /**
     * @return The String representation of this reservation
     */
    @Override
    public String toString() {
        return "TableReservationModel{" +
                "mId=" + mId +
                ", mIsAvailable=" + mIsAvailable +
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
        dest.writeByte(mIsAvailable ? (byte) 1 : (byte) 0);
    }

    protected TableReservationModel(Parcel in) {
        mId = in.readInt();
        mIsAvailable = in.readByte() != 0;
    }

    public static final Creator<TableReservationModel> CREATOR = new Creator<TableReservationModel>() {
        @Override
        public TableReservationModel createFromParcel(Parcel source) {
            return new TableReservationModel(source);
        }

        @Override
        public TableReservationModel[] newArray(int size) {
            return new TableReservationModel[size];
        }
    };
}
