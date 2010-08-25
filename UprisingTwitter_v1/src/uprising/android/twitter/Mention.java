package uprising.android.twitter;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Mention extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mention);

		TextView tv = (TextView)findViewById(R.id.mention);
		tv.setText("Mention");
	}

}
