package com.outlet.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.outlet.device.camera.SelectPictureFragment;
import com.outlet.device.databinding.FragmentSelectDeviceBinding;
import com.outlet.device.models.Asset;

import java.util.ArrayList;
import java.util.List;

public class SelectDeviceFragment extends Fragment {
    FragmentSelectDeviceBinding binding;
    private SelectFragmentViewModel viewModel;

    List<String> devices = new ArrayList<String>();
    List<String> devices_id = new ArrayList<String>();
    SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectDeviceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sharedPref = getActivity().getSharedPreferences(
                getString(R.string.app_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        viewModel = new ViewModelProvider(getActivity()).get(SelectFragmentViewModel.class);

        viewModel.getAllAssets().observe(getActivity(), new Observer<List<Asset>>() {
            @Override
            public void onChanged(List<Asset> assets) {
                for (Asset asset: assets){
                    devices.add(asset.getTypeName());
                    devices_id.add(asset.getAssetId());
                }
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, devices);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.deviceSpinner.setAdapter(dataAdapter);

        binding.deviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                String asset_id = devices_id.get(position);

                editor.putString(getString(R.string.asset_id), asset_id);
                editor.putString(getString(R.string.device_name), item);
                editor.apply();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.conditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                editor.putString(getString(R.string.condition), item);
                editor.putString(getString(R.string.stateId), String.valueOf(position));
                editor.apply();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String barCodeValue = sharedPref.getString(getResources().getString(R.string.barcode), null);
        binding.qrCode.setText(barCodeValue);

        binding.btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(barCodeValue != null){

                    editor.putString(getString(R.string.barCode), binding.qrCode.getText().toString());
                    editor.putString(getString(R.string.qrCode), binding.qrCode.getText().toString());
                    editor.putString(getString(R.string.other_remarks), binding.otherRemarks.getText().toString());
                    editor.apply();
                    Log.d("barCode", barCodeValue);

                    Fragment fragment = new SelectPictureFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(((ViewGroup)(getView().getParent())).getId(), fragment)
                            .addToBackStack(null)
                            .commit();

                } else{

                    Toast.makeText(getContext(), "Scan QR / Barcode first ", Toast.LENGTH_LONG).show();
                }


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