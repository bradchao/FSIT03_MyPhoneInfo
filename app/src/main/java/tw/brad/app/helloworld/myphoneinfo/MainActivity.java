package tw.brad.app.helloworld.myphoneinfo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TelephonyManager tmgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED ) {
            Log.i("brad", "B");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_PHONE_NUMBERS,
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.READ_CALL_LOG
                    },
                    1);
        }else{
            Log.i("brad", "A");
            init();
        }

    }
    private void init(){
        tmgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String deviceid = tmgr.getDeviceId();
        Log.i("brad", deviceid);
        String n1 = tmgr.getLine1Number();
        String n2 = tmgr.getSimSerialNumber();
        String n3 = tmgr.getSubscriberId();
        Log.i("brad", "n1 = " + n1);
        Log.i("brad", "n2 = " + n2);
        Log.i("brad", "n3 = " + n3);

        MyCallListener mcl = new MyCallListener();
        tmgr.listen(mcl, PhoneStateListener.LISTEN_CALL_STATE);
        //tmgr.listen(mcl, PhoneStateListener.LISTEN_SERVICE_STATE);

        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER},null,null,null);
        Log.i("brad", "count:" + c.getCount());
        while (c.moveToNext()){
            String name = c.getString(0); //c.getString(c.getColumnIndex(""));
            String tel = c.getString(1); //c.getString(c.getColumnIndex(""));
            Log.i("brad", name + ":" + tel);
        }


    }

    private class MyCallListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.i("brad", "idle");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i("brad", "ring:" + incomingNumber);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.i("brad", "offhook:" + incomingNumber);
                    break;
            }

        }

        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            super.onServiceStateChanged(serviceState);
            Log.i("brad", "service:" + serviceState);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }
}
