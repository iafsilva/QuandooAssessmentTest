package com.quandoo.ivoafsilva.quandooassessment.network;

import com.quandoo.ivoafsilva.quandooassessment.BuildConfig;
import com.quandoo.ivoafsilva.quandooassessment.customers.CustomerModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Singleton representing the Quandoo Service.
 * Consists of the Quandoo API and helper methods to retrieve information from it.
 */
public final class QuandooService {
    /**
     * The instance of this Singleton class
     */
    private static volatile QuandooService sInstance;

    /**
     * An instance of the {@link QuandooAPI} to be used internally.
     */
    private static QuandooAPI sQuandooApi;

    /**
     * Private constructor
     */
    private QuandooService() {
        //No instance
    }

    /**
     * Method to return the instance of this Singleton
     *
     * @return The only instance of {@link QuandooService}
     */
    public static QuandooService getInstance() {
        if (sInstance == null) {
            synchronized (QuandooService.class) {
                if (sInstance == null) {
                    sInstance = new QuandooService();
                    init();
                }
            }
        }
        return sInstance;
    }

    /**
     * Method that initializes this Singleton
     */
    private static void init() {
        sQuandooApi = new Retrofit.Builder()
                .baseUrl(BuildConfig.QUANDOO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QuandooAPI.class);
    }

    /**
     * Get customers list
     *
     * @return Array of customers.
     */
    public Call<List<CustomerModel>> getCustomerList() {
        return sQuandooApi.getCustomerList();
    }

    /**
     * Get table reservations
     *
     * @return Array of customers.
     */
    public Call<List<Boolean>> getTableReservationsList() {
        return sQuandooApi.getTableReservationsMap();
    }

    /**
     * Interface representing the Quandoo API
     */
    private interface QuandooAPI {

        /**
         * Get customers list
         * <p>
         * URL: https://s3-eu-west-1.amazonaws.com/quandoo-assessment/customer-list.json
         * Path: quandoo-assessment/customer-list.json
         *
         * @return Array of customers.
         */
        @GET("customer-list.json")
        Call<List<CustomerModel>> getCustomerList();

        /**
         * Get reservations table map
         * <p>
         * URL: https://s3-eu-west-1.amazonaws.com/quandoo-assessment/table-map.json
         * Path: quandoo-assessment/table-map.json
         *
         * @return Array of booleans each representing table availability.
         */
        @GET("table-map.json")
        Call<List<Boolean>> getTableReservationsMap();
    }
}
