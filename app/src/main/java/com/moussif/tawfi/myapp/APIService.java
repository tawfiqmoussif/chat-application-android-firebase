package com.moussif.tawfi.myapp;

import com.moussif.tawfi.myapp.Notification.MyResponse;
import com.moussif.tawfi.myapp.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAPTX3hZ8:APA91bExRHjcoTDXbvturvf40rGzpvzmE76uRx5A1v8SkAvEfHzFGOMBb0LO32aZV0Yx4WAjZzGVdaoe6lFR-BEalAmWA3nTV83dCfgOzplK6SJjVziXPldgkSuRCw8NCYxMe_QvR6hT"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
