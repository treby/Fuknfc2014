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

    private static final int[] VoiceList = {
        R.raw.kei_voice_008_phrase1,    // ���͂悤
        R.raw.kei_voice_008_phrase2,    // �������ǂ�ǂ�R�[�h�����Ă������I
        R.raw.kei_voice_015_phrase1,    // ���A���d
        R.raw.kei_voice_015_phrase2,    // ��낵����
        R.raw.kei_voice_029,            // ������
        R.raw.kei_voice_056,            // �X�^�[�g�I
        R.raw.kei_voice_060,            // ���イ�`��傤�`
        R.raw.kei_voice_061_a,          // �����A����I A
        R.raw.kei_voice_061_b,          // �����A����I B
        R.raw.kei_voice_068,            // ����
        R.raw.kei_voice_069,            // ����
        R.raw.kei_voice_070,            // ��
        R.raw.kei_voice_071,            // ����
        R.raw.kei_voice_073,            // ���
        R.raw.kei_voice_074,            // ��
        R.raw.kei_voice_075,            // �낭
        R.raw.kei_voice_076,            // �Ȃ�
        R.raw.kei_voice_077,            // �͂�
        R.raw.kei_voice_079,            // ���イ
        R.raw.kei_voice_080,            // ���イ
        R.raw.kei_voice_081,            // �͂��A����[���[
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
