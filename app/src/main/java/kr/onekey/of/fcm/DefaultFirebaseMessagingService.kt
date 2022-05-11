package kr.onekey.of.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class DefaultFirebaseMessagingService : FirebaseMessagingService(){
   override fun onMessageReceived(message: RemoteMessage) {
      super.onMessageReceived(message)

   }

   override fun onNewToken(token: String) {
      super.onNewToken(token)
      //sendRegistrationToServer(token)
   }

   private fun sendNotification(messageBody: String) {
      
   }
}