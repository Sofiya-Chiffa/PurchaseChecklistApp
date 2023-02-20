package com.ephirium.purchasechecklistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import com.ephirium.purchasechecklistapplication.databinding.ActivityEditListBinding;

// Activity (лучше переделать в фрагмент), отвечающая за изменение списка покупок
// (удаление, перемещение, сокрытие)
public class EditList extends AppCompatActivity {

    // TODO: добавить возможность редактирования списка

    private ActivityEditListBinding binding;

    public static Intent newIntent(Context context){
        return new Intent(context, EditList.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}