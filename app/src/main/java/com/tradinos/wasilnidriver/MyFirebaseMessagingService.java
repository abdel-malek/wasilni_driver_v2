package com.tradinos.wasilnidriver;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tradinos.wasilnidriver.receivers.NotificationReceiver;

import java.util.Map;

import static com.tradinos.wasilnidriver.util.UtilFunction.p;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String ADMIN_CHANNEL_ID = "dragon-channel-id";
    private NotificationCompat.Builder notificationBuilder ;
    private NotificationManager notificationManager ;
    private NotificationChannel adminChannel;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        setupNotification(remoteMessage);

        super.onMessageReceived(remoteMessage);
    }


    private void setupNotification(RemoteMessage remoteMessage){
        notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSmallIcon(R.mipmap.driver_logo_text)
                .setAutoCancel(true);

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence adminChannelName = "dragon-channel";
            String adminChannelDescription = "notification";



            adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
            adminChannel.setDescription(adminChannelDescription);
            adminChannel.enableLights(true);
            adminChannel.setLightColor(Color.RED);
            adminChannel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(adminChannel);
            }
        }
        notificationAction(remoteMessage.getData());
    }

    public void notificationAction(Map<String ,String> data){
        String  messageCode =  data.get("code");
//        Log.e( "notificationAction: ", messageCode);
        Intent intent = new Intent("booking_notification");
        intent.putExtra("event",messageCode);
        intent.setAction("com.wasilni.wasilnidriverv2.receivers");
        sendOrderedBroadcast(intent,
                null,
                new NotificationReceiver(),
                null,
                Activity.RESULT_OK,
                null,
                null);

    }

}