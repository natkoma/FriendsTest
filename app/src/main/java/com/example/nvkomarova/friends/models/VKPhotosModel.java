package com.example.nvkomarova.friends.models;

import java.util.List;

public class VKPhotosModel {
    public ResponseModel response;

    public class ResponseModel {
        public int count;

        public List<PhotoModel> items;

        public class PhotoModel {
            public long id;
            public String photo_75;
            public String photo_130;
            public String photo_604;
            public String photo_807;
            public String photo_1280;

            public String getMiddlePhoto(){
                if (photo_604 != null) return photo_604;
                if (photo_130 != null) return photo_130;
                return photo_75;
            }

            public String getBigPhoto(){
                if(photo_1280 != null) return photo_1280;
                if(photo_807 != null) return photo_807;
                return getMiddlePhoto();
            }
        }
    }
}
