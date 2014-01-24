package jp.atndk.android.fuknfc2014;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
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

    private CountDownTimer mCountDownTimer;
    private int mRestSeconds;

    private String[] mDialogChoice;
    private int[] mDialogSeconds;
    private int mDialogPointer;

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
//        Button btn_write = (Button) findViewById(R.id.buttonWrite);
//        btn_write.setOnClickListener(this);
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
//        Intent intent = null;

        switch(v.getId()) {
        case R.id.buttonControl:
            affectTimer();
            break;
//        case R.id.buttonWrite:
//            intent = new Intent(this, WriteActivity.class);
//            startActivity(intent);
//            break;
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

    private void affectTimer() {
        if (mCountDownTimer != null) {
            stopTimer(mCountDownTimer);
            mCountDownTimer = null;
            return ;
        }

        startTimer();
    }
    
    private void startTimer() {
        if (mRestSeconds < 1) {
            mRestSeconds = mDialogSeconds[mDialogPointer];
            updateRestView(mRestSeconds);
            return;
        }

        mRestSeconds += 1;
        mCountDownTimer = new CountDownTimer( mRestSeconds * 1000, 1000 ){
            @Override
            public void onFinish() {
                mRestSeconds = 0;
                updateRestView(mRestSeconds);

                mVoiceManager.play(PronamaVoiceManager.KEI_FINISH_B, 100);
                stopTimer(this);
                mCountDownTimer = null;
            }

            @Override
            public void onTick(long millisUntilFinished) {
                mRestSeconds--;
                playPronamaCount(mRestSeconds);
                updateRestView(mRestSeconds);
            }
        };

        ((Button) findViewById(R.id.buttonControl)).setText(R.string.btn_stop);
        mVoiceManager.play(PronamaVoiceManager.KEI_START, 100);
        mCountDownTimer.start();
    }
    
    private void stopTimer(CountDownTimer cdt) {
        if (cdt == null) {
            return;
        }

        cdt.cancel();
        ((Button) findViewById(R.id.buttonControl)).setText(R.string.btn_start);
    }
    
    private void updateRestView(int rest_seconds) {
        TextView tv_rest = (TextView) findViewById(R.id.textView1);
        int minutes = rest_seconds / 60;
        int seconds = rest_seconds % 60;

        tv_rest.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void playPronamaCount(int rest_seconds) {
        if (rest_seconds > 10) {
            return;
        }

        switch(rest_seconds) {
        case 10:
            mVoiceManager.play(PronamaVoiceManager.KEI_JYU, 100);
            break;
        case 9:
            mVoiceManager.play(PronamaVoiceManager.KEI_KYU, 100);
            break;
        case 8:
            mVoiceManager.play(PronamaVoiceManager.KEI_HACHI, 100);
            break;
        case 7:
            mVoiceManager.play(PronamaVoiceManager.KEI_NANA, 100);
            break;
        case 6:
            mVoiceManager.play(PronamaVoiceManager.KEI_ROKU, 100);
            break;
        case 5:
            mVoiceManager.play(PronamaVoiceManager.KEI_GO, 100);
            break;
        case 4:
            mVoiceManager.play(PronamaVoiceManager.KEI_YON, 100);
            break;
        case 3:
            mVoiceManager.play(PronamaVoiceManager.KEI_SAN, 100);
            break;
        case 2:
            mVoiceManager.play(PronamaVoiceManager.KEI_NI, 100);
            break;
        case 1:
            mVoiceManager.play(PronamaVoiceManager.KEI_ICHI, 100);
            break;
        case 0:
            mVoiceManager.play(PronamaVoiceManager.KEI_ZERO, 100);
            break;
        }
    }
}
