package com.quandoo.ivoafsilva.quandooassessment.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.quandoo.ivoafsilva.quandooassessment.customers.CustomerModel;
import com.quandoo.ivoafsilva.quandooassessment.reservations.TableReservationModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public final class RealmUtils {
    /**
     * TAG to use when logging
     */
    private static final String TAG = RealmUtils.class.getSimpleName();

    /**
     * Method to insert customers into the default Realm instance
     *
     * @param customers The list of customers to insert
     */
    public static void insertCustomers(final List<CustomerModel> customers) {
        Realm.Transaction insertCustomerTransaction = new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insertOrUpdate(customers);
            }
        };
        executeRealmTransaction(insertCustomerTransaction);
        Log.d(TAG, "insertCustomers successful");
    }

    /**
     * Method to insert customers into the default Realm instance
     */
    public static List<CustomerModel> getCustomers() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CustomerModel> allCustomers = realm.where(CustomerModel.class).findAll();
        Log.d(TAG, "getCustomers loaded" + allCustomers);
        return allCustomers;
    }

    /**
     * Method to insert table reservations into the default Realm instance
     *
     * @param reservations the list of reservations
     */
    public static void insertTableReservations(final List<TableReservationModel> reservations) {
        Realm.Transaction insertCustomerTransaction = new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insertOrUpdate(reservations);
            }
        };
        executeRealmTransaction(insertCustomerTransaction);
        Log.d(TAG, "insertTableReservations successful");
    }

    /**
     * Method to insert table reservations into the default Realm instance
     */
    public static List<TableReservationModel> getTableReservations() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<TableReservationModel> allTables = realm.where(TableReservationModel.class).findAll();
        Log.d(TAG, "getTableReservations loaded" + allTables);
        return allTables;
    }

    /**
     * Helper method to run a transaction and handle correctly the database instance
     *
     * @param transaction the transaction to be ran
     */
    public static void executeRealmTransaction(Realm.Transaction transaction) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(transaction);
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }
}
