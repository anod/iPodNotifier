package ipod.anodsplace.info.ipodnotifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(NotificationService.getIntent(MainActivity.this));

        Intent linkPodIntent = Intent.makeMainActivity(NotificationService.LinkPod);
        startActivity(linkPodIntent);

        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
