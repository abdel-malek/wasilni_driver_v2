package com.wasilni.wasilnidriverv2.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.OnSheetDismissedListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.wasilni.wasilnidriverv2.R;
import com.wasilni.wasilnidriverv2.ui.adapters.BookingAdapter;
import com.wasilni.wasilnidriverv2.mvp.model.Ride;
import com.wasilni.wasilnidriverv2.mvp.presenter.GetMyRidesPresenterImp;
import com.wasilni.wasilnidriverv2.ui.adapters.UpcomingRidesAdapter;
import com.wasilni.wasilnidriverv2.mvp.presenter.OnOffDriverPresenterImp;
import com.wasilni.wasilnidriverv2.mvp.view.HomeContract;
import com.wasilni.wasilnidriverv2.mvp.view.OnOffDriverContract;
import com.wasilni.wasilnidriverv2.mvp.view.RideContruct;
import com.wasilni.wasilnidriverv2.receivers.NotificationReceiver;
import com.wasilni.wasilnidriverv2.ui.Activities.Base.NavigationActivity;
import com.wasilni.wasilnidriverv2.ui.Dialogs.TripPassengersActionsFragment;
import com.wasilni.wasilnidriverv2.ui.Dialogs.RideSummaryFragment;
import com.wasilni.wasilnidriverv2.util.UtilFunction;
import com.wasilni.wasilnidriverv2.util.UtilUser;

import java.util.List;

import static com.wasilni.wasilnidriverv2.ui.Dialogs.TripPassengersActionsFragment.ischecked;

public class HomeActivity extends NavigationActivity implements
        TripPassengersActionsFragment.OnFragmentInteractionListener,
        RideSummaryFragment.OnFragmentInteractionListener ,
        BookingAdapter.OnAdapterInteractionListener,
        View.OnClickListener,
        OnMapReadyCallback,
        HomeContract.HomeView {

    public GoogleMap mMap;
    public RecyclerView recyclerView;
    public ImageView driverStatus;
    public TextView driverStatusTextView ;
    public LinearLayout onlineOfflineLayout ;
    public LinearLayout bottomLayout ;
    public ImageView notificationImageView ;
    public ConstraintLayout newOrderLayout ;
    public Button notificationButton ;
    public ImageButton passengersActionsBtn;
    public TripPassengersActionsFragment tripPassengersActionsFragment = TripPassengersActionsFragment.newInstance(this);
    public BottomSheetLayout bottomSheet;

    private NotificationReceiver notificationReceiver = new NotificationReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            recreate();
        }
    };


    public static final int PEEK_HEIGHT_DROP_OFF = 210;
    public static final int PEEK_HEIGHT_PICKED_UP = 150;
    public static final int PEEK_HEIGHT_NORMAL = 200;
    public static HomeActivity homeActivity ;


    public RideContruct.MyRidesPresenter myRidesPresenter ;
    private OnOffDriverContract.OnOffDriverPresenter onOffDriverPresenter = new OnOffDriverPresenterImp(this);

    public UpcomingRidesAdapter mAdapter ;

    @Override
    public void setRides(List<Ride> data) {
        mAdapter.setList(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ischecked = false;
        super.onCreate(savedInstanceState);
        UtilFunction.doExtends(mainLayout , this , R.layout.activity_home);
        homeActivity = this;
        myRidesPresenter = new GetMyRidesPresenterImp(this);
        initView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    @Override
    public void initView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        driverStatus = findViewById(R.id.driver_status_image) ;
        onlineOfflineLayout = findViewById(R.id.bottom_linear_layout) ;
        driverStatusTextView = findViewById(R.id.driver_status);
        notificationImageView = findViewById(R.id.notification_img);
        notificationImageView.bringToFront();
        newOrderLayout = findViewById(R.id.new_order_layout) ;
        bottomLayout = findViewById(R.id.bottom_layout) ;
        passengersActionsBtn = findViewById(R.id.passenger_actions_btn);
        recyclerView = findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new UpcomingRidesAdapter(tripPassengersActionsFragment) ;
        recyclerView.setAdapter(mAdapter);

        myRidesPresenter.sendToServer(null);



        // Init bottom sheet
        bottomSheet =  findViewById(R.id.bottomsheet);
        bottomSheet.setInterceptContentTouch(false);
        bottomSheet.setShouldDimContentView(false);
        bottomSheet.setPeekOnDismiss(true);
        bottomSheet.setPeekSheetTranslation(UtilFunction.convertDpToPx(this, PEEK_HEIGHT_NORMAL));

        bottomSheet.addOnSheetDismissedListener(new OnSheetDismissedListener() {
            @Override
            public void onDismissed(BottomSheetLayout bottomSheetLayout) {
                passengersActionsBtn.setVisibility(View.VISIBLE);
            }
        });
        checkDriverStatus();


        driverStatus.setOnClickListener(this);
        passengersActionsBtn.setOnClickListener(this);

    }



    @Override
    protected void onStart() {
        super.onStart();
        regesterRecivers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregesterRecivers();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.driver_status_image :
                driverStatusOnclick();
                break;
            case R.id.passenger_actions_btn:
                if(!tripPassengersActionsFragment.isAdded()) {
                    this.tripPassengersActionsFragment.show(getSupportFragmentManager(), R.id.bottomsheet);
                }
                if(ischecked) {
                    this.passengersActionsBtn.setVisibility(View.INVISIBLE);
                }
                else
                {
                    UtilFunction.showToast(this, "please select ride");
                }
                break;
        }
    }

    public void changePeekHeight(int height){
        bottomSheet.setPeekSheetTranslation(UtilFunction.convertDpToPx(this, height));

    }


    @Override
    public void itemChanged(String status) {
        switch (status){
            case "APPROVED" :{
                bottomSheet.setPeekSheetTranslation(UtilFunction.convertDpToPx(this, PEEK_HEIGHT_NORMAL));
                break;
            }
            case "STARTED" :{
                bottomSheet.setPeekSheetTranslation(UtilFunction.convertDpToPx(this, PEEK_HEIGHT_NORMAL));
                break;
            }
            case "ARRIVED" :{
                bottomSheet.setPeekSheetTranslation(UtilFunction.convertDpToPx(this, PEEK_HEIGHT_PICKED_UP));
                break;
            }
            case "PICKED_UP" :{
                bottomSheet.setPeekSheetTranslation(UtilFunction.convertDpToPx(this, PEEK_HEIGHT_DROP_OFF));
                break;
            }
            case "DONE" :{
                bottomSheet.setPeekSheetTranslation(UtilFunction.convertDpToPx(this, PEEK_HEIGHT_NORMAL));
                break;
            }
        }
        this.bottomSheet.peekSheet();
    }

    @Override
    public void driverStatusOnclick() {
        onOffDriverPresenter.sendToServer(null);
    }


    @Override
    public void regesterNotification() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.wasilni.wasilnidriverv2.receivers");
        registerReceiver(notificationReceiver , intentFilter) ;
    }

    @Override
    public void regesterRecivers() {
        regesterNotification();
    }

    @Override
    public void unregesterNotification() {
        unregisterReceiver(notificationReceiver); ;

    }

    @Override
    public void unregesterRecivers() {
        unregesterNotification();
    }

    @Override
    public void checkDriverStatus() {
        if(!UtilUser.getUserInstance().isChecked()){
            driverStatus.setImageResource(R.mipmap.power_off);
            driverStatusTextView.setText("You're offline");
            passengersActionsBtn.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else
        {
            driverStatus.setImageResource(R.mipmap.power_on);
            driverStatusTextView.setText("You're online");
            passengersActionsBtn.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.INVISIBLE);
        }

    }



    @Override
    public void onFailure(Throwable t) {
        UtilFunction.showToast(this,"onFailure : "+t.getMessage());

    }

    @Override
    public void responseCode200() {
        if(UtilUser.getUserInstance().isChecked()){
            UtilUser.getUserInstance().setChecked(false);
            driverStatus.setImageResource(R.mipmap.power_off);
            driverStatusTextView.setText("You're offline");
            passengersActionsBtn.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);

            ViewAnimator
                    .animate(bottomLayout)
                    .translationY(onlineOfflineLayout.getHeight() , 0)
                    .duration(1000)
                    .start();

        }
        else
        {
            UtilUser.getUserInstance().setChecked(true);
            driverStatus.setImageResource(R.mipmap.power_on);
            driverStatusTextView.setText("You're online");
            passengersActionsBtn.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);


            ViewAnimator
                    .animate(bottomLayout)
                    .translationY(0 ,onlineOfflineLayout.getHeight() )
                    .duration(1000)
                    .start();
        }
    }
}
