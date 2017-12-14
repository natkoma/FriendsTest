package com.example.nvkomarova.friends;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.nvkomarova.friends.adapters.FriendsAdapter;
import com.example.nvkomarova.friends.models.VKFriendsModel;
import com.example.nvkomarova.friends.utils.PreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int AUTH_VK = 0;

    private View VKAuthBtn;
    private ListView friendsView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferencesManager.init(this);

        setContentView(R.layout.main);

        VKAuthBtn = findViewById(R.id.vk_auth_btn);
        friendsView = (ListView) findViewById(R.id.friends_view);
        progress = (ProgressBar) findViewById(R.id.progress_bar);

        if (PreferencesManager.getVKToken().isEmpty()) {
            friendsView.setVisibility(View.GONE);
            VKAuthBtn.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            VKAuthBtn.setOnClickListener((View v) -> {
                startActivityForResult(new Intent(this, VkontakteActivity.class), AUTH_VK);
            });
        }else {
            VKAuthBtn.setVisibility(View.GONE);
            friendsView.setVisibility(View.VISIBLE);
            getFriends();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch( requestCode ) {
            case AUTH_VK:
                if( resultCode == Activity.RESULT_OK ) {
                    VKAuthBtn.setVisibility(View.GONE);
                    friendsView.setVisibility(View.VISIBLE);
                    getFriends();
                }
                break;
        }
    }

    private void getFriends(){
        progress.setVisibility(View.VISIBLE);
        String vkUid =PreferencesManager.getVKUid();
        App.getEndpoints().getFriends(vkUid, PreferencesManager.getVKApiVersion(), "city,photo_50").enqueue(new Callback<VKFriendsModel>() {
            @Override
            public void onResponse(Call<VKFriendsModel> call, Response<VKFriendsModel> response) {
                progress.setVisibility(View.GONE);
                VKFriendsModel result = response.body();

                if(response != null && result != null && result.response != null) {
                    List<VKFriendsModel.ResponseModel.FriendModel> friends = result.response.items;
                    FriendsAdapter adapter = new FriendsAdapter(MainActivity.this, friends);
                    friendsView.setAdapter(adapter);
                    friendsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MainActivity.this, FriendPhotosActivity.class);
                            intent.putExtra(FriendPhotosActivity.USER_ID, id);
                            startActivity(intent);
                        }
                    });
                }else {

                }
            }

            @Override
            public void onFailure(Call<VKFriendsModel> call, Throwable t) {

            }
        });
    }
}