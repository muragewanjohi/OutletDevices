package com.outlet.device;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String barCodeValue = sharedPref.getString(getResources().getString(R.string.barcode), null);

        if(barCodeValue != null){
            binding.qrCode.setText(barCodeValue);
            Log.d("barCode", barCodeValue);
        }

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
        binding.btnScanQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ScanBarCodeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)(getView().getParent())).getId(), fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });



        return view;
    }
}