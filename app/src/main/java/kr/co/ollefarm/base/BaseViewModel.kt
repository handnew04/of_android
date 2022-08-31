package kr.co.ollefarm.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {
   private var job: Job = Job()
   protected val TAG = javaClass.name

   private val coroutineContext: CoroutineContext
      get() = Dispatchers.IO + job

   fun launch(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch(coroutineContext) {
      block()
   }
}