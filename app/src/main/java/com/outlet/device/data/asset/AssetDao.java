package com.outlet.device.data.asset;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.outlet.device.models.Asset;

import java.util.List;

@Dao
public interface AssetDao {
    @Query("SELECT * FROM asset")
    LiveData<List<Asset>> getAll();

    @Query("SELECT * FROM asset WHERE assetId LIKE :id LIMIT 1")
    Asset findByAssetId(int id);

    @Query("SELECT * FROM asset WHERE assetName LIKE :assetName LIMIT 1")
    Asset findByAssetName(String assetName);

    @Insert
    void insert(Asset asset);

    @Update
    void update(Asset asset);

    @Delete
    void delete(Asset asset);

    @Query("DELETE FROM asset")
    void deleteAllAssets();
}