package com.example.moviedbappwithmap

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.moviedbappwithmap.presentation.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.http.Body

class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = String::class.java.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d("TAG", "From: ${remoteMessage?.from}")

        remoteMessage?.data?.isNullOrEmpty()?.let {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if(!remoteMessage.data.isNullOrEmpty()) {
                val msg: String = remoteMessage.data.get("message").toString()
                sendNotification(msg)
            }
        }

        remoteMessage?.notification?.let{
            sendNotification(remoteMessage.notification?.body)
        }
    }

    private fun sendNotification(messageBody: String?){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val message = "\"Super Вторник\" – супер-выгодное предложение для всех!\n" +
                "Каждый вторник покупайте билеты в кино по выгодным ценам:\n" +
                "Обычные залы: взрослый - 1000 тг, студенческий - 800 тг, детский - 600 тг.\n" +
                "В зале IMAX: взрослый - 1500 тг, детский - 1000 тг, студенческий - 1300 тг,\n" +
                "В залах Premium: First Class Laser - 7500 тг, Comfort - 1300 тг."
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_movie_filter_black_24dp)
            .setContentTitle("Movie DB App")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(message))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setColor(ContextCompat.getColor(this,R.color.colorAccent))
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0,notificationBuilder.build())
    }

}