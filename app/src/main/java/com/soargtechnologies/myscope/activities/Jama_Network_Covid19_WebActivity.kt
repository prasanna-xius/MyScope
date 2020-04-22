package com.soargtechnologies.myscope.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.soargtechnologies.myscope.R
import kotlinx.android.synthetic.main.covid19_map_mian.*
import kotlinx.android.synthetic.main.educational_blog_main.*
import kotlinx.android.synthetic.main.educational_blog_main.header1

class Jama_Network_Covid19_WebActivity :BaseActivity(){

    companion object {
        const val PAGE_URL = "pageUrl_CovidJama_Network"
        const val MAX_PROGRESS = 100
        fun newIntent1(context: Context, pageUrl_CovidJama_Network: String): Intent {
            val intent = Intent(context, Jama_Network_Covid19_WebActivity::class.java)
            intent.putExtra(PAGE_URL, pageUrl_CovidJama_Network)
            return intent
        }
    }
    private lateinit var pageUrl_CovidJama_Network: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covid19_map_mian)

        activitiesToolbar()
        header1!!.text = "JAMA Network"

//        webView.loadUrl("https://coronavirus.jhu.edu/map.html")

        pageUrl_CovidJama_Network = intent.getStringExtra(PAGE_URL)
                ?: throw IllegalStateException("field ${PAGE_URL} missing in Intent")
        initWebView()
        setWebClient()
        handlePullToRefresh()
        loadUrl(pageUrl_CovidJama_Network)
    }
    private fun handlePullToRefresh() {
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }
    }
    private fun setWebClient() {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(
                    view: WebView,
                    newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                progressBar1.progress = newProgress
                if (newProgress < MAX_PROGRESS && progressBar1.visibility == ProgressBar.GONE) {
                    progressBar1.visibility = ProgressBar.VISIBLE
                }
                if (newProgress == MAX_PROGRESS) {
                    progressBar1.visibility = ProgressBar.GONE
                }
            }
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, exit the activity)
        return super.onKeyDown(keyCode, event)
    }
    private fun loadUrl(pageUrl_CovidJama_Network: String) {
        webView.loadUrl(pageUrl_CovidJama_Network)

    }
}
