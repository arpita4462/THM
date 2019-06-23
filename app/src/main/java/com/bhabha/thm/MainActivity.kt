package com.bhabha.thm

import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
//import com.airbnb.lottie.LottieAnimationView
import android.webkit.WebSettings.PluginState
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import ccom.bhabha.thm.MyWebViewClient


class MainActivity : AppCompatActivity(), NetworkStateReceiver.NetworkStateReceiverListener {

    var networkStateReceiver: NetworkStateReceiver? = null

    private var pDialog: ProgressBar? = null
//    private var noInternetView: LottieAnimationView? = null

    var webView: WebView? = null
    var url: String? = null
    var url1: String? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.about_us_web)
        pDialog = findViewById(R.id.loading)
//        noInternetView = findViewById(R.id.no_internet)

        //check internet connections
        networkStateReceiver = NetworkStateReceiver()
        networkStateReceiver!!.addListener(this)
        this.registerReceiver(networkStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        url = "https://tmc.gov.in/digital-library/"
//        url = "https://www.myanurakti.com/wp-login.php/"

        pDialog!!.visibility = View.VISIBLE
        webView!!.visibility = View.GONE
//        noInternetView!!.visibility = View.GONE

        callwebsite()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun callwebsite() {
//        try {

        pDialog!!.visibility = View.GONE
        webView!!.visibility = View.VISIBLE
//        noInternetView!!.visibility = View.GONE

        webView!!.clearCache(false)
        webView!!.settings.javaScriptEnabled=true
        webView!!.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE)
        webView!!.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
        webView!!.loadUrl(url)


/*
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }
        }
*/

        webView!!.setWebViewClient(MyWebViewClient())
        webView!!.setFocusable(true)
        webView!!.setFocusableInTouchMode(true)
//        webView!!.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE)

//        webView!!.getSettings().setJavaScriptEnabled(true)
        webView!!.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH)
        webView!!.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE)
        webView!!.getSettings().setDomStorageEnabled(true)
        webView!!.getSettings().setDatabaseEnabled(true)
        webView!!.getSettings().setAppCacheEnabled(true)
        webView!!.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
        webView!!.setWebChromeClient(WebChromeClient())
        webView!!.getSettings().setPluginState(PluginState.ON)
//            webView!!.getSettings().setBuiltInZoomControls(true)
//            webView!!.getSettings().setSupportZoom(true)
//
//            webView!!.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY)
//            webView!!.setScrollbarFadingEnabled(false)
//
//            webView!!.setInitialScale(30)
//            webView!!.loadUrl(url)
        Log.i("Getweb", webView!!.url)
//        } catch (e: Exception) {
//
//            pDialog!!.visibility=View.GONE
//            webView!!.visibility=View.GONE
//            noInternetView!!.visibility=View.VISIBLE
//            e.printStackTrace()
//
//        }


    }

    override fun onKeyDown(keyCode:Int,event: KeyEvent):Boolean
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView!!.canGoBack()) {
            webView!!.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event);
    }


    override fun onBackPressed() {
        if (/*webView!!.isFocused &&*/ webView!!.canGoBack()) {
            webView!!.goBack()
        } else {
            super.onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStart() {
        super.onStart()
        callwebsite()
    }


    /*Checking Internet Connection and Showing Message*/
    private fun showSnack(isConnected: String) {
        val message: String
        val color: Int
        if (isConnected.equals("true")) {

        } else {
            message = getString(R.string.sorry_nointernet)
            color = Color.RED
            val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            val sbView = snackbar.view
            val textView = sbView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
            textView.setTextColor(color)
            snackbar.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun networkAvailable() {
        callwebsite()
        showSnack("true")
    }

    override fun networkUnavailable() {
        pDialog!!.visibility = View.GONE
        webView!!.visibility = View.GONE
//        noInternetView!!.visibility = View.VISIBLE
        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
        showSnack("false")
    }

    override fun onDestroy() {
        super.onDestroy()
        networkStateReceiver!!.removeListener(this)
        this.unregisterReceiver(networkStateReceiver)
    }

    //save instance onScreenOrientaion
    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putString("KeyUser", url)
        super.onSaveInstanceState(outState)
    }

    //restore instance onScreenOrientation
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        url = savedInstanceState!!.getString("KeyUser")

    }
}
