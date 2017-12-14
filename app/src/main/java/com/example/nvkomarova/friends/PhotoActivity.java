package com.example.nvkomarova.friends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

    public static final String EXTRA_PHOTO = "PHOTO";
    private ImageView mImageView;
    private String photoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        mImageView = (ImageView) findViewById(R.id.image);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            photoURL = bundle.getString(EXTRA_PHOTO, "");
        }

        Picasso.with(this)
                .load(photoURL)
                .into(mImageView);

    }
}