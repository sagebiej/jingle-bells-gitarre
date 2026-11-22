package de.sagebiel.jinglebells.handy;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Phone wrapper: loads the live page from GitHub Pages so it updates by itself,
 * falls back to the bundled copy when offline, and passes the microphone through.
 */
public class MainActivity extends Activity {
    private static final String LIVE = "https://sagebiej.github.io/jingle-bells-gitarre/";
    private static final String LOCAL = "file:///android_asset/index.html";
    private WebView web;
    private PermissionRequest pending;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setStatusBarColor(0xFFC8102E);
        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setAllowFileAccess(true);
        web.setBackgroundColor(0xFFFFFFFF);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView v, WebResourceRequest r, WebResourceError e) {
                if (r.isForMainFrame() && r.getUrl().toString().startsWith(LIVE)) v.loadUrl(LOCAL);
            }
        });
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest r) {
                if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                    r.grant(new String[]{PermissionRequest.RESOURCE_AUDIO_CAPTURE});
                } else {
                    pending = r;
                    requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                }
            }
        });
        setContentView(web);
        if (state != null) web.restoreState(state); else web.loadUrl(LIVE);
    }

    @Override
    public void onRequestPermissionsResult(int code, String[] perms, int[] res) {
        if (pending == null) return;
        if (res.length > 0 && res[0] == PackageManager.PERMISSION_GRANTED)
            pending.grant(new String[]{PermissionRequest.RESOURCE_AUDIO_CAPTURE});
        else pending.deny();
        pending = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        web.saveState(out);
    }

    @Override
    protected void onPause() {
        web.evaluateJavascript("window.tvStop&&window.tvStop()", null);
        super.onPause();
    }
}
