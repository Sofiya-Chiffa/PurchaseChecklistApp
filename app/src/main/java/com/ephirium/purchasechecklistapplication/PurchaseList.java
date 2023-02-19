package com.ephirium.purchasechecklistapplication;

import android.Manifest;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ephirium.purchasechecklistapplication.databinding.FragmentPurchaseListBinding;

import java.util.ArrayList;
import java.util.List;

// Фрагмент со списком покупок
public class PurchaseList extends Fragment {

    // TODO: сделать список покупок (class Purchase)

    private FragmentPurchaseListBinding binding;

    public static PurchaseList newInstance(){
        return new PurchaseList();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPurchaseListBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }
}