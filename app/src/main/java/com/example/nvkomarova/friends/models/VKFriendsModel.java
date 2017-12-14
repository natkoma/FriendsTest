package com.example.nvkomarova.friends.models;

import java.util.List;

public class VKFriendsModel {
    public ResponseModel response;

    public class ResponseModel {
        public int count;

        public List<FriendModel> items;

        public class FriendModel {
            public long id;
            public String first_name;
            public String last_name;
            public String photo_50;
        }
    }

    public Error error;

    public class Error{
        public Integer error_code;
        public String error_msg;
    }
}
