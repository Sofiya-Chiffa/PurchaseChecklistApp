package com.ephirium.purchasechecklistapplication;

import android.Manifest;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ephirium.purchasechecklistapplication.databinding.FragmentPurchaseListBinding;
import com.ephirium.purchasechecklistapplication.databinding.ViewPurchaseBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// Фрагмент со списком покупок
public class PurchaseList extends Fragment {

    // TODO: сделать список покупок (class Purchase)

    private FragmentPurchaseListBinding binding;
    private List<SinglePurchase> purchaseList = new ArrayList<>();

    public static PurchaseList newInstance(){
        return new PurchaseList();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPurchaseListBinding.inflate(getLayoutInflater());



        purchaseList.add(new SinglePurchase("Творожок", 1, 100,
                new CategoryPurchase("Молочные Продукты")));

        for(SinglePurchase purchase : purchaseList){
            ViewPurchaseBinding purchaseBinding = ViewPurchaseBinding.inflate(getLayoutInflater());
            purchaseBinding.product.setText(purchase.getName());
            purchaseBinding.count.setText(String.valueOf(purchase.getCount()));
            purchaseBinding.price.setText(String.valueOf(purchase.getPrice()));
            purchaseBinding.category.setText(purchase.getCategoryPurchase().getName());
            binding.layoutScroll.addView(purchaseBinding.getRoot());
        }

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        //for(SinglePurchase purchase : purchaseList){
        //    ViewPurchaseBinding purchaseBinding = ViewPurchaseBinding.inflate(getLayoutInflater());
        //    purchaseBinding.product.setText(purchase.getName());
        //    purchaseBinding.count.setText(purchase.getCount());
        //    purchaseBinding.price.setText(String.valueOf(purchase.getPrice()));
        //    purchaseBinding.category.setText(purchase.getCategoryPurchase().getName());
        //    binding.scroll.addView(purchaseBinding.getRoot());
        //}
    }
}