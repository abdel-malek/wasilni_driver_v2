package com.wasilni.wasilnidriverv2.mvp.view;

import com.wasilni.wasilnidriverv2.mvp.model.User;
import com.wasilni.wasilnidriverv2.network.ServerPresenter;

public interface UserData {
    public interface GetUserData extends ServerPresenter<Object , User> {

    }
}
