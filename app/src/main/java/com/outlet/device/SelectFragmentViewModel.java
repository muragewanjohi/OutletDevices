package com.outlet.device;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.outlet.device.data.asset.AssetRepository;
import com.outlet.device.models.Asset;

import java.util.List;

public class SelectFragmentViewModel extends AndroidViewModel
{
    private AssetRepository repository;
    private LiveData<List<Asset>> allAssets;
    // private MutableLiveData<List<Asset>> searchResults;

    public SelectFragmentViewModel (Application application) {
        super(application);
        repository = new AssetRepository(application);
        allAssets = repository.getAllAssets();
    }

    public void insertAsset(Asset asset) {
        repository.insert(asset);
    }

    LiveData<List<Asset>> getAllAssets() {
        return allAssets;
    }

    /*MutableLiveData<List<Product>> getSearchResults() {
    return searchResults;
}



    public void insertProduct(Product product) {
        repository.insertProduct(product);
    }

    public void findProduct(String name) {
        repository.findProduct(name);
    }

    public void deleteProduct(String name) {
        repository.deleteProduct(name);
    }*/
}
