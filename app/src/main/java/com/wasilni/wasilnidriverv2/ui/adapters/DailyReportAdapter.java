package com.wasilni.wasilnidriverv2.ui.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wasilni.wasilnidriverv2.R;
import com.wasilni.wasilnidriverv2.mvp.model.Booking;

import java.util.List;


public class DailyReportAdapter extends RecyclerView.Adapter<DailyReportAdapter.MyViewHolder> {

    private Activity activity ;
    private List<Booking> list ;

    public DailyReportAdapter(Activity activity, List<Booking> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_report_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            Booking booking = list.get(position) ;
            holder.name.setText(booking.getName());
            holder.src.setText(booking.getPickUpName());
            holder.des.setText(booking.getPullDownName());
            holder.distance.setText(""+booking.getSummary().getKm_count());
            holder.duration.setText(""+booking.getSummary().getBooking_time());
            holder.waitingTime.setText(""+booking.getSummary().getWaiting_time_count());
//            holder.calcCost.setText(booking.getCalcCost());
//            holder.bookingCost.setText(booking.getBookingCost());
//            holder.deliveredMoney.setText(booking.getDeliveredMoney());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, src , des ,distance , duration ,
                waitingTime , calcCost ,bookingCost , deliveredMoney;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            src = itemView.findViewById(R.id.ride_source);
            des = itemView.findViewById(R.id.ride_destination);
            distance = itemView.findViewById(R.id.distance);
            duration = itemView.findViewById(R.id.duration);
            waitingTime = itemView.findViewById(R.id.wait_time);
            calcCost = itemView.findViewById(R.id.calculated_cost);
            bookingCost = itemView.findViewById(R.id.booking_cost);
            deliveredMoney = itemView.findViewById(R.id.delivered_money);
        }
    }
}