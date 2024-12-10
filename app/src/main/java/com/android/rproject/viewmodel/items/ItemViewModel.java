package com.android.rproject.viewmodel.items;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.rproject.repository.ItemRepository;
import com.android.rproject.data.models.Items;

import java.util.List;

public class ItemViewModel extends ViewModel {
    private final ItemRepository repository;
    private LiveData<List<Items>> items;
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public ItemViewModel() {
        repository = new ItemRepository();
        items = repository.getItems();
    }

    public LiveData<List<Items>> getItems() {
        return items;
    }

    public void addItem(Items item) {
        repository.addItem(item, new ItemRepository.RepositoryCallback() {
            @Override
            public void onSuccess(Items addedItem) {
                fetchItems();
                toastMessage.setValue("Item added successfully");
            }

            @Override
            public void onFailure(String error) {
                toastMessage.setValue("Failed to add item: " + error);
            }
        });
    }

    public void updateItem(String id, Items item) {
        repository.updateItem(id, item, new retrofit2.Callback<Items>() {
            @Override
            public void onResponse(retrofit2.Call<Items> call, retrofit2.Response<Items> response) {
                if (response.isSuccessful()) {
                    fetchItems();
                    toastMessage.setValue("Item updated successfully");
                } else {
                    toastMessage.setValue("Failed to update item");

                }
            }
            @Override
            public void onFailure(retrofit2.Call<Items> call, Throwable t) {
                toastMessage.setValue("Failed to update item: " + t.getMessage());
            }
        });
    }

    public void deleteItem(String id) {
        repository.deleteItem(id, new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchItems();
                    toastMessage.setValue("Item deleted successfully");
                } else {
                    toastMessage.setValue("Failed to delete item");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                toastMessage.setValue("Failed to delete item: " + t.getMessage());
            }
        });
    }

    private void fetchItems() {
        repository.getItems();
    }
}
