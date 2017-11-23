package com.quandoo.ivoafsilva.quandooassessment.reservations;

import android.graphics.Color;
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

public class TableReservationAdapter extends RecyclerView.Adapter<TableReservationAdapter.ReservationItemViewHolder> {
    /**
     * The List of this Adapter
     */
    private final List<Boolean> mTableReservationsList;

    /**
     * Constructor for this adapter
     *
     * @param tableReservationsList The list of the tables to show in this {@link RecyclerView}
     */
    public TableReservationAdapter(List<Boolean> tableReservationsList) {
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
        Boolean isAvailable = mTableReservationsList.get(position);
        holder.bindReservation(isAvailable);
    }

    @Override
    public int getItemCount() {
        return mTableReservationsList.size();
    }

    public void setTableReservationsList(List<Boolean> reservationsList){
        if(reservationsList != null && reservationsList.size()> 0) {
            int oldReservationsSize = mTableReservationsList.size();
            mTableReservationsList.clear();
            notifyItemRangeRemoved(0, oldReservationsSize);
            mTableReservationsList.addAll(reservationsList);
            notifyItemRangeInserted(0, reservationsList.size());
        }
    }

    /**
     * View Holder for each customer entry
     */
    public static class ReservationItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            view.setOnClickListener(this);
            mTextViewTableNr = view.findViewById(R.id.text_table_nr);
            mTextViewTableAvailability = view.findViewById(R.id.text_table_availability);
        }

        /**
         * Method to bind a reservation to this holder
         *
         * @param isAvailable Whether the table is reserved or not
         */
        public void bindReservation(Boolean isAvailable) {
            mTextViewTableNr.setText(String.valueOf(getAdapterPosition()));
            mTextViewTableAvailability.setText(isAvailable.toString());
            mTextViewTableAvailability.setBackgroundColor(isAvailable ? Color.GREEN : Color.RED);
        }

        /**
         * Method called when an item is clicked
         *
         * @param view The item that is clicked
         */
        @Override
        public void onClick(View view) {
            if(Boolean.valueOf(mTextViewTableAvailability.getText().toString()).equals(Boolean.FALSE)) {
                Toast.makeText(view.getContext(), "Table " + getAdapterPosition() + " is NOT available!", Toast.LENGTH_SHORT).show();
            } else {
                mTextViewTableNr.setText(String.valueOf(getAdapterPosition()));
                mTextViewTableAvailability.setText(Boolean.FALSE.toString());
                mTextViewTableAvailability.setBackgroundColor(Color.RED);
                Toast.makeText(view.getContext(), "Table reserved!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
