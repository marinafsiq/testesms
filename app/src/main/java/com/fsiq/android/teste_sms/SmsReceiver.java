package com.fsiq.android.teste_sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    private String msg, phoneNum = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intented received: " + intent.getAction());
        if(intent.getAction() ==  SMS_RECEIVED){
            Bundle dataBundle = intent.getExtras();
            if(dataBundle!=null){
                Object[] mypdu = (Object[]) dataBundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[mypdu.length];

                for(int i=0; i<mypdu.length; i++){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        String format = dataBundle.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[])mypdu[i], format);
                    } else{
                        messages[i] = SmsMessage.createFromPdu((byte[])mypdu[i]);
                    }
                    msg = messages[i].getMessageBody();
                    phoneNum = messages[i].getOriginatingAddress();
                }
                Toast.makeText(context, "Message: " + msg + "\n PhoneNum: " + phoneNum, Toast.LENGTH_LONG).show();
            }
        }
    }
}
