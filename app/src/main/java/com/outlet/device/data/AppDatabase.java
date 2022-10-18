package com.outlet.device.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.outlet.device.data.asset.AssetDao;
import com.outlet.device.data.upload.UploadDao;
import com.outlet.device.models.Asset;
import com.outlet.device.models.Upload;

@Database(entities = {Asset.class, Upload.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AssetDao assetDao();
    public abstract UploadDao uploadDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class,
                                    "premise_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
