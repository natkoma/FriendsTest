package com.example.nvkomarova.friends.api;
import com.example.nvkomarova.friends.models.VKFriendsModel;
import com.example.nvkomarova.friends.models.VKPhotosModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndpoints {
    @GET("friends.get")
    Call<VKFriendsModel> getFriends(@Query("user_id") String user_id,
                                    @Query("v") String version,
                                    @Query("fields") String fields);

    @GET("photos.getAll?extended=0&count=200&photo_sizes=0&no_service_albums=1&need_hidden=0&skip_hidden=1")
    Call<VKPhotosModel> getPhotos(@Query("owner_id") long owner_id,
                                  @Query("access_token") String access_token,
                                  @Query("v") String version);
}
