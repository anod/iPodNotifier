package ipod.anodsplace.info.ipodnotifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements NotificationReceiver.OnEventListener {

    private TextView mTextView;
    private TextView mStatusView;
    private NotificationReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(android.R.id.text1);
        mStatusView = (TextView) findViewById(android.R.id.text2);

        Button button = (Button) findViewById(android.R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(NotificationService.getIntent(MainActivity.this));
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateServiceStatus();
                    }
                }, 1000);
            }
        });

        Button buttonOpen = (Button) findViewById(android.R.id.button2);
        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkPodIntent = Intent.makeMainActivity(NotificationService.LinkPod);
                startActivity(linkPodIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new NotificationReceiver(this);
        registerReceiver(mReceiver, NotificationReceiver.getFilter());
        updateServiceStatus();
    }

    private void updateServiceStatus() {
        mStatusView.setText(NotificationService.isRunning ? "Service: Running" : "Service: Stopped");
    }

    @Override
    protected void onPause() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        super.onPause();
    }

    @Override
    public void onTrackInfo(TrackInfo info) {
        mTextView.setText(info.render());
    }
}
