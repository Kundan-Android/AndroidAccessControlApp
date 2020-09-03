package com.widas.demo_ac.AccessControl.service;

import com.widas.demo_ac.AccessControl.Model.ActionPerformStatus;
import com.widas.demo_ac.AccessControl.Model.ActionResponse;
import com.widas.demo_ac.AccessControl.Model.DeviceStatus;
import com.widas.demo_ac.MainScreen.model.AssetsListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApi {

    @POST("/access-control-srv/devices/iot/control/{device_id}/{ph_id}/status")
    Call<DeviceStatus> getDeviceStatus(@Path("device_id") String device_id, @Path("ph_id") String ph_id);

    @POST("/access-control-srv/devices/iot/control/{device_id}/{ph_id}/action")
    Call<ActionPerformStatus> performAction(@Path("device_id") String device_id, @Path("ph_id") String ph_id,@Body ActionResponse action);

//    @GET("/access-control-srv/physicalresource/list/lite")
//    Call<AssetsListModel> assetsList();
//
    @GET("/access-control-srv/physicalresource/list/lite/by/user")
    Call<AssetsListModel> assetsList();

}
