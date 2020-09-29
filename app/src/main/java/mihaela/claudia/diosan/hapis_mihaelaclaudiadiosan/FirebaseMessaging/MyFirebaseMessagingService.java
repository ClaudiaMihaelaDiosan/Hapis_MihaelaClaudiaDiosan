package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.FirebaseMessaging;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor.HomeDonor;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;


public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    public MyFirebaseMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 /* request code */, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        int notificationId = new Random().nextInt(60000);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setSmallIcon(R.mipmap.hapis_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.hapis_icon))
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setChannelId(getString(R.string.notification_channel))
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
        } else {
            builder.setSmallIcon(R.mipmap.hapis_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.hapis_icon))
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_MAX);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, builder.build());
    }
}
