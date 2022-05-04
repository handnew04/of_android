package kr.onekey.of.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kr.onekey.of.BR

abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel>(
   @LayoutRes
   protected val layoutResId: Int
) : AppCompatActivity() {
   protected lateinit var binding: VDB
   protected abstract val viewModel: VM
   protected lateinit var TAG: String
   //protected lateinit var toolbar: ToolbarBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      TAG = javaClass.name

      binding = DataBindingUtil.setContentView<VDB>(this, layoutResId).apply {
         lifecycleOwner = this@BaseActivity
         // TODO: 2022/05/03 _all -> viewModel로 변경해야함
         setVariable(BR._all, viewModel)
      }
   }

//   fun initToolbar(toolbar: ToolbarBinding) {
//      this.toolbar = toolbar
//
//      this.toolbar.apply {
//         ivBackArrow.setOnClickListener {
//            finish()
//         }
//      }
//   }
//
//   fun setToolbar(title: String) {
//      toolbar.tvTitle.text = title
//   }
}