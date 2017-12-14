package com.example.nvkomarova.friends.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nvkomarova.friends.R;
import com.example.nvkomarova.friends.models.VKFriendsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FriendsAdapter extends BaseAdapter {

    private List<VKFriendsModel.ResponseModel.FriendModel> friends;
    private LayoutInflater inflater;
    private Context context;

    public FriendsAdapter(Context context, List<VKFriendsModel.ResponseModel.FriendModel> friends){
        this.friends = friends;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        if(friends == null) return 0;
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return friends.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder;
        VKFriendsModel.ResponseModel.FriendModel friend = friends.get(position);

        if(convertView == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.friend_item, parent, false);

            viewHolder.firstName = (TextView) view.findViewById(R.id.name);
            viewHolder.image = (ImageView) view.findViewById(R.id.icon);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.firstName.setText(friend.first_name + " " + friend.last_name);
        Picasso.with(context)
                .load(friend.photo_50)
                .into(viewHolder.image);

        return view;
    }

    static class ViewHolder {
        private TextView firstName;
        private ImageView image;
    }
}
