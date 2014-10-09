package com.wzz.ConvenientAlbum.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.wzz.ConvenientAlbum.R;

import java.util.ArrayList;

/**
 * Created by WANGZHENGZE on 2014/10/8.
 */
public class DrawerMenuListAdapter extends BaseAdapter {
    private ArrayList<String> menuContent;
    private Context context;

    public DrawerMenuListAdapter(Context context, ArrayList<String> menuContent) {
        this.context = context;
        this.menuContent = menuContent;
    }

    @Override
    public int getCount() {
        return menuContent.size();
    }

    @Override
    public Object getItem(int position) {
        return menuContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.drawer_menu_item, null);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.item_name);
        tvName.setText(menuContent.get(position));
        return convertView;
    }
}
