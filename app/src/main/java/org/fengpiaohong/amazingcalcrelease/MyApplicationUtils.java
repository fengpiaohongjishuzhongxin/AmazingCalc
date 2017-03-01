package org.fengpiaohong.amazingcalcrelease;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by 乘耀 on 2017/2/18.
 */

public class MyApplicationUtils extends Application {
    private static String devicesStr = null;
    private static String dK = null;
    private static Context context;

    @Override
    public void onCreate() {
        //super.onCreate();
        context = getApplicationContext();
    }

    static String getDevicesStr() {
        if (devicesStr == null) {
            TelephonyManager phm;
            phm = (TelephonyManager) context.getSystemService(Application.TELEPHONY_SERVICE);
            devicesStr = phm.getDeviceId();
            System.out.println("-------------GetDevicesIDOK----------------" + devicesStr);
            //devicesStr=Integer.toHexString(Integer.parseInt(devicesStr));
            devicesStr = Long.toHexString(Long.parseLong(devicesStr.trim(), 10)).toUpperCase();
            devicesStr = devicesStr.substring(2, 8);
        }
        return devicesStr;
    }

    public static String getdK() {
        return dK;
    }

    public static void setdK(String dK) {
        MyApplicationUtils.dK = dK;
    }

    public static Context getContext() {
        return context;
    }


    //对double型进行精度等格式化
    public static double formatDouble(double d, int scale) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留多少位小数

        nf.setMaximumFractionDigits(scale);
            // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);
            // 是否用逗号隔开 例如1487508738 为true时 1,487,508,738 为false时不用逗号隔开
        nf.setGroupingUsed(false);

        return Double.valueOf(nf.format(d));
    }


    //所有类型数字转化为字符串
    public static String trans(Object number){
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setGroupingUsed(false);
        return nf.format(number);
    }

    //
    public static int hexToInt(String hex){
        return Integer.parseInt(hex,16);
    }
    public static long hexToLong(String hex){
        return Long.parseLong(hex,16);
    }

    public static String intToHex(int x){
        return Integer.toHexString(x);
    }

    public static String longToHex(long x){
        return Long.toHexString(x);
    }

    //long类型转成byte数组
    public static byte[] longToByte(long number){
        long temp = number;
        byte[] b =new byte[8];
        for(int i =0; i < b.length; i++){
            b[i]= Long.valueOf(temp &0xff).byteValue();//
            //将最低位保存在最低位
            temp = temp >>8;// 向右移8位
        }
        return b;
    }
    //byte数组转成long
    public static long byteToLong(byte[] b){
        long s =0;
        long s0 = b[0]&0xff;// 最低位
        long s1 = b[1]&0xff;
        long s2 = b[2]&0xff;
        long s3 = b[3]&0xff;
        long s4 = b[4]&0xff;// 最低位
        long s5 = b[5]&0xff;
        long s6 = b[6]&0xff;
        long s7 = b[7]&0xff;

        // s0不变
        s1 <<=8;
        s2 <<=16;
        s3 <<=24;
        s4 <<=8*4;
        s5 <<=8*5;
        s6 <<=8*6;
        s7 <<=8*7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }

    /**
     * 注释：int到字节数组的转换！
     *
     * @param number
     * @return
     */
    public static byte[] intToByte(int number){
        int temp = number;
        byte[] b =new byte[4];
        for(int i =0; i < b.length; i++){
            b[i]=new Integer(temp &0xff).byteValue();//
            //将最低位保存在最低位
            temp = temp >>8;// 向右移8位
        }
        return b;
    }

    /**
     * 注释：字节数组到int的转换！
     *
     * @param b
     * @return
     */
    public static int byteToInt(byte[] b){
        int s =0;
        int s0 = b[0]&0xff;// 最低位
        int s1 = b[1]&0xff;
        int s2 = b[2]&0xff;
        int s3 = b[3]&0xff;
        s3 <<=24;
        s2 <<=16;
        s1 <<=8;
        s = s0 | s1 | s2 | s3;
        return s;
    }

    public static int hexToInt2(String hex){
        int i=0,j;
        for (int k=0;k<hex.length();k++){
            j= (int) Math.pow(16,k);
            //i=i+ (int) hex.charAt(k) *j;
            i=i+"0123456789ABCDEF".indexOf(hex.toUpperCase().charAt(hex.length()-k-1))*j;
            //System.out.println("Log2:"+k+"  "+hex.toUpperCase().charAt(k)+"   "+"0123456789ABCDEF".indexOf(hex.toUpperCase().charAt(k)));
        }
        return i;
    }
}
