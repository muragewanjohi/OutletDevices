package com.outlet.device.view_assets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.outlet.device.R;

import java.io.File;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private ListData[] listdata;

    // RecyclerView recyclerView;
    public MyListAdapter(ListData[] listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListData myListData = listdata[position];

        holder.tvDeviceCondition.setText(listdata[position].getCondition());
        holder.tvDeviceName.setText(listdata[position].getDevice_name());
        holder.tvDeviceId.setText(listdata[position].getDevice_id());
        //holder.imageView.setImageResource(listdata[position].getImgId());
        Log.d("Image_getImgId",listdata[position].getImgId());
        File imgFile = new  File(listdata[position].getImgId());
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        holder.imageView.setImageBitmap(myBitmap);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getDevice_name(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tvDeviceName;
        public TextView tvDeviceCondition;
        public TextView tvDeviceId;
        public ConstraintLayout constraintLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.deviceImage);
            this.tvDeviceCondition = (TextView) itemView.findViewById(R.id.txtCondition);
            this.tvDeviceName = (TextView) itemView.findViewById(R.id.txt_device);
            this.tvDeviceId = (TextView) itemView.findViewById(R.id.txtDeviceId);
            constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.constraintLayout);
        }
    }
}
