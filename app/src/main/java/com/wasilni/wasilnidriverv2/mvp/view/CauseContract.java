package com.wasilni.wasilnidriverv2.mvp.view;

import com.wasilni.wasilnidriverv2.mvp.model.Cause;
import com.wasilni.wasilnidriverv2.mvp.model.pojo.PaginationAPI;
import com.wasilni.wasilnidriverv2.network.ServerPresenter;

import java.util.List;

public interface CauseContract {
    public interface CausePresenter extends ServerPresenter<PaginationAPI<List<Cause>>> {

    }
}
