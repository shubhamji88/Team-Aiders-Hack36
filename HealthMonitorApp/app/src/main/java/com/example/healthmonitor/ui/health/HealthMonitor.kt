package com.example.healthmonitor.ui.health


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.healthmonitor.R


class HealthMonitor : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health )
        supportActionBar?.hide()
        val width= resources.displayMetrics.widthPixels
        val height= resources.displayMetrics.heightPixels*0.305
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        window.setLayout(width.toInt(),height.toInt())
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val param=window.attributes
        param.gravity= Gravity.BOTTOM
        val type=intent.getIntExtra("type",-1)
        val web=findViewById<WebView>(R.id.web)
        when(type){
            1->setUpHeart(web)
            2->setUpSpo2(web)
        }

    }
    override fun onPause() {
        super.onPause()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun setUpSpo2(webview: WebView){
        true.also { webview.settings.javaScriptEnabled = it }
        webview.webViewClient = WebViewClient()
        webview.webChromeClient = WebChromeClient()
        webview.settings.setGeolocationEnabled(true)
        webview.isSoundEffectsEnabled = true
        webview.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        webview.settings.useWideViewPort = true
        webview.loadUrl("https://thingspeak.com/channels/1354140/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15")
    }
    private fun setUpHeart(webview: WebView){
        true.also { webview.settings.javaScriptEnabled = it }
        webview.webViewClient = WebViewClient()
        webview.webChromeClient = WebChromeClient()
       webview.requestFocus()
        webview.settings.setGeolocationEnabled(true)
        webview.isSoundEffectsEnabled = true
        webview.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        webview.settings.useWideViewPort = true
        webview.loadUrl("https://thingspeak.com/channels/1354140/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&max=120&min=60&results=60&type=line")
    }
}

