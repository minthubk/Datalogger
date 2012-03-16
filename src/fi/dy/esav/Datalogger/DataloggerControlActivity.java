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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class DataloggerControlActivity extends Activity {
	
	TextView statusLabel;
	CheckBox chkbox_acc;
	CheckBox chkbox_gps;
	EditText tf_filename;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Datalogger", "Activity created!");
        setContentView(R.layout.control);
        
        statusLabel = (TextView) findViewById(R.id.lbl_status);
        Button toggleButton = (Button) findViewById(R.id.toggleButton);
        
        chkbox_acc = (CheckBox) findViewById(R.id.chkbox_acc);
        chkbox_gps = (CheckBox) findViewById(R.id.chkbox_gps);
        tf_filename = (EditText) findViewById(R.id.tf_filename);
        
        Settings settings = new Settings(this);
        settings.read();
        
        chkbox_acc.setChecked(settings.log_Acc);
        chkbox_gps.setChecked(settings.log_GPS);
        tf_filename.setText(settings.filename);
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(
        		new Runnable() {
        			public void run() {
        				updateStatus();
        			}
        		}, 0, 1, TimeUnit.SECONDS);
        
        
        toggleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggleService();
			}
		});
        
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Settings settings = new Settings(this);
		
		settings.log_Acc = chkbox_acc.isChecked();
		settings.log_GPS = chkbox_gps.isChecked();
		settings.filename = tf_filename.getText().toString();
		
		settings.write();
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