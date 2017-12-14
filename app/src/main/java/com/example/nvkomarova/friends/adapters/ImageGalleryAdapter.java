package com.example.nvkomarova.friends.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nvkomarova.friends.PhotoActivity;
import com.example.nvkomarova.friends.R;
import com.example.nvkomarova.friends.models.VKPhotosModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {

    private List<VKPhotosModel.ResponseModel.PhotoModel> mPhotos;
    private Context mContext;

    public ImageGalleryAdapter(Context context, List<VKPhotosModel.ResponseModel.PhotoModel> photos) {
        mContext = context;
        mPhotos = photos;
    }

    @Override
    public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.photo_item, parent, false);
        ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

        VKPhotosModel.ResponseModel.PhotoModel photo = mPhotos.get(position);
        ImageView imageView = holder.mPhotoImageView;

        Picasso.with(mContext)
                .load(photo.getMiddlePhoto())
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        if (mPhotos == null) return 0;
        return mPhotos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mPhotoImageView;

        public MyViewHolder(View itemView) {

            super(itemView);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                VKPhotosModel.ResponseModel.PhotoModel photo = mPhotos.get(position);
                Intent intent = new Intent(mContext, PhotoActivity.class);
                intent.putExtra(PhotoActivity.EXTRA_PHOTO, photo.getBigPhoto());
                mContext.startActivity(intent);
            }
        }
    }


}