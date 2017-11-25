package com.quandoo.ivoafsilva.quandooassessment.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quandoo.ivoafsilva.quandooassessment.R;
import com.quandoo.ivoafsilva.quandooassessment.models.TableReservationModel;

import java.util.ArrayList;
import java.util.List;

public class TableReservationAdapter extends RecyclerView.Adapter<TableReservationAdapter.ReservationItemViewHolder> {
    /**
     * The List of this Adapter. Each boolean represents if the table is available.
     */
    private final List<TableReservationModel> mTableReservationsList;

    /**
     * Constructor for this adapter
     *
     * @param tableReservationsList The list of the tables to show in this {@link RecyclerView}
     */
    public TableReservationAdapter(List<TableReservationModel> tableReservationsList) {
        mTableReservationsList = new ArrayList<>();
        setTableReservationsList(tableReservationsList);
    }

    @Override
    public ReservationItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_table, parent, false);
        return new ReservationItemViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(ReservationItemViewHolder holder, int position) {
        TableReservationModel reservation = mTableReservationsList.get(position);
        holder.bindReservation(reservation);
    }

    @Override
    public int getItemCount() {
        return mTableReservationsList.size();
    }

    public List<TableReservationModel> getTableReservationsList() {
        return mTableReservationsList;
    }

    public void setTableReservationsList(List<TableReservationModel> reservationsList) {
        if (reservationsList != null && reservationsList.size() > 0) {
            mTableReservationsList.clear();
            mTableReservationsList.addAll(reservationsList);
            notifyDataSetChanged();
        }
    }

    /**
     * View Holder for each customer entry
     */
    public static class ReservationItemViewHolder extends RecyclerView.ViewHolder {
        /**
         * {@link TextView} to hold the number of the table
         */
        private TextView mTextViewTableNr;
        /**
         * {@link TextView} to hold the availability of the table
         */
        private TextView mTextViewTableAvailability;

        /**
         * Constructor for this Holder
         *
         * @param view the layout of the item this holder represents
         */
        public ReservationItemViewHolder(View view) {
            super(view);
            mTextViewTableNr = view.findViewById(R.id.text_table_nr);
            mTextViewTableAvailability = view.findViewById(R.id.text_table_availability);
        }

        /**
         * Method to bind a reservation to this holder
         *
         * @param reservation The table reservation model to be bound
         */
        public void bindReservation(TableReservationModel reservation) {
            mTextViewTableNr.setText(String.valueOf(reservation.getId()));
            mTextViewTableAvailability.setText(String.valueOf(reservation.isAvailable()));
            mTextViewTableAvailability.setBackgroundColor(reservation.isAvailable() ? Color.GREEN : Color.RED);
        }
    }
}
