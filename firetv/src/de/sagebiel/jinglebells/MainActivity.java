package de.sagebiel.jinglebells;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/** Fire TV wrapper: shows the bundled practice page in TV mode and maps the media keys. */
public class MainActivity extends Activity {
    private WebView web;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setAllowFileAccess(true);
        web.setWebViewClient(new WebViewClient());
        web.setBackgroundColor(0xFFFFFFFF);
        setContentView(web);
        web.loadUrl("file:///android_asset/index.html#tv");
        web.requestFocus();
    }

    private void js(String code) {
        web.evaluateJavascript(code, null);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        int k = e.getKeyCode();
        boolean up = e.getAction() == KeyEvent.ACTION_UP;
        switch (k) {
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
            case KeyEvent.KEYCODE_MEDIA_PLAY:
            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                if (up) js("window.tvToggle&&window.tvToggle()");
                return true;
            case KeyEvent.KEYCODE_MEDIA_REWIND:
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                if (up) js("window.tvRewind&&window.tvRewind()");
                return true;
            default:
                return super.dispatchKeyEvent(e);
        }
    }

    @Override
    protected void onPause() {
        js("window.tvStop&&window.tvStop()");
        super.onPause();
    }
}
