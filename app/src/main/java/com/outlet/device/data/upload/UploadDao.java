package com.outlet.device.data.upload;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.outlet.device.models.Upload;

import java.util.List;

@Dao
public interface UploadDao {

    @Query("SELECT * FROM upload")
    LiveData<List<Upload>> getAll();

    @Query("SELECT * FROM upload WHERE assetId LIKE :id LIMIT 1")
    Upload findByAssetId(String id);

/*    @Query("SELECT * FROM upload WHERE synced = 0")
    List<Upload> getNotSynced();*/

    @Query("SELECT * FROM upload WHERE synced = 0")
    List<Upload>getItemsToSync();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Upload upload);

    @Update
    void update(Upload upload);

    @Query("UPDATE Upload SET synced = 1 WHERE datetime =:time")
    void updateUploadByTime(String time);

    @Delete
    void delete(Upload upload);

}
