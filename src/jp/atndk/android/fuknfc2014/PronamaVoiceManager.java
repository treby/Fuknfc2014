package jp.atndk.android.fuknfc2014;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class PronamaVoiceManager {
    public static final int KEI_OHAYO = 0;
    public static final int KEI_DONDON_CODE = 1;
    public static final int KEI_WATASHI = 2;
    public static final int KEI_YOROSHIKU = 3;
    public static final int KEI_EIXTSU = 4;
    public static final int KEI_START = 5;
    public static final int KEI_FINISH_A = 6;
    public static final int KEI_OWARI_A = 7;
    public static final int KEI_OWARI_B = 8;
    public static final int KEI_ZERO = 9;
    public static final int KEI_ICHI = 10;
    public static final int KEI_NI = 11;
    public static final int KEI_SAN = 12;
    public static final int KEI_YON = 13;
    public static final int KEI_GO = 14;
    public static final int KEI_ROKU = 15;
    public static final int KEI_NANA = 16;
    public static final int KEI_HACHI = 17;
    public static final int KEI_KYU = 18;
    public static final int KEI_JYU = 19;
    public static final int KEI_FINISH_B = 20;
    public static final int KEI_NN = 21;
    public static final int KEI_OTTO = 22;

    private static final int[] VoiceList = {
        R.raw.kei_voice_008_phrase1,    // おはよう
        R.raw.kei_voice_008_phrase2,    // 今日もどんどんコード書いていこう！
        R.raw.kei_voice_015_phrase1,    // 私、暮井慧
        R.raw.kei_voice_015_phrase2,    // よろしくね
        R.raw.kei_voice_029,            // えいっ
        R.raw.kei_voice_056,            // スタート！
        R.raw.kei_voice_060,            // しゅう〜りょう〜
        R.raw.kei_voice_061_a,          // おわり、だよ！ A
        R.raw.kei_voice_061_b,          // おわり、だよ！ B
        R.raw.kei_voice_068,            // ぜろ
        R.raw.kei_voice_069,            // いち
        R.raw.kei_voice_070,            // に
        R.raw.kei_voice_071,            // さん
        R.raw.kei_voice_073,            // よん
        R.raw.kei_voice_074,            // ご
        R.raw.kei_voice_075,            // ろく
        R.raw.kei_voice_076,            // なな
        R.raw.kei_voice_077,            // はち
        R.raw.kei_voice_079,            // きゅう
        R.raw.kei_voice_080,            // じゅう
        R.raw.kei_voice_081,            // はい、しゅーりょー
        R.raw.kei_voice_091_phrase1,    // ん？
        R.raw.kei_voice_097_phrase1,    // おっと
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
