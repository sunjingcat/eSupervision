package com.zz.supervision.business.company;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 显示位置
 */
public class ShowLocationActivity extends MyBaseActivity {

    ImageView ivBack;
    TextView tvTitle;
    ImageView ivMore;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.location_mapview)
    MapView mMapView;
    private BaiduMap mMap;
    private double lat;
    private double lng;
    private String title = "";


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initMap();
        lat = getIntent().getDoubleExtra("location_lat", 0.0);
        lng = getIntent().getDoubleExtra("location_lng", 0.0);
        title = getIntent().getStringExtra("location_title");

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }

    private void initMap() {
        mMap = mMapView.getMap();
        //隐层缩放控件
        mMapView.showZoomControls(false);
        mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(18).build()));
        //禁用旋转手势
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        //禁用拖拽手势
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        //禁用旋转
        mMap.getUiSettings().setOverlookingGesturesEnabled(false);
        //禁用指南针
        mMap.getUiSettings().setCompassEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        moveCenter(new LatLng(lat, lng));
    }

    @Override
    protected int getContentView() {
        SDKInitializer.initialize(getApplicationContext());
        return R.layout.activity_show_location;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    private void moveCenter(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).draggable(false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getViewBitmap(title)));
        Marker marker = (Marker) mMap.addOverlay(markerOptions);
        MapStatusUpdate status1 = MapStatusUpdateFactory.newLatLng(latLng);
        mMap.animateMapStatus(status1, 500);
    }

    private Bitmap getViewBitmap(String text) {
        View addViewContent = LayoutInflater.from(this).inflate(R.layout.location_marker, null);
        TextView textView = addViewContent.findViewById(R.id.tv_name);
        textView.setText(text);
        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());
        addViewContent.buildDrawingCache();

        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }

}

