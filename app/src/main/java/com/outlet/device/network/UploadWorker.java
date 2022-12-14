package com.outlet.device.network;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.outlet.device.data.AppDatabase;
import com.outlet.device.data.upload.UploadDao;
import com.outlet.device.data.upload.UploadRepository;
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
    private List<Upload> uploads;
    UploadDao uploadDao;

    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        uploadDao =  AppDatabase.getDatabase(context).uploadDao();

       // uploadRepository = new UploadRepository(get());
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("Upload_worker", "Constructor " );

        uploads = uploadDao.getItemsToSync();

        for (Upload upload: uploads){
            Log.d("Upload_toUpload", "onRequest: " + upload.getDatetime());
            uploadToServer(upload);
        }

        return Result.success();
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

        APIInterface  apiInterface = APIClient.getClient().create(APIInterface.class);
        //  Call<UploadResponse> call = apiInterface.uploadAssets(requestBody);
        Call<UploadResponse> call = apiInterface.uploadAssets(fileBody, userId,outletId,assetId,latitude,longitude,datetime,qrCode,stateId,remark,barCode);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {

               // toUpload.setSynced(true);
                uploadRepository.update(toUpload);

                Log.d("UploadResponse", "onResponse: response code: retrofit: " + response.code());
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {

            }
        });

    }
}
