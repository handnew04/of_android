package kr.co.ollefarm.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kr.co.ollefarm.R
import kr.co.ollefarm.base.BaseActivity
import kr.co.ollefarm.databinding.ActivitySplashBinding
import kr.co.ollefarm.ui.login.LoginActivity
import kr.co.ollefarm.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {
    override val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        observe()
        viewModel.checkEssentialUpdate()
    }

    private fun observe() {
        viewModel.apply {
            succeedLogin.observe(this@SplashActivity) { succeedLogin ->

                val intent: Intent =
                    if (succeedLogin) {
                        Intent(this@SplashActivity, MainActivity::class.java)
                    } else {
                        Intent(this@SplashActivity, LoginActivity::class.java)
                    }

                startActivity(intent)
                finish()
            }
            isSupportVersion.observe(this@SplashActivity) { isSupportVersion ->
                if (isSupportVersion) {
                    viewModel.checkAutoLogin()
                } else {
                    showVersionUpdateDialog()
                }
            }
        }
    }

    private fun showVersionUpdateDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("필수 업데이트를 위해\n플레이 스토어로 이동합니다.")
            setPositiveButton(
                "확인"
            ) { dialog, which ->
                moveToPlayStore()
                dialog.dismiss()
            }
        }.show()
    }

    private fun moveToPlayStore() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "https://play.google.com/store/apps/details?id=${applicationContext.packageName}"
            )
            setPackage("com.android.vending")
        }

        startActivity(intent)
    }
}