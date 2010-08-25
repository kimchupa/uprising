package uprising.android.twitter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TwitterDB extends SQLiteOpenHelper {
	
	Context context;
	
	static final String		DB_NAME			= "twitter.db";
	static final int		DB_VERSION		= 5;
	
	static final String		TABLE_NAME		= "twittertb";
	
	//Column Name
	static final String 	TWITTER_IDX				= BaseColumns._ID;
	static final String 	TWITTER_CREATED_AT 		= "created_at";
	static final String 	TWITTER_USER 			= "user";
	static final String 	TWITTER_TIMELINE		= "timeline";
	static final String 	TWITTER_PHOTO			= "photo";
	static final String		TWITTER_MSGTYPE			= "msgtype";
	
	//Column Index
	static final int 		TWI_CREATE_AT_COL 		= 1;
	static final int 		TWI_USER_COL 			= 2;
	static final int 		TWI_TEXT_COL 			= 3;
	static final int 		TWI_PHOTO_COL 			= 4;
	static final int 		TWI_MSGTYPE_COL 		= 5;
	
	public TwitterDB(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE table " + TABLE_NAME + " ( _id INTEGER NOT NULL PRIMARY KEY, created_at INTEGER, user TEXT, timeline TEXT, photo TEXT, msgtype INTEGER);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME + ";");
	}
	
	public static ContentValues twitter2db(String created_at, String user, String timeline, String photo, String msgtype) {
		ContentValues cv = new ContentValues();
		cv.put(TWITTER_CREATED_AT, created_at);
		cv.put(TWITTER_USER, user);
		cv.put(TWITTER_TIMELINE, timeline);
		cv.put(TWITTER_PHOTO, photo);
		cv.put(TWITTER_MSGTYPE, msgtype);
		return cv;
	}

}
