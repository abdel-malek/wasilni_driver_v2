package com.wasilni.wasilnidriverv2.mvp.view;

import com.wasilni.wasilnidriverv2.network.ServerPresenter;

public interface InterviewContract {
    public interface InterviewPresenter extends ServerPresenter<String , Object> {

    }
    public interface InterviewView  {
        public void responseCode200();
        public void responseCode422();
        public void responseCode500();
        public void responseCode400();
        public void responseCode401();
    }
}