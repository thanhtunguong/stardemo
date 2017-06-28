package com.example.administrator.stardemo.interfaces;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.administrator.stardemo.activity.ScannerActivity;
import com.example.administrator.stardemo.global.Const;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.io.IOException;

/**
 * Created by Administrator on 06/27/2017.
 */

public class StarDemoAPICaller implements StarDemoAPICallerInterface {

    private ScannerActivity activity;

    public StarDemoAPICaller(ScannerActivity activity){
        this.activity = activity;
    }

    @Override
    public void getUrlFromScanningResult(String scanningResult) {
        AndroidNetworking.get(Const.BASE_URL + scanningResult)
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        new AsyncExecuteAPIResponse(response).execute();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(activity, "Error: " + anError, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class AsyncExecuteAPIResponse extends AsyncTask<Integer, Integer, Void> {

        private String jsonString;
        private String url;

        public AsyncExecuteAPIResponse(String jsonString) {
            this.jsonString = jsonString;
        }

        @Override
        protected Void doInBackground(Integer... params) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = null;
            url = null;
            try {
                rootNode = objectMapper.readTree(jsonString);
                url = rootNode.get("videoUrl").asText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (url != null){
                activity.onUrlReceived(url);
            }
        }
    }
}
