package com.kelompokv.praktikum.network.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kelompokv.praktikum.R;
import com.kelompokv.praktikum.activity.user.MainUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class FirebaseService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token){
        Log.d("TAG", "Refreshed Token: "+token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        String dataPayload = data.get("data");

        if(remoteMessage.getData().size() > 0){
            Log.e("TAG", "Message Data Payload: "+ remoteMessage.getData());

            try {
                JSONObject jsonParse = new JSONObject(dataPayload);
                showNotif(jsonParse.getString("title"), jsonParse.getString("message"));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        if(remoteMessage.getNotification() != null){
            Log.e("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            showNotif(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private void showNotif(String title, String body){
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", body);

        Intent intent = new Intent(this, MainUser.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setLights(200,200,200)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setOnlyAlertOnce(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, notifBuilder.build());
    }
}
