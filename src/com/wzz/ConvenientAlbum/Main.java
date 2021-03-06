package com.wzz.ConvenientAlbum;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.wzz.ConvenientAlbum.adapter.DrawerMenuListAdapter;
import com.wzz.ConvenientAlbum.bean.LocationInfoBean;
import com.wzz.ConvenientAlbum.bean.PhotoInfoBean;
import com.wzz.ConvenientAlbum.fragment.FirstFragment;
import com.wzz.ConvenientAlbum.interfaces.INotifyDataSetChanged;
import com.wzz.ConvenientAlbum.sqlite.DBManage;
import com.wzz.ConvenientAlbum.util.Const;
import com.wzz.ConvenientAlbum.util.LocationController;
import com.wzz.ConvenientAlbum.util.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main extends Activity implements INotifyDataSetChanged {
    private ActionBarHelper actionBarHelper;
    private ActionBarDrawerToggle drawerToggle;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ArrayList<String> menuContent;

    private LocationInfoBean locationInfoBean;
    private PhotoInfoBean photoInfoBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.drawer_menu);
        menuContent = getMenuItemName();
        DrawerMenuListAdapter drawerMenuAdapter = new DrawerMenuListAdapter(this, menuContent);
        drawerList.setAdapter(drawerMenuAdapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerLayout.setDrawerListener(new DrawerMenuListener());
        actionBarHelper = createActionBarHelper();
        actionBarHelper.init();
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
        if (savedInstanceState == null) {
            setFragment(0);
        }
        String s;
        if (!(s = Utils.getString(this, "recent_location", "")).isEmpty()) {
            locationInfoBean = new LocationInfoBean();
            String[] temp = s.split(",");
            locationInfoBean.setLongitude(Double.valueOf(temp[0]));
            locationInfoBean.setLatitude(Double.valueOf(temp[1]));
            locationInfoBean.setProvince(temp[2]);
            locationInfoBean.setCity(temp[3]);
            locationInfoBean.setOthers(temp[4]);
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.camera:
                openCamera();
                break;
            case R.id.location:
                startLocation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 启动相机
     */
    private void openCamera() {
        if (locationInfoBean == null) {
            startLocation();
        }
        photoInfoBean = new PhotoInfoBean();
        photoInfoBean.setPath(Const.CAMERA_PATH);
        if (createCameraDirectory(photoInfoBean.getPath()) == Const.COMMON_FAIL) {
            Utils.toastSDCardError(this);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        photoInfoBean.setName(format.format(new Date()) + ".jpg");
        File photoFile = new File(photoInfoBean.getPath(), photoInfoBean.getName());
        Uri photoUri = Uri.fromFile(photoFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, Const.CAMERA_REQUEST_CODE);
    }

    /**
     * 启动定位
     */
    private void startLocation() {
        LocationController controller = new LocationController(this, this);
        controller.start();
    }

    /**
     * 创建相机文件夹
     * @param path 文件夹路径
     * @return 是否创建 0：已存在， 1：创建成功， -1：创建失败
     */
    private int createCameraDirectory(String path) {

        if (!Utils.hasSDCard()) {
            return Const.COMMON_FAIL;
        }

        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                return Const.COMMON_SUCCESS;
            } else {
                return Const.COMMON_FAIL;
            }
        }
        return Const.COMMON_NO_CHANGE;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Const.CAMERA_REQUEST_CODE:
                    if (locationInfoBean == null) {
                        locationInfoBean = new LocationInfoBean();
                    }
                    locationInfoBean.setPath(photoInfoBean.getPath());
                    locationInfoBean.setName(photoInfoBean.getName());
                    DBManage.getInstance(this).saveLocation(locationInfoBean);
                    Toast.makeText(this, getString(R.string.photo_save_to) + photoInfoBean.getPath() + photoInfoBean.getName(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void notifyDataSetChanged(int what, int status, Object value, int arg1, int arg2) {
        if (what == Const.FROM_LOCATION) {
            locationInfoBean = (LocationInfoBean) value;
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setFragment(position);
        }
    }

    private void setFragment(int position) {
        actionBarHelper.setTitle(menuContent.get(position));
        drawerLayout.closeDrawer(drawerList);
        drawerList.setItemChecked(position, true);

        Fragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt(FirstFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    private class DrawerMenuListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerOpened(View drawerView) {
            drawerToggle.onDrawerOpened(drawerView);
            actionBarHelper.onDrawerOpened();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            drawerToggle.onDrawerClosed(drawerView);
            actionBarHelper.onDrawerClosed();
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            drawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            drawerToggle.onDrawerStateChanged(newState);
        }
    }

    private ArrayList<String> getMenuItemName() {
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            temp.add("这是菜单" + i);
        }
        return temp;
    }

    private ActionBarHelper createActionBarHelper() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return new ActionBarHelperICS();
        } else {
            return new ActionBarHelper();
        }
    }

    private class ActionBarHelper {
        public void init() {}
        public void onDrawerClosed() {}
        public void onDrawerOpened() {}
        public void setTitle(CharSequence title) {}
    }

    private class ActionBarHelperICS extends ActionBarHelper {
        private final ActionBar mActionBar;
        private CharSequence mDrawerTitle;
        private CharSequence mTitle;

        ActionBarHelperICS() {
            mActionBar = getActionBar();
        }

        @Override
        public void init() {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mTitle = mDrawerTitle = getTitle();
        }

        @Override
        public void onDrawerClosed() {
            super.onDrawerClosed();
            mActionBar.setTitle(mTitle);
        }

        @Override
        public void onDrawerOpened() {
            super.onDrawerOpened();
            mActionBar.setTitle(mDrawerTitle);
        }

        @Override
        public void setTitle(CharSequence title) {
            mTitle = title;
            mActionBar.setTitle(mTitle);
        }
    }
}
