package kr.co.ollefarm.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionUtil {
   fun hasPermissions(context: Context, requestPermissions: Array<String>): Boolean {
      for (permission in requestPermissions) {
         if (ContextCompat.checkSelfPermission(
               context,
               permission
            ) == PackageManager.PERMISSION_DENIED
         ) {
            return false
         }
      }
      return true
   }
}