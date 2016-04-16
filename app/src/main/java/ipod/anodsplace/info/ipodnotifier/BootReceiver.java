package ipod.anodsplace.info.ipodnotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author algavris
 * @date 13/04/2016.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(NotificationService.getIntent(context));
    }
}
