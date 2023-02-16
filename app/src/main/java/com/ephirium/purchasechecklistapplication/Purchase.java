package com.ephirium.purchasechecklistapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ephirium.purchasechecklistapplication.databinding.FragmentPurchaseBinding;

// Фоагмент-элемент списка покупок
public class Purchase extends Fragment {

    private FragmentPurchaseBinding binding;

    public static Purchase newInstance(){
        return new Purchase();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPurchaseBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}