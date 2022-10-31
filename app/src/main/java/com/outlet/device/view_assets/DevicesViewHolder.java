package com.outlet.device.view_assets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.outlet.device.R;
import com.outlet.device.models.Upload;

import java.io.File;

public class DevicesViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;
    public TextView tvDeviceCondition;
    public TextView tvAsset;
    public TextView tvOutlet;
    public TextView tvDate;
    public TextView tvSynced;
    public ConstraintLayout constraintLayout;

    public DevicesViewHolder(View itemView) {
        super(itemView);
        tvOutlet = (TextView) itemView.findViewById(R.id.txtOutlet);
        tvAsset = (TextView) itemView.findViewById(R.id.txtAsset);
        tvDeviceCondition = (TextView) itemView.findViewById(R.id.txtCondition);
        imageView = (ImageView) itemView.findViewById(R.id.deviceImage);
        tvDate = (TextView) itemView.findViewById(R.id.txtDate);
        tvSynced = (TextView) itemView.findViewById(R.id.txtSynced);
        constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.constraintLayout);
    }

    public void bind(Upload upload) {
        tvDeviceCondition.setText(upload.getStateId());
        tvAsset.setText(upload.getAssetId());
        tvOutlet.setText(upload.getOutletId());
        tvDate.setText(upload.getDatetime());
        tvSynced.setText(upload.getSynced().toString());
        //  imageView.setImageResource(upload.getImageId());
        File imgFile = new  File(upload.getImage());
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        imageView.setImageBitmap(myBitmap);
    }

    static DevicesViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new DevicesViewHolder(view);
    }

}
