package uprising.android.twitter;

import java.io.File;



import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

public class UprisingTwitter extends TabActivity {
    
	private final String PATH = Environment.getDataDirectory() + "/data/uprising.android.twitter";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(new File(PATH + "/data").exists() == false) {
        	File dataPath = new File(PATH + "/data");
        	dataPath.mkdirs();
        }
        
        TabHost th = getTabHost();
        
        Drawable timelineicon = getResources().getDrawable(R.drawable.ic_tab_timeline_active);
        Drawable Mentionicon = getResources().getDrawable(R.drawable.ic_tab_replies_active);
        Drawable DMicon = getResources().getDrawable(R.drawable.ic_tab_messages_active);
              
        th.addTab(th.newTabSpec("tab1")
        			.setIndicator("Timeline", timelineicon)
        			.setContent(new Intent(this, Timeline.class)));
        
        th.addTab(th.newTabSpec("tab2")
        			.setIndicator("Replies", Mentionicon)
        			.setContent(new Intent(this, Mention.class)));
        
        th.addTab(th.newTabSpec("tab3")
        			.setIndicator("DM", DMicon)
        			.setContent(new Intent(this, Compose.class)));
    }
    
    public boolean onCreateOptionsMenu (Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, 1, 0, "Compose").setIcon(R.drawable.ic_menu_compose);
    	menu.add(0, 2, 0, "Profile").setIcon(R.drawable.ic_menu_accounts);
    	
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case 1:
			startActivity(new Intent(this, Compose.class));
    		return true;
    	case 2:
    		Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show();
    		return true;
    	}
    	
    	
    	return false;
    }
    
    @Override
    public void onResume() {
      super.onResume();
      // Start the UpdaterService
      startService(new Intent(this, UpdateService.class));
    }

    @Override
    public void onDestroy() {
      super.onDestroy();
      // Stop the UpdaterService
      stopService(new Intent(this, UpdateService.class));
    }
}