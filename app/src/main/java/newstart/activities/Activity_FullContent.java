package newstart.activities;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import newstart.R;
import com.google.android.material.appbar.MaterialToolbar;

public class Activity_FullContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force Light Mode
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_content);

        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        String content = getIntent().getStringExtra("content");
        int fragmentID = getIntent().getIntExtra("fragmentID", 0); // Default to Nutrition

        MaterialToolbar toolbar = findViewById(R.id.toolbarFull);
        if (toolbar != null) {
            toolbar.setTitle(title);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            updateThemeColors(toolbar, fragmentID);
        }

        WebView webView = findViewById(R.id.webViewFullContent);
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });

            if (url != null && !url.isEmpty()) {
                webView.loadUrl(url);
            } else if (content != null) {
                webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
            }
        }
    }

    private void updateThemeColors(MaterialToolbar toolbar, int fragmentID) {
        int colorRes = R.color.nutrition_primary;
        switch (fragmentID) {
            case 0: colorRes = R.color.nutrition_primary; break;
            case 1: colorRes = R.color.workout_primary; break;
            case 2: colorRes = R.color.water_primary; break;
            case 3: colorRes = R.color.sun_primary; break;
            case 4: colorRes = R.color.temperance_primary; break;
            case 5: colorRes = R.color.air_primary; break;
            case 6: colorRes = R.color.rest_primary; break;
            case 7: colorRes = R.color.trust_primary; break;
            case 8: colorRes = R.color.settings_primary; break;
        }

        int color = ContextCompat.getColor(this, colorRes);
        toolbar.setBackgroundColor(color);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }
}
