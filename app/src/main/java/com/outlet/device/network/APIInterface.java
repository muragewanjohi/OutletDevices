package com.outlet.device.network;

import com.outlet.device.models.Asset;
import com.outlet.device.models.AssetId;
import com.outlet.device.models.AssetStates;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {

    @GET("index.php?/api/asset/{id}")
    Call<List<Asset>> getOutletAssets(@Path("id") String id);
   // void getOutletAssets(@Path("id") String id, Callback<Asset> cb);

    @GET
    Call<List<Asset>> getAssets(@Url String url);

    @GET("index.php?/api/assetstates")
    Call <AssetStates>getAssetsStates();

   /* @POST("/api/users")
    Call<User> createUser(@Body User user);

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}
