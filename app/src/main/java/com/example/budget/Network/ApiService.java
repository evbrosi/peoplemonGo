package com.example.budget.Network;

import com.example.budget.Models.User;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by eaglebrosi on 10/31/16.
 */

public interface ApiService {
    // on the website we are using, their get is "/post/"
    // so we call get post- the {id} is given with the @Path("id") it's the same, and the integer id.

    @POST("api/Account/Register/")
    // response body in swagger says no content- so it's Void.
    Call<Void> register(@Body User newUser);

    @FormUrlEncoded
    @POST("token")
    Call<User> login(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grantType);

    @POST("v1/User/CheckIn")
    Call<Void> CheckIn(@Body LatLng loc);

    @FormUrlEncoded
    @POST("api/Account/UserInfo")
    Call <User> userInfo(@Body User changeInfo);

    @GET("v1/User/Nearby")
    Call <User[]> nearby(@Query ("radiusInMeters") Integer radius);

    @FormUrlEncoded
    // when passing multiple fields we need a @formurlencoded
    @POST("v1/User/Catch")
    Call <Void> catchemAll(@Field("CaughtUserId") String userId, @Field("RadiusInMeters") Integer radius);

    @GET("v1/User/Caught")
    Call <User[]> caught();

   // @POST("api/Account/UserInfo")
  //  Call<User> edit(@Field("fullName")) String fullName, @Field(avatarBase64())
}
