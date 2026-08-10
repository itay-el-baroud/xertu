package com.callshield;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.callshield.data.TokenManager;

public class LoginActivity extends AppCompatActivity {

    private WebView webView;
    private View errorLayout;
    private ProgressBar progressBar;
    private TextView errorText;
    private Button retryButton;
    private TokenManager tokenManager;

    private static final String LOGIN_URL = "https://media-note.ct.ws/login.php";
    private static final String SIGNUP_URL = "https://media-note.ct.ws/sign_up.php";
    private static final String FORGOT_URL = "https://media-note.ct.ws/forgot-password.php";
    private static final String SUCCESS_SCHEME = "myapp://auth-success";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tokenManager = new TokenManager(this);
        webView = findViewById(R.id.webView);
        errorLayout = findViewById(R.id.errorLayout);
        progressBar = findViewById(R.id.progressBar);
        errorText = findViewById(R.id.errorText);
        retryButton = findViewById(R.id.retryButton);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                if (url.startsWith(SUCCESS_SCHEME)) {
                    handleSuccess(url);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.startsWith(SUCCESS_SCHEME)) {
                    handleSuccess(url);
                    return true;
                }
                if (url.startsWith("https://media-note.ct.ws/")) {
                    return false;
                }
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()) {
                    showError("Connection problem");
                }
            }
        });

        retryButton.setOnClickListener(v -> {
            hideError();
            webView.reload();
        });

        webView.loadUrl(LOGIN_URL);
    }

    private void handleSuccess(String url) {
        Uri uri = Uri.parse(url);
        String token = uri.getQueryParameter("token");
        if (token!= null &&!token.isEmpty()) {
            tokenManager.saveToken(token);
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    private void showError(String message) {
        webView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        errorText.setText(message);
        progressBar.setVisibility(View.GONE);
    }

    private void hideError() {
        webView.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
    }
}
