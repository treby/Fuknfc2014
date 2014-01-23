package jp.atndk.android.fuknfc2014;

import java.util.Random;

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
    private Random mRandom;
    private int[] mPronamaRandomVoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVoiceManager = new PronamaVoiceManager(this);
        mRandom = new Random();
        mPronamaRandomVoices = new int[] {
                PronamaVoiceManager.KEI_DONDON_CODE,
                PronamaVoiceManager.KEI_EIXTSU,
                PronamaVoiceManager.KEI_WATASHI,
                PronamaVoiceManager.KEI_YOROSHIKU,
        };

        // set listeners
        ImageView img_pronama = (ImageView) findViewById(R.id.imageViewPronama);
        img_pronama.setOnClickListener(this);
        Button btn_control = (Button) findViewById(R.id.buttonControl);
        btn_control.setOnClickListener(this);
        Button btn_write = (Button) findViewById(R.id.buttonWrite);
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
        case R.id.buttonControl:
            mVoiceManager.play(PronamaVoiceManager.KEI_START, 100);
            break;
        case R.id.buttonWrite:
            intent = new Intent(this, WriteActivity.class);
            startActivity(intent);
            break;
        case R.id.imageViewPronama:
            int choice = mRandom.nextInt(mPronamaRandomVoices.length);
            mVoiceManager.play(mPronamaRandomVoices[choice], 100);
            break;
        default:
            Toast.makeText(this, "Nothing hit.", Toast.LENGTH_LONG).show();
            break;
        }
    }
}
