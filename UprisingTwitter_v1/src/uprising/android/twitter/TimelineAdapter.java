package uprising.android.twitter;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class TimelineAdapter extends SimpleCursorAdapter {
	
	static final String[] from = {	TwitterDB.TWITTER_CREATED_AT,
									TwitterDB.TWITTER_USER,
									TwitterDB.TWITTER_TIMELINE,
	};
	
	static final int[] 		to = {	R.id.textCreatedAt,
									R.id.textUser,
									R.id.textTimeline,
	};
	
	public TimelineAdapter(Context context, Cursor c) {
		super(context, R.layout.timelineadapter, c, from, to);
	}
	
	public void bindView(View row, Context context, Cursor cursor) {
		super.bindView(row, context, cursor);
		
		String userPhotoUrl = cursor.getString(cursor.getColumnIndex(TwitterDB.TWITTER_PHOTO));
		String userID = cursor.getString(cursor.getColumnIndex(TwitterDB.TWITTER_USER));
		ImageView userPhoto = (ImageView) row.findViewById(R.id.userPhoto);
		
		int idx  = userPhotoUrl.lastIndexOf('/');
		String localimage = userID + "_" + userPhotoUrl.substring(idx + 1);
		String path = Environment.getDataDirectory().getAbsolutePath() + "/data/uprising.android.twitter/data/" + localimage;
		
		if(new File(path).exists() == false) {
			UpdateService us = new UpdateService();
			us.DownloadProfileImage(userPhotoUrl, path);
		}
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		userPhoto.setImageBitmap(bitmap);
		
		long createdAt = cursor.getLong(cursor.getColumnIndex(TwitterDB.TWITTER_CREATED_AT));
		TextView createdAtText = (TextView) row.findViewById(R.id.textCreatedAt);
		createdAtText.setText(DateUtils.getRelativeTimeSpanString(createdAt));

	}
}
