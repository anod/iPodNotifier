package ipod.anodsplace.info.ipodnotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @author algavris
 * @date 12/04/2016.
 */

public class NotificationReceiver extends BroadcastReceiver {

    public static final String ACTION_SERVICE = "com.roadrover.ipod.service";
    public static final String ACTION_RESUME = "com.roadrover.ipod.resume";
    public static final String ACTION_PAUSE = "com.roadrover.ipod.pause";
    public static final String ACTION_EXIT = "com.roadrover.ipod.exit";

    private OnEventListener mEventListener;
    private String mTrackName = "";
    private String mArtistName = "";

    public interface OnEventListener {
        void onTrackInfo(TrackInfo info);
    }

    public NotificationReceiver(OnEventListener eventListener) {
        mEventListener = eventListener;
    }

    public static IntentFilter getFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SERVICE);
        intentFilter.addAction(ACTION_RESUME);
        intentFilter.addAction(ACTION_PAUSE);
        intentFilter.addAction(ACTION_EXIT);
        return intentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_SERVICE.equals(action)) {
            if (intent.hasExtra("reset_media_info")) {
                // mEventListener.onTrackInfo(new TrackInfo());
            }
            if (intent.hasExtra("track_name")) {
                String trackName = intent.getStringExtra("track_name");
                if (trackName != null) {
                    mTrackName = trackName;
                    mEventListener.onTrackInfo(new TrackInfo(mArtistName, mTrackName));
                }
            }
            if (intent.hasExtra("artist_name")) {
                String artistName = intent.getStringExtra("artist_name");
                if (artistName != null) {
                    mArtistName = artistName;
                    mEventListener.onTrackInfo(new TrackInfo(mArtistName, mTrackName));
                }
            }
        }
//        } else {
//            if (action.equals(ACTION_RESUME)) {
//                return;
//            }
//            if (action.equals(ACTION_PAUSE)) {
//                return;
//            }
//            if (action.equals(ACTION_EXIT)) {
//                return;
//            }
//        }
    }

}
