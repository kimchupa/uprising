package uprising.android.twitter;



import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class Timeline extends Activity {
	
	ListView listTimeline;	
	TwitterDB dbhelper;
	SQLiteDatabase db;	
	Cursor cursor;	
	TimelineAdapter adapter;
	BroadcastReceiver twitterStatusReceiver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		
		listTimeline = (ListView) findViewById(R.id.listTimeline);
		
		dbhelper = new TwitterDB(this);
		db = dbhelper.getReadableDatabase();

		cursor = db.query(TwitterDB.TABLE_NAME, null, null, null, null, null, TwitterDB.TWITTER_CREATED_AT + " DESC");
		startManagingCursor(cursor);
		adapter = new TimelineAdapter(this, cursor);
		listTimeline.setScrollbarFadingEnabled(true);
		listTimeline.setAdapter(adapter);
		
		twitterStatusReceiver = new TwitterStatusReceiver();
		registerReceiver(twitterStatusReceiver, new IntentFilter(UpdateService.NEW_UPDATE));
		
	}
	public void onResume() {
		super.onResume();
	    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    notificationManager.cancel(UpdateService.Updater.NOTIFICATION_ID);
	}

	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(twitterStatusReceiver);
	}
	
	class TwitterStatusReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			cursor.requery();
		}		
	}
}
