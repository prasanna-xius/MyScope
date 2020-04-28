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
import kotlinx.android.synthetic.main.covid_tracker_main.*
import kotlinx.android.synthetic.main.educational_blog_main.*
import kotlinx.android.synthetic.main.educational_blog_main.header1

class Cell_Covid19_WebActivity :BaseActivity() {
    companion object {
        const val PAGE_URL = "pageUrl_Covid_Cell"
        const val MAX_PROGRESS = 100
        fun newIntent1(context: Context, pageUrl_Covid_Cell: String): Intent {
            val intent = Intent(context, Cell_Covid19_WebActivity::class.java)
            intent.putExtra(PAGE_URL, pageUrl_Covid_Cell)
            return intent
        }
    }
    private lateinit var pageUrl_Covid_Cell: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covid_tracker_main)

        activitiesToolbar()
        header1!!.text = "The Cell"

//        val settings = webView1.settings
//
//        // Enable java script in web view
//        settings.javaScriptEnabled = true
//
//        webView1.loadUrl("http://www.covid19india.org/")
        pageUrl_Covid_Cell = intent.getStringExtra(PAGE_URL)
                ?: throw IllegalStateException("field $PAGE_URL missing in Intent")
        initWebView()
        setWebClient()
        handlePullToRefresh()
        loadUrl(pageUrl_Covid_Cell)
    }
    private fun handlePullToRefresh() {
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView1.settings.javaScriptEnabled = true
        webView1.settings.loadWithOverviewMode = true
        webView1.settings.useWideViewPort = true
        webView1.settings.domStorageEnabled = true
        webView1.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }
    }
    private fun setWebClient() {
        webView1.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(
                    view: WebView,
                    newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
                if (newProgress < MAX_PROGRESS && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
                if (newProgress == MAX_PROGRESS) {
                    progressBar.visibility = ProgressBar.GONE
                }
            }
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView1.canGoBack()) {
            webView1.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, exit the activity)
        return super.onKeyDown(keyCode, event)
    }
    private fun loadUrl(pageUrl_Covid_Cell: String) {
        webView1.loadUrl(pageUrl_Covid_Cell)
    }


}
