package com.outlet.device;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.outlet.device.models.Asset;
import com.outlet.device.network.APIClient;
import com.outlet.device.network.APIInterface;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String KEY = "asset";
    private static final String TAG = "AssetLogs";
    APIInterface apiInterface;
    String outlet_id;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            outlet_id = intent.getStringExtra(KEY);
        }else{
            outlet_id = "8";
        }

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while fetching data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        /**
         GET List of Assets
         **/
        if(outlet_id == null){
            outlet_id = "8";
        }


        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.outlet_id), outlet_id);
        editor.apply();

        ArrayList<String> assetIds = new ArrayList<String>();

        Call<List<Asset>> call = apiInterface.getAssets("https://www.btlke.com/se/index.php?/api/asset/" + outlet_id);
        call.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
                if(response.isSuccessful()) {
                    List<Asset> assetList = response.body();

                    assert assetList != null;
                    for (Asset asset: assetList){
                        mViewModel.insertAsset(asset);
                        assetIds.add(asset.getAssetId());

                        Log.d(TAG,asset.getAssetId());
                    }

                    saveArrayList(assetIds,getString(R.string.asset_ids));

                    Fragment fragment = new SelectDeviceFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_activity, fragment).commit();

                } else {
                    try {
                        Log.e(TAG,response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                progress.cancel();
            }

            @Override
            public void onFailure(Call<List<Asset>> call, Throwable t) {
                t.printStackTrace();
                progress.cancel();
            }
        });

    }

    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}