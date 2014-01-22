package jp.atndk.android.fuknfc2014;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class PronamaVoiceManager {
    public static final int KEI_VOICE_029 = 0;

    private static final int[] VoiceList = {
        R.raw.kei_voice_029
    };
    
    private SoundPool mSoundPool;
    
    private int mVoiceTable[] = new int[VoiceList.length];
    
    public PronamaVoiceManager(Context context) {
        mSoundPool = new SoundPool(VoiceList.length, AudioManager.STREAM_MUSIC, 0);
        
        for (int i = 0; i < VoiceList.length; i++) {
            mVoiceTable[i] = mSoundPool.load(context, VoiceList[i], 1);
        }
    }
    
    public void play(int no, int vol) {
        if (no < 0 || no >= mVoiceTable.length) {
            return;
        }
        
        float fvol = vol / 100;
        mSoundPool.play(mVoiceTable[no], fvol, fvol, 0, 0, 1.0f);
    }
    
    public void release() {
        for (int i = 0; i < mVoiceTable.length; i++) {
            mSoundPool.unload(mVoiceTable[i]);
        }
        mSoundPool.release();
    }
}
