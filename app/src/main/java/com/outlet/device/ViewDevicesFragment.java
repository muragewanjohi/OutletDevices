package com.outlet.device;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.outlet.device.data.asset.AssetRepository;
import com.outlet.device.data.upload.UploadRepository;
import com.outlet.device.databinding.FragmentSelectPictureBinding;
import com.outlet.device.databinding.FragmentViewDevicesBinding;
import com.outlet.device.models.Asset;
import com.outlet.device.models.Upload;
import com.outlet.device.models.UploadResponse;
import com.outlet.device.network.APIClient;
import com.outlet.device.network.APIInterface;
import com.outlet.device.view_assets.ListData;
import com.outlet.device.view_assets.MyListAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class ViewDevicesFragment extends Fragment implements LocationListener {

    private ViewDevicesViewModel mViewModel;
    FragmentViewDevicesBinding binding;
    private static final int REQUEST_LOCATION = 1;
    String latitude, longitude, datetime;

    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;

    public static ViewDevicesFragment newInstance() {
        return new ViewDevicesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ViewDevicesViewModel.class);

        binding = FragmentViewDevicesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        getLocation();

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String device_name = sharedPref.getString(getResources().getString(R.string.device_name), null);
        String condition = sharedPref.getString(getResources().getString(R.string.condition), null);
        String image_url = sharedPref.getString(getResources().getString(R.string.Image), null);
        Log.d("Image_url",image_url);
        String device_id = sharedPref.getString(getResources().getString(R.string.device_id), null);

        ListData[] myListData = new ListData[]{
                new ListData(device_name, device_id, condition, image_url)
        };

        binding.btnFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SelectDeviceFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)(getView().getParent())).getId(), fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.btnUploadToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadRepository repository = new UploadRepository(getActivity().getApplication());
                repository.insert(addItems());

                uploadToServer();

                Fragment fragment = new SelectDeviceFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)(getView().getParent())).getId(), fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        MyListAdapter adapter = new MyListAdapter(myListData);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        return view;
    }

    private Upload addItems() {

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        String userId = "1";
        String outletId = sharedPref.getString(getResources().getString(R.string.outlet_id), null);
        String assetId = sharedPref.getString(getResources().getString(R.string.asset_id), null);
        String remark = sharedPref.getString(getResources().getString(R.string.other_remarks), null);
        String barCode = sharedPref.getString(getResources().getString(R.string.barCode), null);
        String qrCode = sharedPref.getString(getResources().getString(R.string.qrCode), null);
        String image = sharedPref.getString(getResources().getString(R.string.Image), null);
        String stateId = sharedPref.getString(getResources().getString(R.string.stateId), null);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        datetime = dateFormat.format(new Date());

        ActivityCompat.requestPermissions( getActivity(),
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);



        Upload upload = new Upload();
        upload.setUserId(userId);
        upload.setAssetId(assetId);
        upload.setBarCode(barCode);
        upload.setQrCode(qrCode);
        upload.setStateId(stateId);
        upload.setDatetime(datetime);
        upload.setImage(image);
        upload.setOutletId(outletId);
        upload.setLatitude(latitude);
        upload.setLongitude(longitude);
        upload.setRemark(remark);

        return upload;
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager = (LocationManager)  getActivity().getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());

            }
            else{
                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            }
        }
    }

    void uploadToServer(){

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        String userId = "1";
        String outletId = sharedPref.getString(getResources().getString(R.string.outlet_id), "8");
        String assetId = sharedPref.getString(getResources().getString(R.string.asset_id), "1");
        String remark = sharedPref.getString(getResources().getString(R.string.other_remarks), "0");
        String barCode = sharedPref.getString(getResources().getString(R.string.barCode), "0");
        String qrCode = sharedPref.getString(getResources().getString(R.string.qrCode), "1");
        String image = sharedPref.getString(getResources().getString(R.string.Image), "0");
        String stateId = sharedPref.getString(getResources().getString(R.string.stateId), "0");

        File featured_image = new File(image);

        Bitmap bmp = BitmapFactory.decodeFile(featured_image.getAbsolutePath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, bos);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart(userId, userId)
                .addFormDataPart(outletId, outletId)
                .addFormDataPart(assetId, assetId)
                .addFormDataPart(remark, remark)
                .addFormDataPart(barCode, barCode)
                .addFormDataPart(qrCode, qrCode)
                .addFormDataPart(latitude,latitude)
                .addFormDataPart(longitude,longitude)
                .addFormDataPart(datetime,datetime)
                .addFormDataPart(stateId, stateId);

       // builder.addFormDataPart("Image", featured_image.getName(), RequestBody.create(MultipartBody.FORM, bos.toByteArray()));
        builder.addFormDataPart("Image", featured_image.getName(), RequestBody.create(MultipartBody.FORM, featured_image));

        RequestBody requestBody = builder.build();

        APIInterface  apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<UploadResponse> call = apiInterface.uploadAssets(requestBody);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {

                Log.d("UploadResponse", "onResponse: response code: retrofit: " + response.code());
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //remove location callback:
        locationManager.removeUpdates(this);

        //open the map:
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());

    }
}