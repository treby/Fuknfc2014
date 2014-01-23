package jp.atndk.android.fuknfc2014;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener, DialogInterface.OnClickListener {
    private PronamaVoiceManager mVoiceManager;
    private Random mRandom;
    private int[] mPronamaRandomVoices;

    private String[] mDialogChoice;
    private int[] mDialogSeconds;
    private int mDialogPointer;
    private int mRestSeconds;

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
        mDialogChoice = new String[] { "1:30", "3:00", "5:00", "8:00", "10:00", "15:00" };
        mDialogSeconds = new int[] { 90, 180, 300, 480, 600, 900 };
        mDialogPointer = 1;
        mRestSeconds = mDialogSeconds[mDialogPointer];
        updateRestView(mRestSeconds);

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

    // for dialog
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
        case AlertDialog.BUTTON_POSITIVE:
            mRestSeconds = mDialogSeconds[mDialogPointer];
            updateRestView(mRestSeconds);
            break;
        case AlertDialog.BUTTON_NEGATIVE:
            // do nothing
            break;
        default:
            mDialogPointer = which;
            break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.settings_main:
            showTimerSettingDialog();
            break;
        }
        return false;
    }

    private void showTimerSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("LTéûä‘Çê›íË");

        builder.setSingleChoiceItems(mDialogChoice, mDialogPointer, this);
        builder.setPositiveButton("OK", this );
        builder.setNegativeButton("Cancel", this );

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateRestView(int rest_seconds) {
        TextView tv_rest = (TextView) findViewById(R.id.textView1);
        int minutes = rest_seconds / 60;
        int seconds = rest_seconds % 60;

        tv_rest.setText(String.format("%02d:%02d", minutes, seconds));
    }
    
}
