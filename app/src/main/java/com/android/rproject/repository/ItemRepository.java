package com.android.rproject.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.rproject.data.ApiService;
import com.android.rproject.data.models.Items;
import com.android.rproject.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRepository {
    private final ApiService apiService;
    private MutableLiveData<List<Items>> itemsLiveData;

    public ItemRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        itemsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Items>> getItems() {
        apiService.getItems().enqueue(new Callback<List<Items>>() {
            @Override
            public void onResponse(Call<List<Items>> call, Response<List<Items>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    itemsLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Items>> call, Throwable t) {
            }
        });
        return itemsLiveData;
    }

    public void addItem(Items item, final RepositoryCallback callback) {
        Call<Items> call = apiService.addItem(item);

        call.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to add item");
                }
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void updateItem(String id, Items item, Callback<Items> callback) {
        apiService.updateItem(id, item).enqueue(callback);
    }

    public void deleteItem(String id, Callback<Void> callback) {
        apiService.deleteItem(id).enqueue(callback);
    }

    public interface RepositoryCallback {
        void onSuccess(Items addedItem);
        void onFailure(String error);
    }
}
