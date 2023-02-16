package com.ephirium.purchasechecklistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import com.ephirium.purchasechecklistapplication.databinding.ActivityCaptureImageBinding;

// Activity, отвечающая за захват изображения и перевод текста
// (можно сделать отдельные Activity/Fragment)
public class CaptureImage extends AppCompatActivity {

    // TODO: добавить возможность снятия фотки и распознавание текста

    private ActivityCaptureImageBinding binding;

    public static Intent newIntent(Context context){
        return new Intent(context, CaptureImage.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaptureImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startActivity(intent);
    }
}