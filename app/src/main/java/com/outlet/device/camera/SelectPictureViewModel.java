package com.outlet.device.camera;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.outlet.device.R;
import com.outlet.device.data.upload.UploadRepository;
import com.outlet.device.models.Asset;
import com.outlet.device.models.Upload;
import com.outlet.device.models.UploadResponse;
import com.outlet.device.network.APIClient;
import com.outlet.device.network.APIInterface;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectPictureViewModel extends AndroidViewModel {

    private UploadRepository repository;
    private LiveData<List<Upload>> uploads;

    public SelectPictureViewModel(@NonNull Application application) {
        super(application);
        repository = new UploadRepository(application);
    }

    public void insertUpload() {

        SharedPreferences sharedPref = getApplication().getSharedPreferences(getApplication().getString(R.string.app_preferences),Context.MODE_PRIVATE);

        String userId = "1";
        String outletId = sharedPref.getString(getApplication().getResources().getString(R.string.outlet_id), "8");
        String assetId = sharedPref.getString(getApplication().getResources().getString(R.string.asset_id), "1");
        String remark = sharedPref.getString(getApplication().getResources().getString(R.string.other_remarks), "0");
        String barCode = sharedPref.getString(getApplication().getResources().getString(R.string.barCode), "041532");
        String qrCode = sharedPref.getString(getApplication().getResources().getString(R.string.qrCode), "041532");
        String image = sharedPref.getString(getApplication().getResources().getString(R.string.Image), null);
        String stateId = sharedPref.getString(getApplication().getResources().getString(R.string.stateId), null);
        String latitude = sharedPref.getString(getApplication().getResources().getString(R.string.latitude), null);
        String longitude = sharedPref.getString(getApplication().getResources().getString(R.string.longitude), null);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = dateFormat.format(new Date());

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

        repository.insert(upload);

        if(isNetworkAvailable()){
            uploadToServer(upload);
        }

    }

    void uploadToServer(Upload upload){

        Upload toUpload = upload;

        String userIdString = "1";
        String outletIdString = toUpload.getOutletId();
        String assetIdString = toUpload.getAssetId();
        String remarkString = toUpload.getRemark();
        String barCodeString = toUpload.getBarCode();
        String qrCodeString = toUpload.getQrCode();
        String imageString = toUpload.getImage();
        String latitudeString = toUpload.getLatitude();
        String longitudeString = toUpload.getLongitude();
        String datetimeString = toUpload.getDatetime();
        Log.d("Upload_sharedPrefImage", "onRequest: " + imageString);
        String stateIdString = toUpload.getStateId();

        File featured_image = new File(imageString);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), featured_image);

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"),userIdString);
        RequestBody outletId = RequestBody.create(MediaType.parse("text/plain"),outletIdString);
        RequestBody assetId = RequestBody.create(MediaType.parse("text/plain"),assetIdString);
        RequestBody remark = RequestBody.create(MediaType.parse("text/plain"),remarkString);
        RequestBody barCode = RequestBody.create(MediaType.parse("text/plain"),barCodeString);
        RequestBody qrCode = RequestBody.create(MediaType.parse("text/plain"),qrCodeString);
        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"),latitudeString);
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"),longitudeString);
        RequestBody datetime = RequestBody.create(MediaType.parse("text/plain"),datetimeString);
        RequestBody stateId = RequestBody.create(MediaType.parse("text/plain"),stateIdString);

        // builder.addFormDataPart("Image", featured_image.getName(), RequestBody.create(MultipartBody.FORM, bos.toByteArray()));
        // builder.addFormDataPart("Image", featured_image.getName(), requestFile);

        Log.d("Upload_userId", "onRequest: " + userIdString);
        Log.d("Upload_outletId", "onRequest: " + outletIdString);
        Log.d("Upload_assetId", "onRequest: " + assetIdString);
        Log.d("Upload_remark", "onRequest: " + remarkString);
        Log.d("Upload_barCode", "onRequest: " + barCodeString);
        Log.d("Upload_qrCode", "onRequest: " + qrCodeString);
        Log.d("Upload_latitude", "onRequest: " + latitudeString);
        Log.d("Upload_longitude", "onRequest: " + longitudeString);
        Log.d("Upload_datetime", "onRequest: " + datetimeString);
        Log.d("Upload_stateId", "onRequest: " + stateIdString);
        Log.d("Upload_Image", "onRequest: " + featured_image.getName());

        // RequestBody requestBody = builder.build();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        //  Call<UploadResponse> call = apiInterface.uploadAssets(requestBody);
        Call<UploadResponse> call = apiInterface.uploadAssets(fileBody, userId,outletId,assetId,latitude,longitude,datetime,qrCode,stateId,remark,barCode);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {

               // toUpload.setSynced(true);
                repository.update(toUpload);

                Log.d("UploadResponse", "onResponse: response code: retrofit: " + response.code());
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {

            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
