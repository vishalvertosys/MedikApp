package medik247.vs.com.medik;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;

/**
 * Created by Indosurplus on 5/9/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    String title;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
// [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> sessionMap = sessionManager.getUserDetails();
        String datalogin = sessionMap.get(SessionManager.LoginDetail);
        Log.e("DataLogin", "//////////" + datalogin);
        if (datalogin.equals("doctor")) {
            Log.e("ResponseMEssage1", "" + remoteMessage.getData().get("Body"));
            Log.e("ResponseMEssage2", "" + remoteMessage.getFrom());
            Log.e("ResponseMEssage3", "" + remoteMessage.getCollapseKey());
            Log.e("ResponseMEssage4", "" + remoteMessage.getMessageType());
            Log.e("ResponseMEssage5", "" + remoteMessage.getNotification().getBody());
            Log.e("ResponseMEssage6", "" + remoteMessage.getNotification().getTitle());
            title = remoteMessage.getNotification().getTitle();


// TODO(developer): Handle FCM messages here.
            Log.d(TAG, "From: " + remoteMessage.getFrom());

            Intent intent = new Intent(this, RequestAcceptReject.class);
            intent.putExtra("KEY_ID", remoteMessage.getNotification().getBody());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/sound");

            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.appicon)
                    .setContentText("" + remoteMessage.getNotification().getTitle())
                    .setAutoCancel(true)

                    .setSound(Uri.parse(getApplicationContext().getResources().getResourceName(R.raw.sound)))
                    .setContentIntent(pendingIntent);
            notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationBuilder.setLights(getResources().getColor(R.color.colorPrimaryDark), 1000, 1000);
            notificationBuilder.setSound(uri);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
// Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
                Log.e("ResponseMEssage1", "" + remoteMessage.getData().get("Body"));
                Log.e("ResponseMEssage2", "" + remoteMessage.getFrom());
                Log.e("ResponseMEssage3", "" + remoteMessage.getCollapseKey());
                Log.e("ResponseMEssage4", "" + remoteMessage.getMessageType());
                Log.e("ResponseMEssage5", "" + remoteMessage.getNotification().getBody());
                Log.e("ResponseMEssage6", "" + remoteMessage.getNotification().getTitle());

                title = remoteMessage.getNotification().getTitle();


// TODO(developer): Handle FCM messages here.
                Log.d(TAG, "From: " + remoteMessage.getFrom());

                Intent intent2 = new Intent(this, ScreenSignIn.class);
                intent.putExtra("KEY_ID", remoteMessage.getNotification().getBody());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Uri uri2 = Uri.parse("android.resource://" + getPackageName() + "/raw/sound");

                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                NotificationCompat.Builder notificationBuilder2 = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.appicon)
                        .setContentText("" + remoteMessage.getNotification().getTitle())
                        .setAutoCancel(true)

                        .setSound(Uri.parse(getApplicationContext().getResources().getResourceName(R.raw.sound)))
                        .setContentIntent(pendingIntent);
                notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                notificationBuilder.setLights(getResources().getColor(R.color.colorPrimaryDark), 1000, 1000);
                notificationBuilder.setSound(uri);

                NotificationManager notificationManager2 =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//            sendNotification("text");
            }
// Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }
        }


        /**
         * Create and show a simple notification containing the received FCM message.
         *
         * @param messageBody FCM message body received.
         */
//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/raw/sound");
//
//        try {
//            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//            r.play();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.appicon)
//                .setContentText(title)
//                .setAutoCancel(true)
//
//                .setSound(Uri.parse(getApplicationContext().getResources().getResourceName(R.raw.sound)))
//                .setContentIntent(pendingIntent);
//        notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
//        notificationBuilder  .setLights(getResources().getColor(R.color.colorPrimaryDark), 1000, 1000);
//        notificationBuilder.setSound(uri);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
        else {
            Log.e("ResponseMEssage1", "" + remoteMessage.getData().get("Body"));
            Log.e("ResponseMEssage2", "" + remoteMessage.getFrom());
            Log.e("ResponseMEssage3", "" + remoteMessage.getCollapseKey());
            Log.e("ResponseMEssage4", "" + remoteMessage.getMessageType());
            Log.e("ResponseMEssage5", "" + remoteMessage.getNotification().getBody());
            Log.e("ResponseMEssage6", "" + remoteMessage.getNotification().getTitle());
            title = remoteMessage.getNotification().getTitle();


// TODO(developer): Handle FCM messages here.
            Log.d(TAG, "From: " + remoteMessage.getFrom());

            Intent intent = new Intent(this, ScreenSignIn.class);
            intent.putExtra("KEY_ID", remoteMessage.getNotification().getBody());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/sound");

            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.appicon)
                    .setContentText("" + remoteMessage.getNotification().getTitle())
                    .setAutoCancel(true)

                    .setSound(Uri.parse(getApplicationContext().getResources().getResourceName(R.raw.sound)))
                    .setContentIntent(pendingIntent);
            notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationBuilder.setLights(getResources().getColor(R.color.colorPrimaryDark), 1000, 1000);
            notificationBuilder.setSound(uri);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
// Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
                Log.e("ResponseMEssage1", "" + remoteMessage.getData().get("Body"));
                Log.e("ResponseMEssage2", "" + remoteMessage.getFrom());
                Log.e("ResponseMEssage3", "" + remoteMessage.getCollapseKey());
                Log.e("ResponseMEssage4", "" + remoteMessage.getMessageType());
                Log.e("ResponseMEssage5", "" + remoteMessage.getNotification().getBody());
                Log.e("ResponseMEssage6", "" + remoteMessage.getNotification().getTitle());

                title = remoteMessage.getNotification().getTitle();


// TODO(developer): Handle FCM messages here.
                Log.d(TAG, "From: " + remoteMessage.getFrom());

                Intent intent2 = new Intent(this, ScreenSignIn.class);
                intent.putExtra("KEY_ID", remoteMessage.getNotification().getBody());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Uri uri2 = Uri.parse("android.resource://" + getPackageName() + "/raw/sound");

                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                NotificationCompat.Builder notificationBuilder2 = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.appicon)
                        .setContentText("" + remoteMessage.getNotification().getTitle())
                        .setAutoCancel(true)

                        .setSound(Uri.parse(getApplicationContext().getResources().getResourceName(R.raw.sound)))
                        .setContentIntent(pendingIntent);
                notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                notificationBuilder.setLights(getResources().getColor(R.color.colorPrimaryDark), 1000, 1000);
                notificationBuilder.setSound(uri);

                NotificationManager notificationManager2 =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//            sendNotification("text");
            }
// Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }
        }


        /**
         * Create and show a simple notification containing the received FCM message.
         *
         * @param messageBody FCM message body received.
         */
//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/raw/sound");
//
//        try {
//            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//            r.play();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.appicon)
//                .setContentText(title)
//                .setAutoCancel(true)
//
//                .setSound(Uri.parse(getApplicationContext().getResources().getResourceName(R.raw.sound)))
//                .setContentIntent(pendingIntent);
//        notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
//        notificationBuilder  .setLights(getResources().getColor(R.color.colorPrimaryDark), 1000, 1000);
//        notificationBuilder.setSound(uri);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
    }
}