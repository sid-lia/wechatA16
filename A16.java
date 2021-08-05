package com.sid.templateproject.wx.services.WxUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.sid.templateproject.utils.LogUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.HashMap;

public class A16 {

    public static String getWxA16(Context context) {
        String android_id=Settings.Secure.getString(context.getContentResolver(), "android_id");
        LogUtils.i("android_id:"+android_id);
        String imeiKey=getImeiKey(context);
        String phoneInfo = Build.MANUFACTURER + Build.MODEL + cpuInfo();
        StringBuilder sb=new StringBuilder();
        sb.append(android_id);
        sb.append(imeiKey);
        sb.append(phoneInfo);
        String keyInfo=sb.toString();
        String a16="";
        try {
            a16 = "A" + o(keyInfo.getBytes("utf-8")).substring(0, 15);
        }
        catch(Exception v2_2) {
            v2_2.printStackTrace();
        }
        LogUtils.d("hmc", keyInfo);
        return a16;
    }

    public static String getImeiKey(Context context){
        String imei=getImei(context);
        if (imei==null){
            return null;
        }
        String str= "A" + imei + "123456789ABCDEF";
        return str.substring(0, 15);
    }

    public static String getImei(Context context) {
        try {
            @SuppressLint("MissingPermission") String v0_1 = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            return v0_1 == null ? "" : v0_1;
        }
        catch(Throwable v0) {
            return "";
        }
    }

    private static String cpuInfo() {
        HashMap map = getCpu();
        return ": " + map.get("Features") + ": " + map.get("Processor") + ": " + map.get("CPU architecture") + ": " + map.get("Hardware") + ": " + map.get("Serial");
    }

    public static HashMap getCpu()  {
        if(!Environment.getExternalStorageState().equals("mounted")) {
            return null;
        }
        BufferedReader v1 = null;
        HashMap v3 = new HashMap();
        try {
            v1 = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/cpuinfo"), "UTF-8"));
            while(true) {
                String v0_2 = v1.readLine();
                if(v0_2 == null) {
                    break;
                }
                if (!StringUtils.isEmpty(v0_2)){
                    String[] v0_3 = v0_2.split(":", 2);
                    if (v0_3.length>1){
                        String v2 = v0_3[0].trim();
                        String v0_4 = v0_3[1].trim();
                        if(v3.get(v2) == null) {
                            v3.put(v2, v0_4);
                        }
                    }
                }
            }
        }catch(IOException v0_1) {
            try {
                LogUtils.e("CpuFeatures", v0_1, "getCpu() failed.", new Object[0]);
                v1.close();
            }catch(Throwable v0) {
                v0.printStackTrace();
            }
            return v3;
        }catch(Throwable v0) {
            throw v0;
        }
        return v3;
    }

    public static final String o(byte[] bArr) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            byte[] digest = instance.digest();
            int length = digest.length;
            char[] cArr2 = new char[(length * 2)];
            int i = 0;
            int i2 = 0;
            while (i < length) {
                byte b2 = digest[i];
                int i3 = i2 + 1;
                cArr2[i2] = cArr[(b2 >>> 4) & 15];
                cArr2[i3] = cArr[b2 & 15];
                i++;
                i2 = i3 + 1;
            }
            return new String(cArr2);
        }
        catch(Exception v0) {
            return null;
        }
    }
}
