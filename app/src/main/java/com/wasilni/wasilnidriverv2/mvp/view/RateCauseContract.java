package com.wasilni.wasilnidriverv2.mvp.view;

import com.wasilni.wasilnidriverv2.mvp.model.Booking;
import com.wasilni.wasilnidriverv2.mvp.model.BookingCause;
import com.wasilni.wasilnidriverv2.mvp.model.Cause;
import com.wasilni.wasilnidriverv2.mvp.model.pojo.PaginationAPI;
import com.wasilni.wasilnidriverv2.network.Response;
import com.wasilni.wasilnidriverv2.network.ServerPresenter;

import java.util.List;

public interface RateCauseContract {
    public interface CausePresenter extends ServerPresenter<Cause,PaginationAPI<List<Cause>>> {

    }
    public interface RatePresenter extends ServerPresenter<BookingCause,BookingCause> {

    }
}