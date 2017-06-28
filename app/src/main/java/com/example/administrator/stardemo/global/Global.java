package com.example.administrator.stardemo.global;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.example.administrator.stardemo.R;
import com.example.administrator.stardemo.fragment.PlayVideoFragment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

import static com.example.administrator.stardemo.global.Const.RC_HANDLE_CAMERA_PERM;
import static com.example.administrator.stardemo.global.Const.REQUEST_ID_MULTIPLE_PERMISSIONS;
import static com.example.administrator.stardemo.global.Const.TAG;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Administrator on 06/17/2017.
 */

public class Global {

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            builder.connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS);

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private  boolean checkAndRequestPermissions(Activity activity) {
        int camerapermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        /*int writepermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionLocation = ContextCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionRecordAudio = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);*/


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        /*if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }*/
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public static void requestCameraPermission(final Activity activity, View viewToSetPermissionListener) {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(activity, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(activity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        viewToSetPermissionListener.setOnClickListener(listener);
        Snackbar.make(viewToSetPermissionListener, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok_cap, listener)
                .show();
    }

    public static void testURL() throws Exception {
        String strUrl = "http://stackoverflow.com/about";

        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();

            int responseCode = urlConn.getResponseCode();
//            assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
        } catch (IOException e) {
            System.err.println("Error creating HTTP connection");
            e.printStackTrace();
            throw e;
        }
    }

    public static class AsyncCheckUrlConnection extends AsyncTask<Integer, Integer, Void> {

        private String stringUrl;
        private PlayVideoFragment playVideoFragment;
        private int responseCode;

        public AsyncCheckUrlConnection(PlayVideoFragment playVideoFragment, String stringUrl) {
            this.stringUrl = stringUrl;
            this.playVideoFragment = playVideoFragment;
        }

        @Override
        protected Void doInBackground(Integer... params) {
            responseCode = 0;
            try {
                URL url = new URL(stringUrl);
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                urlConn.connect();

                responseCode = urlConn.getResponseCode();
//            assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
            } catch (IOException e) {
                System.err.println("Error creating HTTP connection");
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            playVideoFragment.onCheckUrlConnection(responseCode);
        }
    }

    public static String returnYoutubeVideoIdFromUrl(String stringUrl){
        String id = stringUrl;
        if (stringUrl.contains(Const.YOUTUBE_LINK_TYPE_1)){
            id = stringUrl.substring(stringUrl.lastIndexOf("=") + 1);
        } else {
            if (stringUrl.contains(Const.YOUTUBE_LINK_TYPE_2)){
                id = stringUrl.substring(stringUrl.lastIndexOf("/") + 1);
            }
        }
        return id;
    }
}
