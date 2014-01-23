package jp.atndk.android.fuknfc2014;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WriteActivity extends Activity implements OnClickListener {

    private NfcAdapter mNfcAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        
        Button btn_write = (Button) findViewById(R.id.button1);
        btn_write.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            enableSearchTag();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        disableSearchTag();
    }

    private void enableSearchTag() {
        Toast.makeText(this, "Searching", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, this.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, intent.getAction(), Toast.LENGTH_LONG).show();
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        writeTag(tag);
    }

    public void writeTag(Tag tag) {
        try {
            List<String> techList = Arrays.asList(tag.getTechList());
            if (techList.contains(Ndef.class.getName())) {
                Ndef ndef = Ndef.get(tag);
                writeNdef(ndef);
            }
        } catch (RuntimeException e) {
            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT);
        }
        Toast.makeText(this, "Complete!!", Toast.LENGTH_LONG).show();
    }

    public void writeNdef(Ndef ndef) {
        EditText etWrite = (EditText) findViewById(R.id.editText1);
        byte[] strBytes = etWrite.getText().toString().getBytes();
        byte[] payload = new byte[strBytes.length + 1];
        payload[0] = 0;
        System.arraycopy(strBytes, 0, payload, 1, strBytes.length);
        
        NdefMessage msg = new NdefMessage(new NdefRecord[] {
                NdefRecord.createUri("http://www.atelier-nodoka.net/"),
                new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload)
        });

        try {
            ndef.connect();
            ndef.writeNdefMessage(msg);
        } catch (IOException e) {
            throw new RuntimeException("failure", e);
        } catch (FormatException e) {
            throw new RuntimeException("Format failure", e);
        } finally {
            try {
                ndef.close();
            } catch (IOException e) {
            }
        }
        
    }
    
    private void disableSearchTag() {
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.write, menu);
        return true;
    }
}
