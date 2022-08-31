package kr.co.ollefarm.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import kr.co.ollefarm.R
import kr.co.ollefarm.base.BaseActivity
import kr.co.ollefarm.databinding.ActivityLoginBinding
import kr.co.ollefarm.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

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