package com.example.axellageraldinc.crudmysql;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListUserAdapter extends BaseAdapter {
    List<UserModel> userList = new ArrayList<>();
    Context context;

    public ListUserAdapter(Context context, List<UserModel> userList){
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemViewHolder holder;
        View view1 = view;
        //Initiate view
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view1 = inflater.inflate(R.layout.activity_list_user_adapter, viewGroup, false);
            holder = new ItemViewHolder();
            holder.txtId = (TextView) view1.findViewById(R.id.txtId);
            holder.txtUsername = (TextView) view1.findViewById(R.id.txtUsername);
            holder.txtPassword = (TextView) view1.findViewById(R.id.txtPassword);
            view1.setTag(holder);
        } else {
            holder = (ItemViewHolder) view1.getTag();
        }

        //Set data ke listview
        holder.txtId.setText(String.valueOf(userList.get(i).getId()));
        holder.txtUsername.setText(userList.get(i).getUsername());
        holder.txtPassword.setText(userList.get(i).getPassword());
        return view1;
    }

    private static class ItemViewHolder{
        TextView txtId, txtUsername, txtPassword;
    }
}
