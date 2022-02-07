package com.example.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;


public class Fragment_map extends Fragment {


    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    private MarkerEventListener eventListener = new MarkerEventListener();
    private GpsInfo gps;

    private double gpsLatitude = 0.0;
    private double gpsLongitude = 0.0;

    List<Address> addressList;


    ArrayList<Meta.Documents> lottoList = new ArrayList<>();
    MapView mapView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);


        mapView = new MapView(getContext());
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        mapView.setPOIItemEventListener(eventListener);
        ViewGroup mapViewContainer = v.findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);


        // 권한 요청
        if (!isPermission) {
            callPermission();
        }
        gps = new GpsInfo(getContext());

        if (gps.isGetLocation()) {
            gpsLatitude = gps.getLatitude();
            gpsLongitude = gps.getLongitude();
        }

        // 현재위치 마커 띄우기
        MapPOIItem marker = new MapPOIItem();
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(gpsLatitude, gpsLongitude);
        mapView.setMapCenterPointAndZoomLevel(mapPoint,1,true);
        marker.setItemName("현재 위치");
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setCustomImageResourceId(R.drawable.placeholder); // 마커 이미지.
        marker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        marker.getCustomCalloutBalloon();
        mapView.addPOIItem(marker);

        // 로또판매점 띄우기
        new KeywordRepository().retrieveData(this, gpsLongitude, gpsLatitude);
        Log.v("좌표값", gpsLatitude + "," + gpsLongitude);

        Toast.makeText(getContext(),lottoList.size()+"d",Toast.LENGTH_SHORT).show();

            return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onRequestPermissionsResult(requestCode, permissions, grantResults);
        onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    public void retrieveOnSuccess(List<Meta.Documents> data) {
        int getListSize = data.size();
        for (int i = 0; i < getListSize; i++) {
            String place_name = data.get(i).getPlace_name();
            String address_name = data.get(i).getAddress_name();
            String phone = data.get(i).getPhone();
            String x = data.get(i).getX();
            String y = data.get(i).getY();
            Log.v("상점 이름",data.get(i).getPlace_name());
            Log.v("상점 주소",data.get(i).getAddress_name());
            Log.v("상점 전화번호",data.get(i).getPhone());
            Log.v("상점 좌표",data.get(i).getX()+","+data.get(i).getY());
            lottoList.add(new Meta.Documents(place_name,address_name,phone,x,y));
            Toast.makeText(getContext(),"retrieveOnSuccess",Toast.LENGTH_SHORT).show();


            for (int j = 0; j < lottoList.size(); j++) {
                MapPOIItem marker1 = new MapPOIItem();
                double X = Double.parseDouble(lottoList.get(i).getY());
                double Y = Double.parseDouble(lottoList.get(i).getX());

                MapPoint mapPoint1 = MapPoint.mapPointWithGeoCoord(X, Y);

                marker1.setItemName(lottoList.get(i).getPlace_name());
                marker1.setMapPoint(mapPoint1);
                marker1.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                marker1.setCustomImageAnchor(0.5f, 1.0f);
                marker1.setCustomImageResourceId(R.drawable.balloon_img);// 기본으로 제공하는 BluePin 마커 모양.
                marker1.getCustomCalloutBalloon();

                mapView.addPOIItem(marker1);

                Toast.makeText(getContext(), "진입"
                        , Toast.LENGTH_SHORT).show();

            }
        }
    }


    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {

        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.balloon_layout, null);
        }

        //vo 함께 받아 vo.getId와 vo.get_tel 출력
        @Override
        public View getCalloutBalloon(MapPOIItem mapPOIItem) {
            String re_id = mapPOIItem.getItemName();
//            for (int i = 0; i < svo.size(); i++) {
//                if (svo.get(i).getWorker_id().equals(re_id)) {
//                    id = svo.get(i).getWorker_id();
//                    tel = svo.get(i).getWorker_phone();
//                }
//            }
            ((ImageView) mCalloutBalloon.findViewById(R.id.location_img)).setImageResource(R.drawable.balloon_img);
            ((TextView) mCalloutBalloon.findViewById(R.id.bl_name)).setText("로또가게이름");
            ((TextView) mCalloutBalloon.findViewById(R.id.bl_tel)).setText("010-1234-5678");

            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem mapPOIItem) {

            return mCalloutBalloon;
        }
    }


    class MarkerEventListener implements MapView.POIItemEventListener {

        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
            Log.d("ERROR", "SIBAL");
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 0);

            } else {
                Intent call_intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "010-5620-5950"));
                call_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(call_intent);

            }

        }


        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

        }
    }

    public class GpsInfo extends Service implements LocationListener {

        private final Context mContext;

        // 현재 GPS 사용유무
        boolean isGPSEnabled = false;

        // 네트워크 사용유무
        boolean isNetworkEnabled = false;

        // GPS 상태값
        boolean isGetLocation = false;

        Location location;
        double lat; // 위도
        double lon; // 경도

        // 최소 GPS 정보 업데이트 거리 10미터
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

        // 최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분
        private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

        protected LocationManager locationManager;


        public GpsInfo(Context context) {
            this.mContext = context;
            getLocation();
        }

        @TargetApi(23)
        public Location getLocation() {
            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(
                            mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                            mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                return null;
            }

            try {
                locationManager = (LocationManager) mContext
                        .getSystemService(LOCATION_SERVICE);

                // GPS 정보 가져오기
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // 현재 네트워크 상태 값 알아오기
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    // GPS 와 네트워크사용이 가능하지 않을때 소스 구현
                } else {
                    this.isGetLocation = true;
                    // 네트워크 정보로 부터 위치값 가져오기
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                // 위도 경도 저장
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }

                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    lat = location.getLatitude();
                                    lon = location.getLongitude();
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return location;
        }

        /**
         * GPS 종료
         */
        public void stopUsingGPS() {
            if (locationManager != null) {
                locationManager.removeUpdates(GpsInfo.this);
            }
        }

        /**
         * 위도값을 가져옵니다.
         */
        public double getLatitude() {
            if (location != null) {
                lat = location.getLatitude();
            }
            return lat;
        }

        /**
         * 경도값을 가져옵니다.
         */
        public double getLongitude() {
            if (location != null) {
                lon = location.getLongitude();
            }
            return lon;
        }

        /**
         * GPS 나 wife 정보가 켜져있는지 확인합니다.
         */
        public boolean isGetLocation() {
            return this.isGetLocation;
        }

        /**
         * GPS 정보를 가져오지 못했을때
         * 설정값으로 갈지 물어보는 alert 창
         */
        public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

            alertDialog.setTitle("GPS 사용유무셋팅");
            alertDialog.setMessage("GPS 셋팅이 되지 않았을수도 있습니다. \n 설정창으로 가시겠습니까?");

            // OK 를 누르게 되면 설정창으로 이동합니다.
            alertDialog.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            mContext.startActivity(intent);
                        }
                    });
            // Cancle 하면 종료 합니다.
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alertDialog.show();
        }

        @Override
        public IBinder onBind(Intent arg0) {
            return null;
        }

        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }
    }


}


