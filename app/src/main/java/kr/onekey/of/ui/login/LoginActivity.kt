package kr.onekey.of.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.messaging.FirebaseMessaging
import kr.onekey.of.R
import kr.onekey.of.base.BaseActivity
import kr.onekey.of.databinding.ActivityLoginBinding
import kr.onekey.of.ui.main.MainActivity
import kr.onekey.of.ui.set.SettingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.exitProcess

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override val viewModel: LoginViewModel by viewModel()
    private val isInvalidTokenLogin: Boolean by lazy {
        intent.getBooleanExtra(INVALID_TOKEN_LOGIN, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()
    }

    private fun observe() {
        viewModel.apply {
            succeedLogin.observe(this@LoginActivity) { successLogin ->
                if (successLogin) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    binding.tvDescription.visibility = View.VISIBLE
                }
            }
        }
    }
    companion object {
        const val INVALID_TOKEN_LOGIN = "INVALID_TOKEN_LOGIN"
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isInvalidTokenLogin) {
            finishAffinity()
        }
    }
}