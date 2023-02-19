package com.ephirium.purchasechecklistapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import com.ephirium.purchasechecklistapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // TODO: запросить Manage_external_storage

    private ActivityMainBinding binding;

    private ActivityResultLauncher<String> cameraPermissionLauncher;

    private ActivityResultLauncher<Intent> storagePermissionLauncher;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            onNavigationItemSelect(item);
            return true;
        });

        cameraPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if(result){
                        startActivity(CaptureImage.newIntent(this));
                    }
                }
        );

        storagePermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(Environment.isExternalStorageManager()){
                        Log.d("PurchaseCheckList", "Confirmed");
                    }
                    else{
                        finishAffinity();
                    }
                }
        );


        if(!Environment.isExternalStorageManager()){
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            storagePermissionLauncher.launch(intent);
        }

        startFragment(PurchaseList.newInstance());
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("NonConstantResourceId")
    private void onNavigationItemSelect(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigationCapture:
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
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