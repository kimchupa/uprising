package uprising.android.twitter;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class UpdateService extends Service {

	static final String 	NEW_UPDATE		= "NEW_UPDATE";
	
	static int newdata;
	static int prevdata;

	Context context;

	SQLiteDatabase 		db;
	TwitterDB 			dbhelper;
	Handler 			handler;
	Updater 			updater;

	@Override
	public void onCreate() {
		super.onCreate();

		handler 	= new Handler();
		dbhelper	= new TwitterDB(this);
		db 			= dbhelper.getWritableDatabase();
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		updater = new Updater();
		handler.post(updater);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(updater);
		db.close();
	}

	public void DownloadProfileImage(String imageURL, String fileName) {  //this is the downloader method
		try {
			URL url = new URL(imageURL); //you can write here any link
			File file = new File(fileName);


			/* Open a connection to that URL. */
			URLConnection ucon = url.openConnection();

			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			
			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			ByteArrayBuffer baf = new ByteArrayBuffer(ucon.getContentLength());
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			byte[] chupa = baf.toByteArray();
			
			FileOutputStream fos = new FileOutputStream(file);
			Bitmap bitmap = BitmapFactory.decodeByteArray(chupa, 0, chupa.length);
			
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			int newwidth = 45;
			int newheight = 45; 
			
			float scaleWidth = ((float) newwidth) / width;
			float scaleHeight = ((float) newheight) / height;
 			
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			
			Bitmap resized = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
			resized.compress(Bitmap.CompressFormat.PNG, 90, fos);
			fos.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	class Updater implements Runnable {

		static final int NOTIFICATION_ID = 47;
		static final long DELAY = 1000L;
		@Override
		public void run() {

			try{
				Cursor cursor = db.query(TwitterDB.TABLE_NAME, null, null, null, null, null, null);
				newdata = cursor.getCount();

				if(newdata > prevdata) {
					sendBroadcast(new Intent(NEW_UPDATE));
					prevdata = cursor.getCount();
				}
			}			
			catch (SQLException e) {
				Toast.makeText(getApplicationContext(), "UP: " + e.getMessage(), Toast.LENGTH_LONG).show();
			}
			handler.postDelayed(this, DELAY);
		}
	}
}
