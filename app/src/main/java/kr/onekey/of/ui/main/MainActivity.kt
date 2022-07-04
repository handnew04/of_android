package kr.onekey.of.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import kr.onekey.of.BuildConfig
import kr.onekey.of.R
import kr.onekey.of.base.BaseActivity
import kr.onekey.of.databinding.ActivityMainBinding
import kr.onekey.of.ui.set.SettingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
   override val viewModel: MainViewModel by viewModel()
   private val webView: WebView by lazy {
      binding.webView
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      initWebView()
   }

   @SuppressLint("SetJavaScriptEnabled")
   private fun initWebView() {
      webView.apply {
         settings.javaScriptEnabled = true
         addJavascriptInterface(WebAppInterface(this@MainActivity), "Android")
         webViewClient = WebViewClient()
         loadUrl(BuildConfig.WEB_URL)
      }
   }

   override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
      if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
         webView.goBack()
         return true
      }

      return super.onKeyDown(keyCode, event)
   }

   class WebAppInterface(private val context: Context) {
      @JavascriptInterface
      fun showSettingActivity() {
         val intent = Intent(context, SettingActivity::class.java)
         context.startActivity(intent)
      }
   }

}