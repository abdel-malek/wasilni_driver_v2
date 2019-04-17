package com.wasilni.wasilnidriverv2.mvp.presenter;

import android.util.Log;

import com.wasilni.wasilnidriverv2.mvp.model.User;
import com.wasilni.wasilnidriverv2.mvp.view.InterviewContract;
import com.wasilni.wasilnidriverv2.network.ApiServiceInterface;
import com.wasilni.wasilnidriverv2.network.Response;
import com.wasilni.wasilnidriverv2.network.RetorfitSingelton;
import com.wasilni.wasilnidriverv2.util.UtilUser;

import retrofit2.Call;

public class InterviewPresenterImp implements InterviewContract.InterviewPresenter {
    @Override
    public void sendToServer(String request) {
        ApiServiceInterface service = RetorfitSingelton.getRetrofitInstance().create(ApiServiceInterface.class);

        /** Call the method with parameter in the interface to get the notice data*/

        Log.d("CompleteData", "sendToServer: " + request);
        Call<com.wasilni.wasilnidriverv2.network.Response<Object>> call =
                service.Interview(UtilUser.getUserInstance().getAccessToken(), request);

        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Response<Object>> call, retrofit2.Response<Response<Object>> response) {
        Log.e("onResponse register",response.message()+" code :"+response.code());

        switch (response.code())
        {
            case 200 :
                break;
            case 422 :
                break;
            case 500 :
                break;
            case 400:
                break;
            case 401:
                break;
        }
    }

    @Override
    public void onFailure(Call<Response<Object>> call, Throwable t) {
        Log.e("onFailure",t.getMessage());

    }
}
