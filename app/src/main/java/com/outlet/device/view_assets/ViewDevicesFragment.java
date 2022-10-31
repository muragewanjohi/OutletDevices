package com.outlet.device.view_assets;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.outlet.device.R;
import com.outlet.device.SelectDeviceFragment;
import com.outlet.device.data.upload.UploadRepository;
import com.outlet.device.databinding.FragmentViewDevicesBinding;
import com.outlet.device.models.Upload;
import com.outlet.device.models.UploadResponse;
import com.outlet.device.network.APIClient;
import com.outlet.device.network.APIInterface;
import com.outlet.device.models.ListData;
import com.outlet.device.network.ConnectionReceiver;
import com.outlet.device.network.UploadWorker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDevicesFragment extends Fragment{

    private ViewDevicesViewModel mViewModel;
    FragmentViewDevicesBinding binding;
    private static final int REQUEST_LOCATION = 1;
    String latitude, longitude, datetime;

    public static ViewDevicesFragment newInstance() {
        return new ViewDevicesFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ViewDevicesViewModel.class);

        binding = FragmentViewDevicesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        final MyListAdapter adapter = new MyListAdapter(new MyListAdapter.WordDiff());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getUploads().observe(getViewLifecycleOwner(), devices -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(devices);
        });

        uploadToServer();




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

                uploadToServer();

                Fragment fragment = new SelectDeviceFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)(getView().getParent())).getId(), fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    public static boolean isNetworkConnected(Context  context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        // For 29 api or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
        } else return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void uploadToServer(){
        WorkManager mWorkManager =WorkManager.getInstance(getActivity().getApplication());

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) //checks whether device should have Network Connection
                .build();


            WorkRequest uploadWorkRequest =
                    new PeriodicWorkRequest.Builder(UploadWorker.class,1, TimeUnit.MINUTES)
                            .setConstraints(constraints)
                            .build();

            Log.d("Upload_worker", "Started " );

        mWorkManager.enqueue(uploadWorkRequest);

    }

}