package org.fengpiaohong.amazingcalcrelease;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.CRC32;

public class MainActivity extends Activity {

    Button btn_con, btn_info;
    TextView tv;
    ProgressBar pb;

    Timer timer = new Timer();
    MyTimerTask task;
    long m_time;
    int k;
    boolean is_getTimeByNet=false;



    final Handler handler = new Handler(){
        int count=0;

        @Override
        public void handleMessage(Message msg) {
            if (count==0){
                tv.setText(orderK( String.valueOf(k)));
            }
            count++;
            if (count>=300){
                count=0;
                m_time+=30;
                k=calcFinal(m_time);
                tv.setText(orderK(String.valueOf(k)));
            }
            pb.setProgress(count);
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        calcByLocal();
        pb= (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(300);


        tv = (TextView) findViewById(R.id.tv1);
        btn_con = (Button) findViewById(R.id.btn_con);
        btn_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcByNet();
            }
        });

        btn_info = (Button) findViewById(R.id.btn_init);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, informationAct.class);
                startActivity(it);
            }
        });

        task = new MyTimerTask(handler);
        timer.schedule(task,0,100);
        Toast.makeText(this,"123123",Toast.LENGTH_LONG).show();
    }

    //退出activity时,停止timer
    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }


    //恢复activity时恢复timer
    @Override
    protected void onResume() {
        super.onResume();
        if (is_getTimeByNet){
            calcByNet();
        }else {
            calcByLocal();
        }
        timer.purge();
        timer.cancel();
        task=new MyTimerTask(handler);
        timer=new Timer();
        timer.schedule(task,0,100);
    }

    //获取本地时间
    private  void calcByLocal(){
        m_time=(long)MyApplicationUtils.formatDouble(System.currentTimeMillis()/1000.0,0);
        m_time=m_time/30*30;
        k=calcFinal(m_time);
    }

    private int calcFinal(long key){
        byte[] b=MyApplicationUtils.intToByte((int)key);
        //System.out.println("The Time Key:"+ Arrays.toString(b));
        CRC32 crc32=new CRC32();
        crc32.update(b);
        //int kk= (int) (crc32.getValue())+MyApplicationUtils.hexToDec(MyApplicationUtils.getDevicesStr());
        String tmpStr = MyApplicationUtils.longToHex(crc32.getValue());
        //tmpStr=tmpStr.substring(tmpStr.length()-8,tmpStr.length());
        //System.out.println("Log: "+(int)key+"  "+tmpStr);
        int kk=MyApplicationUtils.hexToInt2(tmpStr)+MyApplicationUtils.hexToInt2(MyApplicationUtils.getDevicesStr());
        //System.out.println("Crc32 Key:"+kk);
        tmpStr= String.valueOf(kk);
        tmpStr=tmpStr.substring(tmpStr.length()-6,tmpStr.length());
        return Integer.valueOf(tmpStr);
    }


    //获取网络时间
    private void calcByNet(){
        is_getTimeByNet=true;
        final HttpUtil m_http = new HttpUtil("http://api.time.3023.com/time");
        m_http.doGet(new HttpResponse() {
            @Override
            public void response(String... param) {
                Gson gson=new Gson();
                NetTimeClass netTimeClass=gson.fromJson(param[0],NetTimeClass.class);
                m_time=netTimeClass.stime;
                m_time=m_time/30*30;
                System.out.println(m_time);
            }
        });
        k=calcFinal(m_time);
        timer.purge();
        timer.cancel();
        task=new MyTimerTask(handler);
        timer=new Timer();
        timer.schedule(task,0,100);
    }

    private String orderK(String str){
        for (int i=0;i<6-str.length();i++){
            str="0"+str;
        }
        return str;
    }

    //Timer调用的函数
    public class MyTimerTask extends TimerTask {
        Handler m_handler;
        MyTimerTask(Handler handler){
            m_handler=handler;
        }
        @Override
        public void run() {
            Message message = new Message();
            message.what=1;
            m_handler.sendMessage(message);
        }
    }



}

