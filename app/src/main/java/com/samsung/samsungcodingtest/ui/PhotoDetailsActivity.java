package com.samsung.samsungcodingtest.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.samsung.samsungcodingtest.R;
import com.samsung.samsungcodingtest.database.PhotoDBRepository;
import com.samsung.samsungcodingtest.util.Constants;
import com.squareup.picasso.Picasso;

public class PhotoDetailsActivity extends AppCompatActivity {

    private PhotoDBRepository photoDBRepository;
    private int photoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        this.photoDBRepository = new PhotoDBRepository();
        Intent intent = getIntent();
        if(intent != null) {
            TextView textView = findViewById(R.id.textView_photo_details);
            textView.setText(intent.getStringExtra(Constants.PHOTO_TITLE));
            ImageView imageView = findViewById(R.id.imageView_fullScreen);
            String photoUrl = intent.getStringExtra(Constants.PHOTO_URL);
            this.photoId = intent.getIntExtra(Constants.PHOTO_ID,0);
            if(photoUrl != null && !photoUrl.isEmpty()){
                Picasso.get().load(photoUrl).into(imageView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_photo_details,menu);
        MenuItem menuItem = menu.getItem(0);
        if(menuItem != null){
            View itemView = menuItem.getActionView();
            if(itemView != null) {
                ImageView imageView = itemView.findViewById(R.id.favorite_photo);
                if(photoDBRepository.getPhotoFavoriteStatus(this.photoId)){
                    imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
                }else {
                    imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
            }
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.favorite_photo){
            View itemView = item.getActionView();
            if(itemView != null){
                ImageView imageView = itemView.findViewById(R.id.favorite_photo);
                if(photoDBRepository.getPhotoFavoriteStatus(this.photoId)){
                    if(photoDBRepository.updatePhoto(false,this.photoId)){
                        imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    }
                }else {
                    if(photoDBRepository.updatePhoto(true,this.photoId)){
                        imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
