package kr.co.ollefarm.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import kr.co.ollefarm.BuildConfig
import kr.co.ollefarm.R
import kr.co.ollefarm.base.BaseActivity
import kr.co.ollefarm.databinding.ActivityMainBinding
import kr.co.ollefarm.ui.login.LoginActivity
import kr.co.ollefarm.ui.set.SettingActivity
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
         addJavascriptInterface(WebAppInterface(this@MainActivity, viewModel), "Android")
         webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
               Log.d(
                  TAG,
                  "WebView log: ${consoleMessage?.message()}, from line: ${consoleMessage?.lineNumber()}"
               )
               return true
            }
         }
         loadUrl(BuildConfig.WEB_URL, viewModel.getToken())
      }
   }

   override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
      if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
         webView.goBack()
         return true
      }

      return super.onKeyDown(keyCode, event)
   }

   class WebAppInterface(private val context: Context, private val viewModel: MainViewModel) {
      @JavascriptInterface
      fun showSettingActivity() {
         val intent = Intent(context, SettingActivity::class.java)
         context.startActivity(intent)
      }

      @JavascriptInterface
      fun updateTokens(accessToken: String, refreshToken: String) {
         Log.e("updateToken", "a : $accessToken, r : $refreshToken")
         viewModel.updateTokens(accessToken, refreshToken)
      }

      @JavascriptInterface
      fun showLoginActivity() {
         Log.e("showLoginActivity", "show!")
         val intent = Intent(context, LoginActivity::class.java)
         intent.flags.apply {
            FLAG_ACTIVITY_CLEAR_TASK
            FLAG_ACTIVITY_NEW_TASK
         }

         (context as MainActivity).apply {
            startActivity(intent)
            finish()
         }
      }
   }

}