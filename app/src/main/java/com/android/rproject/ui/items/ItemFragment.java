package com.android.rproject.ui.items;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.rproject.R;
import com.android.rproject.data.models.Items;
import com.android.rproject.viewmodel.items.ItemViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemFragment extends Fragment {

    private ItemViewModel viewModel;
    private ListView listView;
    private SimpleAdapter adapter;
    private List<HashMap<String, String>> itemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        listView = view.findViewById(R.id.listView);

        String[] from = {"itemId", "itemName", "itemDescription", "itemPrice"};
        int[] to = {R.id.itemId ,R.id.itemName, R.id.itemDescription, R.id.itemPrice};

        adapter = new SimpleAdapter(getContext(), itemList, R.layout.list_item_layout, from, to);
        listView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getItems().observe(getViewLifecycleOwner(), items -> {
            itemList.clear();
            for (Items item : items) {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("itemName", item.getItemName());
                map.put("itemDescription", item.getItemDescription());
                map.put("itemPrice", String.valueOf(item.getItemPrice()));
                itemList.add(map);
            }
            adapter.notifyDataSetChanged();
        });

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.addButton).setOnClickListener(v -> showAddItemDialog());

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            HashMap<String, String> selectedItem = itemList.get(position);
            String itemId = selectedItem.get("id");

            View updateButton = view1.findViewById(R.id.updateBtn);
            View deleteButton = view1.findViewById(R.id.deleteBtn);

            updateButton.setOnClickListener(v -> showUpdateItemDialog(itemId, selectedItem));
            deleteButton.setOnClickListener(v -> deleteItem(itemId));
        });

        return view;
    }

    private void showAddItemDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_item, null);

        EditText itemNameEditText = dialogView.findViewById(R.id.itemNameEditText);
        EditText itemDescriptionEditText = dialogView.findViewById(R.id.itemDescriptionEditText);
        EditText itemPriceEditText = dialogView.findViewById(R.id.itemPriceEditText);

        new android.app.AlertDialog.Builder(requireContext())
                .setTitle("Add Item")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String itemName = itemNameEditText.getText().toString().trim();
                    String itemDescription = itemDescriptionEditText.getText().toString().trim();
                    String itemPriceString = itemPriceEditText.getText().toString().trim();

                    if (TextUtils.isEmpty(itemName) || TextUtils.isEmpty(itemDescription) || TextUtils.isEmpty(itemPriceString)) {
                        Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            Double itemPrice = Double.parseDouble(itemPriceString);
                            Items newItem = new Items(null, itemName, itemDescription, itemPrice);
                            viewModel.addItem(newItem);
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Invalid price", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showUpdateItemDialog(String itemId, HashMap<String, String> itemData) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_item, null);

        EditText itemNameEditText = dialogView.findViewById(R.id.itemNameEditText);
        EditText itemDescriptionEditText = dialogView.findViewById(R.id.itemDescriptionEditText);
        EditText itemPriceEditText = dialogView.findViewById(R.id.itemPriceEditText);

        itemNameEditText.setText(itemData.get("itemName"));
        itemDescriptionEditText.setText(itemData.get("itemDescription"));
        itemPriceEditText.setText(itemData.get("itemPrice"));

        new android.app.AlertDialog.Builder(requireContext())
                .setTitle("Update Item")
                .setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String itemName = itemNameEditText.getText().toString().trim();
                    String itemDescription = itemDescriptionEditText.getText().toString().trim();
                    String itemPriceString = itemPriceEditText.getText().toString().trim();

                    if (TextUtils.isEmpty(itemName) || TextUtils.isEmpty(itemDescription) || TextUtils.isEmpty(itemPriceString)) {
                        Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            Double itemPrice = Double.parseDouble(itemPriceString);
                            Items updatedItem = new Items(itemId, itemName, itemDescription, itemPrice);
                            viewModel.updateItem(itemId, updatedItem);
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Invalid price", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteItem(String itemId) {
        new android.app.AlertDialog.Builder(requireContext())
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteItem(itemId);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
