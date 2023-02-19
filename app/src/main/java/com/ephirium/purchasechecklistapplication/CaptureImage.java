package com.ephirium.purchasechecklistapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.ephirium.purchasechecklistapplication.databinding.ActivityCaptureImageBinding;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

// Activity, отвечающая за захват изображения и перевод текста
// (можно сделать отдельные Activity/Fragment)
public class CaptureImage extends AppCompatActivity {

    // TODO: добавить возможность снятия фотки и распознавание текста

    private ActivityCaptureImageBinding binding;
    private Uri fileImageUri;

    private ActivityResultLauncher<Intent> launcher;

    public static Intent newIntent(Context context){
        return new Intent(context, CaptureImage.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaptureImageBinding.inflate(getLayoutInflater());
        fileImageUri = FileProvider.getUriForFile(this,
                getApplicationContext().getPackageName() + ".provider",
                new File(getExternalCacheDir(), UUID.randomUUID().toString() + ".jpg"));
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK){
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                                    fileImageUri);
                            binding.img.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );

        setContentView(binding.getRoot());
        startCameraForResult();
    }

    private void startCameraForResult() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileImageUri);
        launcher.launch(intent);
    }
}