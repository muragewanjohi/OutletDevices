package com.outlet.device.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.outlet.device.data.asset.AssetDao;
import com.outlet.device.models.Asset;

@Database(entities = {Asset.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AssetDao assetDao();

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
