package ipod.anodsplace.info.ipodnotifier;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;

import java.util.List;

public class NotificationService extends Service implements NotificationReceiver.OnEventListener {
    private static final int NOTIFICATION_MEDIA_STATE = 1;
    private Notification mNotification;
    private NotificationManager mNotificationManager;
    private NotificationReceiver mReceiver;

    public static final ComponentName LinkPod = new ComponentName("com.roadrover.ipod", "com.roadrover.ipod.IapPlayerActivity");
    public static boolean isRunning = false;

    public static Intent getIntent(Context context) {
        return new Intent(context,NotificationService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        mNotification = createNotification(new TrackInfo(), Notification.PRIORITY_MIN);
        mNotificationManager.notify(NOTIFICATION_MEDIA_STATE, mNotification);

        mReceiver = new NotificationReceiver(this);
        registerReceiver(mReceiver, NotificationReceiver.getFilter());
        startForeground(NOTIFICATION_MEDIA_STATE, mNotification);
        synchronized (this)
        {
            isRunning = true;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        synchronized (this)
        {
            isRunning = false;
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Notification createNotification(TrackInfo trackInfo, int priority) {
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                Intent.makeMainActivity(LinkPod),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.album_art_empty)).getBitmap();
        Notification.Builder builder = new Notification.Builder(this)
                .setTicker(trackInfo.render())
                .setSmallIcon(R.drawable.ic_stat_hardware_phone_iphone)
                .setOngoing(true)
                .setContentText(trackInfo.getAuthorText())
                .setContentTitle(trackInfo.getTitleText())
                .setLargeIcon(bitmap)
                .setPriority(priority)
                .setContentIntent(pi);

        return builder.build();
    }

    @Override
    public void onTrackInfo(TrackInfo info) {
        int priority = isIpodAppInForeground() ? Notification.PRIORITY_MIN : Notification.PRIORITY_DEFAULT;
        mNotification = createNotification(info, priority);
        mNotificationManager.notify(NOTIFICATION_MEDIA_STATE, mNotification);
    }

    private boolean isIpodAppInForeground() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(1);

        if (services.get(0) == null) {
            return false;
        }

        ActivityManager.RunningTaskInfo task = services.get(0);
        return LinkPod.getPackageName().equals(task.topActivity.getPackageName());
    }

}
