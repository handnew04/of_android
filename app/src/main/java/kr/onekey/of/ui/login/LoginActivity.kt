package kr.onekey.of.ui.login

import android.os.Bundle
import android.widget.Toast
import kr.onekey.of.R
import kr.onekey.of.base.BaseActivity
import kr.onekey.of.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()
    }

    private fun observe() {
        viewModel.apply {
            successLogin.observe(this@LoginActivity) { successLogin ->
                if (successLogin) {
                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}