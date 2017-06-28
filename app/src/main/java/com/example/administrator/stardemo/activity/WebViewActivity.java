package com.example.administrator.stardemo.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.administrator.stardemo.R;
import com.example.administrator.stardemo.base.BaseActivity;

public class WebViewActivity extends BaseActivity {

    private WebViewActivity activity;
    private Context context;
    private WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.webView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString("url");
        }

        String frameVideo = "<html><body><br><iframe width=\\\"420\\\" height=\\\"315\\\" src=\\\"https://www.youtube.com/watch?v=MBq0ZJ90IAs\\\" frameborder=\\\"0\\\" allowfullscreen></iframe></body></html>";
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadData(frameVideo, "text/html", "utf-");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
