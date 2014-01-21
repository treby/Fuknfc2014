package jp.atndk.android.fuknfc2014;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    private PronamaVoiceManager mVoiceManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVoiceManager = new PronamaVoiceManager(this);
        
        // set listeners
        ImageView img_pronama = (ImageView) findViewById(R.id.imageView1);
        img_pronama.setOnClickListener(this);
        Button btn_read = (Button) findViewById(R.id.button1);
        btn_read.setOnClickListener(this);
        Button btn_write = (Button) findViewById(R.id.button2);
        btn_write.setOnClickListener(this);
        
    }

    @Override
    protected void onDestroy() {
        if (mVoiceManager != null) {
            mVoiceManager.release();
            mVoiceManager = null;
        }
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
        Intent intent = null;

        switch(v.getId()) {
        case R.id.button1:
            intent = new Intent(this, ReadActivity.class);
            startActivity(intent);
            break;
        case R.id.button2:
            intent = new Intent(this, WriteActivity.class);
            startActivity(intent);
            break;
        case R.id.imageView1:
            mVoiceManager.play(PronamaVoiceManager.KEI_VOICE_029, 100);
            break;
        default:
            Toast.makeText(this, "Nothing hit.", Toast.LENGTH_LONG).show();
            break;
        }
    }
}
