package com.outlet.device;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.outlet.device.data.asset.AssetRepository;
import com.outlet.device.models.Asset;

import java.util.List;

public class MainViewModel extends AndroidViewModel
{
    private AssetRepository repository;
    private LiveData<List<Asset>> allAssets;
   // private MutableLiveData<List<Asset>> searchResults;

    public MainViewModel (Application application) {
        super(application);
        repository = new AssetRepository(application);
        allAssets = repository.getAllAssets();
        //searchResults = repository.getSearchResults();
    }

    public void insertAsset(Asset asset) {
        repository.insert(asset);
    }

    /*MutableLiveData<List<Product>> getSearchResults() {
    return searchResults;
}

    LiveData<List<Product>> getAllProducts() {
        return allProducts;
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
