package com.ephirium.purchasechecklistapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ephirium.purchasechecklistapplication.databinding.FragmentPurchaseListBinding;

// Фрагмент со списком покупок
public class PurchaseList extends Fragment {

    // TODO: сделать список покупок (class Purchase)

    private FragmentPurchaseListBinding binding;

    public static PurchaseList newInstance(){
        return new PurchaseList();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPurchaseListBinding.inflate(getLayoutInflater());

        getChildFragmentManager().beginTransaction()
                .replace(R.id.purch, Purchase.newInstance(), null)
                .commit();

        return binding.getRoot();
    }
}