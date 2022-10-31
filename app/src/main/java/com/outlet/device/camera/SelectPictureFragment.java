package com.outlet.device.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.outlet.device.BuildConfig;
import com.outlet.device.R;
import com.outlet.device.view_assets.ViewDevicesFragment;
import com.outlet.device.databinding.FragmentSelectPictureBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectPictureFragment extends Fragment implements LocationListener {

    SelectPictureViewModel selectPictureViewModel;
    private Uri imageUri;
    private static final String TAG = "API123";
    private static final String SAVED_INSTANCE_URI = "uri";

    FragmentSelectPictureBinding binding;
    int request_times = 0;
    String device_id, outlet_id;

    private static final int REQUEST_LOCATION = 1;
    String latitude, longitude, datetime;

    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;
    SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectPictureBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        selectPictureViewModel = new ViewModelProvider(this).get(SelectPictureViewModel.class);

        sharedPref = getActivity().getSharedPreferences(
                getString(R.string.app_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        getLocation();

        device_id = sharedPref.getString(getResources().getString(R.string.device_id), null);
        outlet_id = sharedPref.getString(getResources().getString(R.string.outlet_id), null);

        if (savedInstanceState != null) {
            if (imageUri != null) {
                imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
                binding.cameraPreview.setImageURI(imageUri);

                String Image = getImageFilePath(getContext(), imageUri);

                editor.putString(getString(R.string.Image), String.valueOf(imageUri));
                editor.apply();

                Log.d("Image_path", Image);
            }
        }

        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        //requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        //requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        //Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED

        binding.btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.cameraPreview.setImageDrawable(null);

                if (request_times == 2) {
                    takeBarcodePicture();
                }

            }
        });

        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPictureViewModel.insertUpload();

                Fragment fragment = new ViewDevicesFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup) (getView().getParent())).getId(), fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        request_times += 1;
                        Log.d("request_times", String.valueOf(request_times));
                    } else {
                        Toast.makeText(getContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );


    ActivityResultLauncher<Uri> startActivityForResult = registerForActivityResult(
            new ActivityResultContracts.TakePicture(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    launchMediaScanIntent();
                    try {

                        Bitmap bitmap = decodeBitmapUri(getActivity(), imageUri);
                        if (bitmap != null) {
                            binding.cameraPreview.setImageURI(imageUri);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Failed to load Image", Toast.LENGTH_SHORT)
                                .show();
                        Log.e(TAG, e.toString());
                    }
                }
            });

    private void takeBarcodePicture() {

        long millis = new Date().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String datetime  = dateFormat.format(new Date());
        String datetime = String.valueOf(millis);

       // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String assetId = sharedPref.getString(getResources().getString(R.string.asset_id), "1");

        String Image = assetId + "_" + outlet_id + "_" + datetime + ".jpg";

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), Image);
        imageUri = FileProvider.getUriForFile(getActivity(),
                BuildConfig.APPLICATION_ID + ".provider", photo);

        Log.d("Image_photo", String.valueOf(imageUri.getPath()));
        Log.d("Image_file", photo.getAbsolutePath());

        //  Image = RealPathUtil.getRealPath(getContext(),imageUri);


        editor.putString(getString(R.string.Image), photo.getAbsolutePath());
        editor.apply();

        Log.d("Image_path", Image);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult.launch(imageUri);
        /*} else{

            Toast.makeText(getContext(),"Outlet and Device ID missing", Toast.LENGTH_SHORT);

            Fragment fragment = new SelectDeviceFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.select_picture_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        }*/


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {

            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
            // binding.cameraPreview.setImageURI(imageUri);
        }
        super.onSaveInstanceState(outState);
    }

    private void launchMediaScanIntent() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);

        getActivity().sendBroadcast(mediaScanIntent);
    }

    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }

    public static String getImageFilePath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            Log.d("getPath() uri: ", uri.toString());
            Log.d("getPath() uri auth: ", uri.getAuthority());
            Log.d("getPath() uri path: ", uri.getPath());

            // ExternalStorageProvider
            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                System.out.println("getPath() docId: " + docId + ", split: " + split.length + ", type: " + type);

                // This is for checking Main Memory
                if ("primary".equalsIgnoreCase(type)) {
                    if (split.length > 1) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1] + "/";
                    } else {
                        return Environment.getExternalStorageDirectory() + "/";
                    }
                    // This is for checking SD Card
                } else {
                    return "storage" + "/" + docId.replace(":", "/");
                }

            }
        }
        return null;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager = (LocationManager)  getActivity().getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
               // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                latitude = String.valueOf(location.getLatitude());
                editor.putString(getResources().getString(R.string.latitude), latitude);
                longitude = String.valueOf(location.getLongitude());
                editor.putString(getResources().getString(R.string.longitude), longitude);
                editor.apply();

            }
            else{
                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //remove location callback:
        locationManager.removeUpdates(this);

        //open the map:
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());

       // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getResources().getString(R.string.latitude), latitude);
        editor.putString(getResources().getString(R.string.longitude), longitude);
        editor.apply();

    }


}