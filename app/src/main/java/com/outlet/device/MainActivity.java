package com.outlet.device;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.outlet.device.models.Asset;
import com.outlet.device.network.APIClient;
import com.outlet.device.network.APIInterface;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String KEY = "asset";
    private static final String TAG = "AssetLogs";
    APIInterface apiInterface;
    String outlet_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Call<List<Asset>> call = apiInterface.getAssets("https://www.btlke.com/se/index.php?/api/asset/" + outlet_id);
        call.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
                if(response.isSuccessful()) {
                    List<Asset> changesList = response.body();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        changesList.forEach(asset -> Log.d(TAG,asset.getAssetId()));
                    }

                    Fragment fragment = new SelectOutletFragment();
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
}