package com.outlet.device;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.outlet.device.databinding.FragmentSelectOutletBinding;

import java.util.Objects;

public class SelectOutletFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectOutletBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSelectOutletBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SelectDeviceFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)(getView().getParent())).getId(), fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}