package kr.onekey.of.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kr.onekey.of.R
import kr.onekey.of.base.BaseActivity
import kr.onekey.of.databinding.ActivitySplashBinding
import kr.onekey.of.ui.login.LoginActivity
import kr.onekey.of.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {
    override val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        observe()
        viewModel.checkAutoLogin()
    }

    private fun observe() {
        viewModel.succeedLogin.observe(this@SplashActivity) { succeedLogin ->

            val intent: Intent =
                if (succeedLogin) {
                    Intent(this@SplashActivity, MainActivity::class.java)
                } else {
                    Intent(this@SplashActivity, LoginActivity::class.java)
                }

            startActivity(intent)
            finish()
        }
    }

}