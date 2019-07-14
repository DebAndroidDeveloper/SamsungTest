package com.samsung.samsungcodingtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.samsungcodingtest.R;
import com.samsung.samsungcodingtest.model.PhotoDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>{

    private static final String TAG = PhotoListAdapter.class.getCanonicalName();
    private Context mContext;
    private List<PhotoDetails> mPhotoList;
    private Listener mListener;
    private Picasso mPicassoInstance;

    public PhotoListAdapter(@NonNull Context context,@NonNull Listener listener){
        this.mPhotoList = new ArrayList<>();
        this.mContext = context;
        this.mListener = listener;
        mPicassoInstance = Picasso.get();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_photo_list_row,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        PhotoDetails photoDetails = this.mPhotoList.get(position);
        String imageUrl = photoDetails.getThumbnailUrl();
        if(imageUrl != null && !imageUrl.isEmpty()) {
            this.mPicassoInstance.load(imageUrl).into(holder.photoImageView);
        }
        else
            holder.photoImageView.setImageResource(R.mipmap.ic_launcher_round);

        holder.itemView.setOnClickListener(createClickListener(photoDetails));
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public interface Listener {
        void onRowItemClicked(@NonNull PhotoDetails photoDetails);
    }

    public List<PhotoDetails> getPhotoList() {
        return mPhotoList;
    }

    public void setPhotoList(List<PhotoDetails> mPhotoList) {
        this.mPhotoList = mPhotoList;
        this.notifyDataSetChanged();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
         ImageView photoImageView;

        public PhotoViewHolder(View view){
            super(view);
            this.photoImageView =  view.findViewById(R.id.imageView_thumbnil);
        }
    }

    private View.OnClickListener createClickListener(@NonNull final PhotoDetails photoDetails) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRowItemClicked(photoDetails);
            }
        };
    }
}
