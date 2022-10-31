package com.outlet.device.view_assets;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.outlet.device.data.asset.AssetRepository;
import com.outlet.device.data.upload.UploadRepository;
import com.outlet.device.models.Asset;
import com.outlet.device.models.Upload;

import java.util.List;

public class ViewDevicesViewModel extends AndroidViewModel {

    private UploadRepository repository;
    private LiveData<List<Upload>> uploads;

    public ViewDevicesViewModel(@NonNull Application application) {
        super(application);
        repository = new UploadRepository(application);
        uploads = repository.getAllUploads();
    }

    LiveData<List<Upload>> getUploads() {
        return uploads;
    }

}