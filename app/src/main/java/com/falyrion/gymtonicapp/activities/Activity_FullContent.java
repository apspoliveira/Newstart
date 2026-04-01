package com.falyrion.gymtonicapp.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.falyrion.gymtonicapp.R;

public class Activity_FullContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_content);

        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        String content = getIntent().getStringExtra("content");

        TextView textViewTitle = findViewById(R.id.textViewFullTitle);
        textViewTitle.setText(title);

        ImageView buttonBack = findViewById(R.id.buttonBackFull);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebView webView = findViewById(R.id.webViewFullContent);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        if (url != null && !url.isEmpty()) {
            webView.loadUrl(url);
        } else if (content != null) {
            webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
        }
    }
}
