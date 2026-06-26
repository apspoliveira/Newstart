package newstart.activities;

import android.graphics.Color;
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
import androidx.core.content.ContextCompat;

import newstart.R;
import com.google.android.material.appbar.MaterialToolbar;

public class Activity_FullContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_content);

        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        String content = getIntent().getStringExtra("content");
        int fragmentID = getIntent().getIntExtra("fragmentID", 0);

        MaterialToolbar toolbar = findViewById(R.id.toolbarFull);
        if (toolbar != null) {
            toolbar.setTitle(title);
            toolbar.setNavigationOnClickListener(v -> finish());
            updateThemeColors(toolbar, fragmentID);
        }

        WebView webView = findViewById(R.id.webViewFullContent);
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

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
                int themeColor = getThemeColor(fragmentID);
                String styledHtml = getStyledHtml(content, themeColor);
                webView.loadDataWithBaseURL(null, styledHtml, "text/html", "UTF-8", null);
            }
        }
    }

    private int getThemeColor(int fragmentID) {
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
        return ContextCompat.getColor(this, colorRes);
    }

    private void updateThemeColors(MaterialToolbar toolbar, int fragmentID) {
        int color = getThemeColor(fragmentID);
        toolbar.setBackgroundColor(color);
        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setTint(Color.WHITE);
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    private String getStyledHtml(String html, int themeColor) {
        String hexColor = String.format("#%06X", (0xFFFFFF & themeColor));
        String lightHexColor = hexColor + "15"; // approx 8% opacity

        return "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\">" +
                "<style>" +
                "  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap');" +
                "  body { font-family: 'Poppins', sans-serif; background-color: #F8FAFC; margin: 0; padding: 16px; color: #334155; -webkit-font-smoothing: antialiased; }" +
                "  .recipe-container { background: #FFFFFF; border-radius: 32px; padding: 28px; box-shadow: 0 20px 40px rgba(0,0,0,0.04); margin-bottom: 20px; }" +
                "  h1 { color: #1E293B; font-size: 28px; font-weight: 800; line-height: 1.2; margin: 0 0 24px 0; letter-spacing: -0.5px; }" +
                "  h2, h3 { color: " + hexColor + "; font-size: 18px; font-weight: 700; margin: 32px 0 16px 0; display: flex; align-items: center; text-transform: uppercase; letter-spacing: 1.5px; }" +
                "  h3::before { content: ''; display: inline-block; width: 8px; height: 8px; background-color: " + hexColor + "; margin-right: 12px; border-radius: 2px; transform: rotate(45deg); }" +
                "  ul { padding: 0; list-style: none; margin: 0; background-color: " + lightHexColor + "; border-radius: 20px; padding: 20px; }" +
                "  li { position: relative; padding-left: 32px; margin-bottom: 14px; font-size: 15px; line-height: 1.6; color: #475569; font-weight: 500; }" +
                "  li:last-child { margin-bottom: 0; }" +
                "  li::before { content: '✓'; position: absolute; left: 0; color: " + hexColor + "; font-weight: 900; font-size: 18px; line-height: 1; }" +
                "  p { font-size: 16px; line-height: 1.8; color: #64748B; margin: 16px 0; }" +
                "  ol { padding-left: 24px; margin: 0; }" +
                "  ol li { padding-left: 8px; list-style-type: decimal; color: #475569; }" +
                "  ol li::before { display: none; }" +
                "  b, strong { color: #1E293B; font-weight: 700; }" +
                "  .footer { margin-top: 48px; text-align: center; color: #94A3B8; font-size: 10px; font-weight: 700; letter-spacing: 3px; text-transform: uppercase; border-top: 1px solid #F1F5F9; padding-top: 24px; }" +
                "</style></head><body>" +
                "<div class='recipe-container'>" + html + 
                "<div class='footer'>Newstart Lifestyle • Nutrition</div>" +
                "</div>" +
                "</body></html>";
    }
}
