package com.wasilni.wasilnidriverv2.ui.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wasilni.wasilnidriverv2.R;
import com.wasilni.wasilnidriverv2.mvp.model.Booking;
import com.wasilni.wasilnidriverv2.mvp.model.BookingSummary;
import com.wasilni.wasilnidriverv2.mvp.model.Ride;
import com.wasilni.wasilnidriverv2.mvp.presenter.DailyReportPresenter;
import com.wasilni.wasilnidriverv2.ui.adapters.DailyReportAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.wasilni.wasilnidriverv2.DriverApplication.updateResources;

public class DailyReportActivity extends AppCompatActivity implements DailyReportPresenter.OnResponseInterface {
    RecyclerView recyclerView ;
    DailyReportAdapter adapter ;
    TextView date ;
    DailyReportPresenter presenter = new DailyReportPresenter(this,this) ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateResources(this, "ar");
        setContentView(R.layout.activity_daily_report);

        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        date = findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        date.setText(formattedDate);
        adapter = new DailyReportAdapter(this  ) ;
        recyclerView.setAdapter(adapter);
        presenter.sendToServer(null);
    }

    @Override
    public void populateBooking(List<Ride> bookings) {
        adapter.setList(bookings);
        adapter.notifyDataSetChanged();
    }
}
