package com.ephirium.purchasechecklistapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ephirium.purchasechecklistapplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            onNavigationItemSelect(item);
            return true;
        });

        // binding.bottomNavigation.setItemIconTintList(null);

        startFragment(PurchaseList.newInstance());
    }

    @SuppressLint("NonConstantResourceId")
    private void onNavigationItemSelect(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigationCapture:
                startActivity(CaptureImage.newIntent(this));
                break;
            case R.id.navigationAdd:
                startActivity(AddPurchase.newIntent(this));
                break;
            case R.id.navigationEdit:
                startActivity(EditList.newIntent(this));
                break;
        }
    }

    private void startFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment, null)
                .commit();
    }
}