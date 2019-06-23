package ccom.bhabha.thm

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient


 class MyWebViewClient : WebViewClient() {



//     val url="https://www.myanurakti.com/"
     override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
         Log.i("onPageStarted",url)
         super.onPageStarted(view, url, favicon)
     }
     @SuppressLint("NewApi")
     override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
         Log.i("shouldUrlLoading",request.toString())
         view!!.loadUrl(request!!.url.toString())
//         view!!.loadUrl(url)
         return true
     }
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        if(Uri.parse(url).getHost().endsWith("www.myanurakti.com")) {
            view!!.loadUrl(url)
            return false
        }

        Log.i("urlLoded2",url)
        return true
    }


    override fun onPageFinished(view: WebView, url: String) {
//        view.loadUrl(url)
        Log.i("onPageFinished",url)
    }

}