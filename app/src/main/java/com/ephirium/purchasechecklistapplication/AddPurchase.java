package com.ephirium.purchasechecklistapplication;

import static com.ephirium.purchasechecklistapplication.DatabaseHelper.COLUMN_COST;
import static com.ephirium.purchasechecklistapplication.DatabaseHelper.COLUMN_COUNT;
import static com.ephirium.purchasechecklistapplication.DatabaseHelper.COLUMN_NAME;
import static com.ephirium.purchasechecklistapplication.DatabaseHelper.COLUMN_STATUS;
import static com.ephirium.purchasechecklistapplication.DatabaseHelper.TABLE_PRODUCTS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ephirium.purchasechecklistapplication.databinding.ActivityAddPurchaseBinding;

import android.content.ContentValues;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;

import android.widget.Button;
import android.widget.EditText;
import android.view.View;

// Activity, отвечающая за добавление в список покупок товар
public class AddPurchase extends AppCompatActivity {

    // TODO: сделать подгрузку с базы данных, возможность отсослать в список покупок новый товар,
    //  добавление категорий (опционально)

    DatabaseHelper sqlHelper;

    private ActivityAddPurchaseBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, AddPurchase.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPurchaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addPr.setOnClickListener(this::addProduct);

        sqlHelper = DatabaseHelper.getInstance(this);
        sqlHelper.open();
    }

    public void addProduct(View v) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, binding.nameedittext.getText().toString());
        contentValues.put(COLUMN_COUNT, binding.countedittext.getText().toString());
        contentValues.put(COLUMN_COST, 0);
        contentValues.put(COLUMN_STATUS, false);
        sqlHelper.insert(TABLE_PRODUCTS, contentValues);
        finish();
    }

    public void deleteProduct(String name) {
        sqlHelper.delete(TABLE_PRODUCTS, COLUMN_NAME + "=" + name, null);
        finish();
    }

    public void changeProductStatus(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, true);
        sqlHelper.update(TABLE_PRODUCTS, contentValues, COLUMN_NAME + "=" + name, null);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqlHelper.close();
    }
}
