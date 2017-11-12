package com.mec.pants;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 11/11/2017.
 */

public class UserList extends ArrayAdapter {

    private Activity context;
    private List<User> userList;

    public UserList(Activity context, List<User> userList) {
        super(context, R.layout.profiletemplate, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.profiletemplate,null,true);

        TextView name = (TextView) listViewItem.findViewById(R.id.usernameText);
        TextView location = (TextView) listViewItem.findViewById(R.id.locationText);

        User user = userList.get(position);

        name.setText(user.getUsername());
        location.setText(user.getLocation());

        return listViewItem;

    }
}
