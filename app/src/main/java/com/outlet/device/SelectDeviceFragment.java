package com.outlet.device;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.outlet.device.databinding.FragmentSelectDeviceBinding;
import com.outlet.device.databinding.FragmentSelectOutletBinding;

public class SelectDeviceFragment extends Fragment {
    FragmentSelectDeviceBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectDeviceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SelectPictureFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)(getView().getParent())).getId(), fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}