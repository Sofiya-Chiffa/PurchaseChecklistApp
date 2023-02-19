package com.ephirium.purchasechecklistapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.camera.camera2.interop.Camera2CameraInfo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.interop.Camera2Interop;
import androidx.camera.camera2.interop.CaptureRequestOptions;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;
import androidx.camera.core.internal.CameraUseCaseAdapter;
import androidx.core.content.FileProvider;

import com.ephirium.purchasechecklistapplication.databinding.ActivityCaptureImageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

// Activity, отвечающая за захват изображения и перевод текста
// (можно сделать отдельные Activity/Fragment)
public class CaptureImage extends AppCompatActivity {

    // TODO: добавить возможность снятия фотки и распознавание текста

    private ActivityCaptureImageBinding binding;
    private Uri fileImageUri;

    private final LanguageIdentifier languageIdentifier = LanguageIdentification.getClient();

    private final TextRecognizer textRecognizer =
            TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    private ActivityResultLauncher<Intent> launcher;

    public static Intent newIntent(Context context){
        return new Intent(context, CaptureImage.class);
    }

    private int getRotationCompensation(String cameraId, Activity activity, boolean isFrontFacing)
            throws CameraAccessException {
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        CameraManager cameraManager = (CameraManager) activity.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);

        if (isFrontFacing) {
            rotationCompensation = (sensorOrientation + rotationCompensation) % 360;
        } else { // back-facing
            rotationCompensation = (sensorOrientation - rotationCompensation + 360) % 360;
        }
        return rotationCompensation;
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
                        analyzeImage();
                    }
                }
        );

        setContentView(binding.getRoot());
        startCameraForResult();
    }

    private void analyzeImage(){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                    fileImageUri);
            InputImage image;
            try{
                Bitmap bmi = MediaStore.Images.Media.getBitmap(getContentResolver(),
                        fileImageUri);
                image = InputImage.fromBitmap(bmi,
                        getRotationCompensation(String.valueOf(Camera.CameraInfo.CAMERA_FACING_BACK),
                        this, false));
                Task<Text> result = textRecognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text text) {
                                languageIdentifier.identifyLanguage(text.getText());

                                String[] res = getSuccessString(text);
                                connectToDataBase(res);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            } catch (IOException e){
                Log.d("PurchaseCheckList", e.toString());
            } catch (CameraAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String[] getSuccessString(Text result){
        String resultText = result.getText();
        String[] results = new String[result.getTextBlocks().size()];
        int i = 0;
        for (Text.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            results[i] = blockText;
            i++;
        }
        return results;
    }


    // ToDO: дописать подключение к датабазе и поиск по именам
    private Purchase[] connectToDataBase(String[] names){
        for(String name : names){
            Log.d("PurchaseApplication", name);
        }
        return null;
    }

    private void startCameraForResult() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileImageUri);
        launcher.launch(intent);
    }
}