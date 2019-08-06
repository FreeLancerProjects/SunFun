package com.creativeshare.sunfun.notifications;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.creativeshare.sunfun.R;
import com.creativeshare.sunfun.models.UserModel;
import com.creativeshare.sunfun.preferences.Preferences;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FireBaseMessaging extends FirebaseMessagingService {
    Preferences preferences = Preferences.getInstance();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        final Map<String, String> map = remoteMessage.getData();

        for (String key : map.keySet())
        {
            Log.e("key : ",key+" value :"+ map.get(key));
        }
        /*if (getSession().equals(Tags.session_login)) {

            String current_user_id = String.valueOf(getUserData().getUser().getId());
            String to_user_id = map.get("to_user");
            if (current_user_id.equals(to_user_id)) {
                ManageNotification(map);
            }

        }*/


    }

    private void ManageNotification(Map<String, String> map) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            createNewVersionNotification(map);

        } else {
            createOldVersionNotification(map);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNewVersionNotification(final Map<String, String> map) {

        String sound_Path = "android.resource://" + getPackageName() + "/" + R.raw.not;

        String notification_type = map.get("notification_type");




            String CHANNEL_ID = "my_channel_02";
            CharSequence CHANNEL_NAME = "my_channel_name";
            int IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;

            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            final NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE);
            channel.setShowBadge(true);
            channel.setSound(Uri.parse(sound_Path), new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                    .setLegacyStreamType(AudioManager.STREAM_NOTIFICATION)
                    .build()
            );

            builder.setChannelId(CHANNEL_ID);
            builder.setSound(Uri.parse(sound_Path), AudioManager.STREAM_NOTIFICATION);
            //builder.setSmallIcon(R.drawable.ic_notification);






    }

    private void createOldVersionNotification(final Map<String, String> map) {

        String sound_Path = "android.resource://" + getPackageName() + "/" + R.raw.not;

        String notification_type = map.get("notification_type");





    }

    private UserModel getUserData() {
        return preferences.getUserData(this);
    }

    private String getSession() {
        return preferences.getSession(this);
    }

}
