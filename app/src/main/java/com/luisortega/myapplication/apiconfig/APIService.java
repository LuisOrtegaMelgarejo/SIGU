package com.luisortega.myapplication.apiconfig;

/**
 * Created by Luis on 19/05/2018.
 */
import com.luisortega.myapplication.models.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @FormUrlEncoded
    @POST("restLogin")
    Call<User> loginPost(@Field("user") String user, @Field("password") String password);
}
