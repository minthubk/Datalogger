package fi.dy.esav.Datalogger;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

public class Settings {
	public String filename;
	
	public boolean log_Acc;
	public boolean log_GPS;
	
	private Context context;
	
	public Settings(Context context) {
		this.context=context;
	}
	
	public void read() {
		SharedPreferences prefs = context.getSharedPreferences("Datalogger.DataloggerSettings",0);
		
		filename = prefs.getString("filename", 
				                   Environment.getExternalStorageDirectory().getAbsolutePath()
					               + File.separator + "data.txt");
		log_Acc = prefs.getBoolean("log_Acc", false);
		log_GPS = prefs.getBoolean("log_GPS", false);
	}
	
	public void write() {
		SharedPreferences prefs = context.getSharedPreferences("Datalogger.DataloggerSettings",0);
		Editor editor = prefs.edit();
		
		editor.putString("filename", filename);
		editor.putBoolean("log_Acc", log_Acc);
		editor.putBoolean("log_GPS", log_GPS);
		
		editor.apply();
		
	}
}
