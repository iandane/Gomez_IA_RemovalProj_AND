package com.android.rproject.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.rproject.R;
import com.android.rproject.ui.items.ItemFragment;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ItemFragment())
                .commit();
    }
}
