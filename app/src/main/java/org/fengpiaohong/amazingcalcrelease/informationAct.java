package org.fengpiaohong.amazingcalcrelease;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class informationAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        TextView tv= (TextView) findViewById(R.id.DevicesView);

        tv.setText(MyApplicationUtils.getDevicesStr());
    }
}
