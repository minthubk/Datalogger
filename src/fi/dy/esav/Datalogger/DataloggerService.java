/**
 * 
 */
package fi.dy.esav.Datalogger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Oppilas
 *
 */
public class DataloggerService extends Service {

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	ScheduledFuture schedulerHandle;
	ScheduledExecutorService scheduler;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		scheduler = Executors.newScheduledThreadPool(1);
        schedulerHandle = 
        	scheduler.scheduleAtFixedRate(
        		new Runnable() {
        			public void run() {
        				Log.d("Datalogger", "service task");
        			}
        		}, 0, 1, TimeUnit.SECONDS);
        super.onCreate();
        return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//schedulerHandle.cancel(false);
		scheduler.shutdown();
		Log.d("Datalogger", "Service destroyed!");
	}

}
