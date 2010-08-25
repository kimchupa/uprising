package uprising.android.twitter;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class Compose extends Activity {
	static final Uri TWITTER_URI = Uri.parse("content://uprising.android.app/twi_table");
	
	EditText editText;
	InputMethodManager imm;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compose);
		
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		
		editText = (EditText) findViewById(R.id.compose_edittext);
		Button sendButton 	= ((Button)findViewById(R.id.compose_sendbutton));
		Button cancelButton = ((Button)findViewById(R.id.compose_cancelbutton));
		
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ContentValues cv = new ContentValues();
				ContentResolver cr = getContentResolver();
				cv.put("twi_timeline", editText.getText().toString());
				cr.insert(TWITTER_URI, cv);
				finish();
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
			}
		});
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
				
			}
			
		});
	}
	
	public void onDestroy() {
		super.onDestroy();
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		
	}

}
