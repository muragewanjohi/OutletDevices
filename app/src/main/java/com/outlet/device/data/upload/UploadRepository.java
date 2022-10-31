package com.outlet.device.data.upload;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.outlet.device.data.AppDatabase;
import com.outlet.device.data.asset.AssetDao;
import com.outlet.device.data.asset.AssetRepository;
import com.outlet.device.models.Asset;
import com.outlet.device.models.Upload;

import java.util.List;

public class UploadRepository {

    private UploadDao uploadDao;
    private LiveData<List<Upload>> allUploads;

    public UploadRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        uploadDao = database.uploadDao();
        allUploads = uploadDao.getAll();
    }

    public void insert(Upload upload) {
        new UploadRepository.InsertUploadAsyncTask(uploadDao).execute(upload);
    }

    public void update(Upload model) {
        Log.d("UploadUpdate ", "Upload Sync: " + model.getSynced().toString());
        new UploadRepository.UpdateUploadAsyncTask(uploadDao).execute(model);
    }

    public void findByAssetName(Upload model) {
        new UploadRepository.FindByAssetIdAsyncTask(uploadDao).execute(model);
    }

    public void delete(Upload upload) {
        new UploadRepository.DeleteUploadAsyncTask(uploadDao).execute(upload);
    }


    public LiveData<List<Upload>> getAllUploads() {
        return allUploads;
    }

   /* public List<Upload> getNotSyncedUploads() {
        return uploadDao.getNotSynced();
    }*/

    public List<Upload> getItemsToSync() {
        Log.d("uploadToSync", "Get items to Sync");
        return uploadDao.getItemsToSync();
    }

    private static class InsertUploadAsyncTask extends AsyncTask<Upload, Void, Void> {
        private UploadDao uploadDao;

        private InsertUploadAsyncTask(UploadDao uploadDao) {
            this.uploadDao = uploadDao;
        }

        @Override
        protected Void doInBackground(Upload... model) {
            uploadDao.insert(model[0]);
            return null;
        }
    }

    /*private static class UpdateUploadAsyncTask extends AsyncTask<Upload, Void, Void> {
        private UploadDao uploadDao;

        private UpdateUploadAsyncTask(UploadDao uploadDao) {
            this.uploadDao = uploadDao;
        }

        @Override
        protected Void doInBackground(Upload... models) {
            uploadDao.update(models[0]);
            return null;
        }
    }*/

    private static class UpdateUploadAsyncTask extends AsyncTask<Upload, Void, Void> {
        private UploadDao uploadDao;

        private UpdateUploadAsyncTask(UploadDao uploadDao) {
            this.uploadDao = uploadDao;
        }

        @Override
        protected Void doInBackground(Upload... models) {
            uploadDao.updateUploadByTime(models[0].getDatetime());
            return null;
        }
    }

    private static class FindByAssetIdAsyncTask extends AsyncTask<Upload, Void, Void> {
        private UploadDao uploadDao;

        private FindByAssetIdAsyncTask(UploadDao uploadDao) {
            this.uploadDao = uploadDao;
        }

        @Override
        protected Void doInBackground(Upload... models) {
            uploadDao.
                    findByAssetId(models[0].getAssetId());
            return null;
        }
    }

    private static class DeleteUploadAsyncTask extends AsyncTask<Upload, Void, Void> {
        private UploadDao uploadDao;

        private DeleteUploadAsyncTask(UploadDao uploadDao) {
            this.uploadDao = uploadDao;
        }

        @Override
        protected Void doInBackground(Upload... uploads) {
            uploadDao.delete(uploads[0]);
            return null;
        }
    }

}
