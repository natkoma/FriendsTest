package com.example.nvkomarova.friends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nvkomarova.friends.adapters.ImageGalleryAdapter;
import com.example.nvkomarova.friends.models.VKPhotosModel;
import com.example.nvkomarova.friends.utils.PreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendPhotosActivity extends AppCompatActivity {

    public static final String USER_ID = "user_id";

    private long userId;
    private RecyclerView photosView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_photos);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userId = bundle.getLong(USER_ID, 0);
        }

        getPhotos();
    }

    private void getPhotos(){
        String to = PreferencesManager.getVKToken();
        App.getEndpoints().getPhotos(userId, PreferencesManager.getVKToken(), PreferencesManager.getVKApiVersion()).enqueue(new Callback<VKPhotosModel>() {
            @Override
            public void onResponse(Call<VKPhotosModel> call, Response<VKPhotosModel> response) {
                VKPhotosModel result = response.body();
                if (response != null && result != null && result.response != null){
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(FriendPhotosActivity.this, 2);
                    photosView = (RecyclerView) findViewById(R.id.photos_view);
                    photosView.setHasFixedSize(true);
                    photosView.setLayoutManager(layoutManager);

                    List<VKPhotosModel.ResponseModel.PhotoModel> photos = result.response.items;
                    if(photos.size() > 0 ) {
                        ImageGalleryAdapter adapter = new ImageGalleryAdapter(FriendPhotosActivity.this, photos);
                        photosView.setAdapter(adapter);
                    }else {
                        TextView empty = (TextView) findViewById(R.id.empty);
                        empty.setVisibility(View.VISIBLE);
                    }
                }else {
                    TextView not_result = (TextView) findViewById(R.id.not_result);
                    not_result.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<VKPhotosModel> call, Throwable t) {

            }
        });
    }
}
