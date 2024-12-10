package com.android.rproject.data;

import com.android.rproject.data.models.Items;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("getItems")
    Call<List<Items>> getItems();

    @POST("addItem")
    Call<Items> addItem(@Body Items item);

    @PATCH("updateItem/{id}")
    Call<Items> updateItem(@Path("id") String id, @Body Items item);

    @DELETE("deleteItem/{id}")
    Call<Void> deleteItem(@Path("id") String id);
}
