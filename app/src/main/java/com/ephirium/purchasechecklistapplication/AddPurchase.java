package com.ephirium.purchasechecklistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ephirium.purchasechecklistapplication.databinding.ActivityAddPurchaseBinding;

// Activity, отвечающая за добавление в список покупок товар
public class AddPurchase extends AppCompatActivity {

    // TODO: сделать подгрузку с базы данных, возможность отсослать в список покупок новый товар,
    //  добавление категорий (опционально)

    private ActivityAddPurchaseBinding binding;

    public static Intent newIntent(Context context){
        return new Intent(context, AddPurchase.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPurchaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}