package jp.atndk.android.fuknfc2014;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set listeners
        Button btn_read = (Button) findViewById(R.id.button1);
        btn_read.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // for button
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button1:
			Intent intent = new Intent(this, ReadActivity.class);
			startActivity(intent);
			break;
		default:
			Toast.makeText(this, "Nothing hit.", Toast.LENGTH_LONG).show();
			break;
		}
	}
}
