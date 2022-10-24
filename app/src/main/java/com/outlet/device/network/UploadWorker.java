package com.outlet.device.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.outlet.device.R;
import com.outlet.device.data.asset.AssetRepository;
import com.outlet.device.data.upload.UploadRepository;
import com.outlet.device.models.Asset;
import com.outlet.device.models.Upload;
import com.outlet.device.models.UploadResponse;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadWorker extends Worker {

    private UploadRepository uploadRepository;
    private LiveData<List<Upload>> uploads;

    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {

        getItemsFromDb();

       return Result.success();
    }

    private void getItemsFromDb(){

         /*uploadRepository = new UploadRepository();
        uploads = uploadRepository.getNotSyncedUploads();
        if (uploads.getValue() != null){
            
        }*/

    }

    /*void uploadToServer(Upload upload){

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        String userIdString = "1";
        String outletIdString = sharedPref.getString(getResources().getString(R.string.outlet_id), "8");
        String assetIdString = sharedPref.getString(getResources().getString(R.string.asset_id), "1");
        String remarkString = sharedPref.getString(getResources().getString(R.string.other_remarks), "0");
        String barCodeString = sharedPref.getString(getResources().getString(R.string.barCode), "041532");
        String qrCodeString = sharedPref.getString(getResources().getString(R.string.qrCode), "041532");
        String imageString = sharedPref.getString(getResources().getString(R.string.Image), "0");
        String latitudeString = latitude;
        String longitudeString = longitude;
        String datetimeString = datetime;
        Log.d("Upload_sharedPrefImage", "onRequest: " + imageString);
        String stateIdString = sharedPref.getString(getResources().getString(R.string.stateId), "0");

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

        APIInterface  apiInterface = APIClient.getClient().create(APIInterface.class);
        //  Call<UploadResponse> call = apiInterface.uploadAssets(requestBody);
        Call<UploadResponse> call = apiInterface.uploadAssets(fileBody, userId,outletId,assetId,latitude,longitude,datetime,qrCode,stateId,remark,barCode);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {

                Log.d("UploadResponse", "onResponse: response code: retrofit: " + response.code());
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {

            }
        });

    }*/
}
