//Jose M. Aguilar (jaguil61)

package edu.jaguil61.project5musicclient;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class MusicService extends Service {

    private static final int NOTIFICATION_ID = 1;
    MediaPlayer mPlayer;
    private int mStartID;
    private Notification notification ;

    private static String CHANNEL_ID = "Music player style" ;

    @Override
    public void onCreate() {
        super.onCreate();

        this.createNotificationChannel();

        // Create a notification area notification so the user
        // can get back to the MusicServiceClient
        final Intent notificationIntent = new Intent(getApplicationContext(),
                SecondActivity.class);

        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setOngoing(true).setContentTitle("Music Playing")
                .setContentText("Click to Access Music Player")
                .setTicker("Music is playing!")
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher_round, "Show service", pendingIntent)
                .build();

        mPlayer = new MediaPlayer();

        if (null != mPlayer) {
            mPlayer.setLooping(false);

            // Stop Service when music has finished playing
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                    // stop Service if it was started with this ID
                    // Otherwise let other start commands proceed
                    stopSelf(mStartID);
                }
            });
        }

        // Put this Service in a foreground state, so it won't
        // readily be killed by the system
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {

        //URL String
        String URLString = intent.getStringExtra("songURL");

        try {
            setupMediaPlayer(URLString);
        } catch (IOException e) {
            Log.i("MUSIC_SERVICE", e.toString());
        }

        mPlayer.start();

        // Don't automatically restart this Service if it is killed
        return START_NOT_STICKY;
    }

    //prepare the medial player
    private void setupMediaPlayer(String theURL) throws IOException {
        mPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        mPlayer.setDataSource(this, Uri.parse(theURL));

        mPlayer.prepare();
    }


    //can't bind this service
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (null != mPlayer) {
            mPlayer.stop();
            mPlayer.release();
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = "Music player notification";
        String description = "The channel for music player notifications";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, name, importance);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.setDescription(description);
        }
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
    }
}
