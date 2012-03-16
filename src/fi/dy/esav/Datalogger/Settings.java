package fi.dy.esav.Datalogger;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

public class Settings {
	public String file_path;
	public String file_name;
	
	public boolean log_Acc;
	public boolean log_GPS;
	
	private Context context;
	private boolean loaded;
	
	public Settings(Context context) {
		this.context=context;
	}
	
	public void read() {
		SharedPreferences prefs = context.getSharedPreferences("Datalogger.DataloggerSettings",0);
		
		file_path = prefs.getString("file_path", Environment.getExternalStorageDirectory().getAbsolutePath());
		file_path = prefs.getString("file_path", Environment.getExternalStorageDirectory().getAbsolutePath());
		
		log_Acc = prefs.getBoolean("file_path", false);
		log_GPS = prefs.getBoolean("file_path", false);
	}
	
	public void write() {
		SharedPreferences prefs = context.getSharedPreferences("Datalogger.DataloggerSettings",0);
		Editor editor = prefs.edit();
		
		editor.putString("file_path", file_path);
		editor.putString("file_name", file_name);
		
		editor.putBoolean("log_Acc", log_Acc);
		editor.putBoolean("log_GPS", log_GPS);
		
		editor.apply();
		
	}
}
