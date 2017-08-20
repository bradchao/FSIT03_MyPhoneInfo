package tw.brad.app.helloworld.myphoneinfo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

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

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }
}
