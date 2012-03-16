package fi.dy.esav.Datalogger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fi.dy.esav.Datalogger.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DataloggerControlActivity extends Activity {
	
	TextView statusLabel;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Datalogger", "Activity created!");
        setContentView(R.layout.control);
        statusLabel = (TextView) findViewById(R.id.lbl_status);
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(
        		new Runnable() {
        			public void run() {
        				updateStatus();
        			}
        		}, 0, 1, TimeUnit.SECONDS);
        
        Button toggleButton = (Button) findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggleService();
			}
		});
        
    }
    
    private RunningServiceInfo getService() {
    	for (RunningServiceInfo s : ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
    		if (s.service.getClassName().equals(DataloggerService.class.getName())) {
    			return s;
    		}
    	}
    	return null;
    }
    
    private void updateStatus() {
    	if(getService() != null) {
    		runOnUiThread(new Runnable() {
				public void run() {
					statusLabel.setText(getString(R.string.status_prefix) + " " + getString(R.string.status_running));
				}
    		});
    	} else {
    		runOnUiThread(new Runnable() {
				public void run() {
					statusLabel.setText(getString(R.string.status_prefix) + " " + getString(R.string.status_not_running));
				}
    		});
    	}
    }
    
    private void toggleService() {
    	RunningServiceInfo s = getService();
    	if(s != null) {
    		stopService(new Intent(DataloggerService.class.getName()));
    	} else {
    		startService(new Intent(DataloggerService.class.getName()));
    	}
    }
}