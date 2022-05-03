package kr.onekey.of.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
   private var job: Job = Job()
   protected val TAG = javaClass.name

   override val coroutineContext: CoroutineContext
      get() = Dispatchers.IO + job
}