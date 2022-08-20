package demo.app.venus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UserGuideActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        webView = findViewById(R.id.userGuideView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://venus-mu.vercel.app/");
    }

    @Override
    public void onBackPressed() {

        if(webView.canGoBack()){
            webView.goBack();
        }else{
        super.onBackPressed();
        }
    }
}