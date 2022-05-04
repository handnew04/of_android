package kr.onekey.of.ui.main

import android.os.Bundle
import kr.onekey.of.R
import kr.onekey.of.base.BaseActivity
import kr.onekey.of.databinding.ActivityMainBinding
import kr.onekey.of.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
   override val viewModel: MainViewModel by viewModel()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
   }
}