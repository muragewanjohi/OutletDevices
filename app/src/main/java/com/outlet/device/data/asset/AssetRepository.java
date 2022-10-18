package com.outlet.device.data.asset;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.outlet.device.data.AppDatabase;
import com.outlet.device.models.Asset;

import java.util.List;

public class AssetRepository {

    private AssetDao assetDao;
    private LiveData<List<Asset>> allAssets;

    public AssetRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        assetDao = database.assetDao();
        allAssets = assetDao.getAll();
    }

    public void insert(Asset asset) {
        new InsertAssetAsyncTask(assetDao).execute(asset);
    }

    public void update(Asset model) {
        new UpdateAssetAsyncTask(assetDao).execute(model);
    }

    public void findByAssetName(Asset model) {
        new FindByAssetNameAssyncTask(assetDao).execute(model);
    }

    public void delete(Asset asset) {
        new DeleteAssetAsyncTask(assetDao).execute(asset);
    }

    public void deleteAllAssets() {
        new DeleteAllAssetsAsyncTask(assetDao).execute();
    }

    public LiveData<List<Asset>> getAllAssets() {
        return allAssets;
    }

    private static class InsertAssetAsyncTask extends AsyncTask<Asset, Void, Void> {
        private AssetDao assetDao;

        private InsertAssetAsyncTask(AssetDao assetDao) {
            this.assetDao = assetDao;
        }

        @Override
        protected Void doInBackground(Asset... model) {
            assetDao.insert(model[0]);
            return null;
        }
    }

    private static class UpdateAssetAsyncTask extends AsyncTask<Asset, Void, Void> {
        private AssetDao assetDao;

        private UpdateAssetAsyncTask(AssetDao assetDao) {
            this.assetDao = assetDao;
        }

        @Override
        protected Void doInBackground(Asset... models) {
            assetDao.update(models[0]);
            return null;
        }
    }

    private static class FindByAssetNameAssyncTask extends AsyncTask<Asset, Void, Void> {
        private AssetDao assetDao;

        private FindByAssetNameAssyncTask(AssetDao assetDao) {
            this.assetDao = assetDao;
        }

        @Override
        protected Void doInBackground(Asset... models) {
            assetDao.
                    findByAssetName(models[0].getAssetName());
            return null;
        }
    }

    private static class DeleteAssetAsyncTask extends AsyncTask<Asset, Void, Void> {
        private AssetDao assetDao;

        private DeleteAssetAsyncTask(AssetDao assetDao) {
            this.assetDao = assetDao;
        }

        @Override
        protected Void doInBackground(Asset... assets) {
            assetDao.delete(assets[0]);
            return null;
        }
    }


    private static class DeleteAllAssetsAsyncTask extends AsyncTask<Void, Void, Void> {
        private AssetDao assetDao;
        private DeleteAllAssetsAsyncTask(AssetDao assetDao) {
            this.assetDao = assetDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            assetDao.deleteAllAssets();
            return null;
        }
    }
}
