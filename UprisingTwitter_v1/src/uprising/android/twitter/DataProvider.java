package uprising.android.twitter;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public class DataProvider extends ContentProvider {
	
	static final Uri TWITTER_URI = Uri.parse("content://uprising.android.twitter/" + TwitterDB.TABLE_NAME);
	
	SQLiteDatabase db;
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String sql;
		sql = "DELETE from " + TwitterDB.TABLE_NAME;
		db.execSQL(sql);
		return 1;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long row = db.insert(TwitterDB.TABLE_NAME, null, values);
		if(row > 0) {
			
			Uri notiUri = ContentUris.withAppendedId(TWITTER_URI, row);
			getContext().getContentResolver().notifyChange(notiUri, null);
			return notiUri;
		}
		
		return null;
	}

	@Override
	public boolean onCreate() {
		TwitterDB dbhelper = new TwitterDB(this.getContext());
		db = dbhelper.getWritableDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}
